/***
 Metronic AngularJS App Main Script
 ***/

/* Metronic App */
var DBApp = angular.module('DBApp', [
    'ui.router',
    'ui.bootstrap',
    'oc.lazyLoad',
    'db.components.grid',
    'db.components.form.grid',
    'db.components.report.grid',
    'db.components.data.table',
    'db.components.data.grid',
    'db.components.form',
    'db.components.form.fields',
    'db.components.orgTree',
    'db.components.resourceTree',
    'db.components.areaTree',
    'db.components.tree',
    'db.components.monthselect',
    'db.components.selectCity',
    'ui.select',
    'ngSanitize',
    'dbImService',
    'dbUtils',
    'ngAnimate',
    'toaster',
    'angular-datetimepicker'
]);

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
DBApp.config(['$ocLazyLoadProvider', '$httpProvider', function ($ocLazyLoadProvider, $httpProvider) {
    $ocLazyLoadProvider.config({
        debug: true
    });
    //添加请求头，防止后台无法判断是ajax请求
    $httpProvider.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest'
}]);

/* Setup Rounting For All Pages */
DBApp.config(['$stateProvider', '$urlRouterProvider', StateConfigController]);

/* Setup global settings */
DBApp.factory('settings', ['$rootScope', function ($rootScope) {
    // supported languages
    var settings = {
        layout: {
            pageSidebarClosed: false, // sidebar state
            pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
        },
        layoutImgPath: Metronic.getAssetsPath() + 'admin/layout/img/',
        layoutCssPath: Metronic.getAssetsPath() + 'admin/layout/css/'
    };
    $rootScope.settings = settings;

    return settings;
}]);
/*UI-SELECT多选框使用到的过滤器*/
DBApp.filter('selectPropsFilter', function () {
    return function (items, props) {
        var out = [];
        if (angular.isArray(items)) {
            items.forEach(function (item) {
                var itemMatches = false;

                var keys = Object.keys(props);
                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = props[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            out = items;
        }
        return out;
    };
});

/* Setup App Main Controller */
DBApp.controller('AppController', ['$scope', '$rootScope', AppController]);

function AppController ($scope) {
    $scope.$on('$viewContentLoaded', function () {
        Metronic.initComponents(); // init core components
        //Layout.init(); //  Init entire layout(header, footer, sidebar, etc) on page load if the partials included in server side instead of loading with ng-include directive 
    });
}

/***
 Layout Partials.
 By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial
 initialization can be disabled and Layout.init() should be called on page load complete as explained above.
 ***/

/* Setup Layout Part - Header */
DBApp.controller('HeaderController', ['$scope', '$window', '$http', '$state', 'dbUtils', function ($scope, $window, $http, $state, dbUtils) {
    $scope.$on('$includeContentLoaded', function () {
        Layout.initHeader(); // init header
    });
    $scope.userInfo = dbUtils.getUserInfo();
    $scope.logout = function () {
        dbUtils.confirm('确定退出系统吗?', function () {
            $http.post('../logout.ctl', {}).success(function (data, status, headers, config) {
                $window.sessionStorage.setItem('loginName', "");
                $window.location.href = '../';//退出后直接返回主页
            });
        });
    };
    $scope.reBackIndex = function () {
        $state.go('dashboard');
    };

    $scope.changePwd = function () {
        var instance = $modal.open({
            animation: true,
            templateUrl: 'views/security/changePwd.html',
            controller: ['$scope', 'dbUtils', '$modalInstance', 'loginName', function ($scope, dbUtils, $modalInstance, loginName) {
                $scope.data = {
                    loginName: loginName,
                    password: null,
                    checkPassword: null
                };
                //监听 密码 和确认密码是否一致。
                angular.forEach(['data.password', 'data.checkPassword'], function (item) {
                    $scope.$watch(item, function (newVal, oldVal) {
                        if (newVal !== oldVal) {
                            var password = $scope.data.password;
                            var checkPassword = $scope.data.checkPassword;
                            $scope.passwordMsg = "";
                            if (password != checkPassword) {
                                $scope.passwordMsg = "两次密码不一致,请检查。";
                            }
                        }
                    });
                });


                $scope.submitDialogForm = function (isValid) {
                    $scope.submited = true;
                    if (isValid) {
                        var password = $scope.data.password;
                        var checkPassword = $scope.data.checkPassword;
                        if (password != checkPassword) {
                            return;
                        }
                        var reqBody = angular.copy($scope.data);
                        dbUtils.post("modifyPwd", reqBody, function (data) {
                            if (data) {
                                dbUtils.warning(data);
                            } else {
                                dbUtils.success("密码修改更新成功!");
                                $modalInstance.close();
                            }
                        }, function (error) {
                            dbUtils.error(error);
                        });
                    }
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }],
            size: "md",
            backdrop: "static",
            resolve: {
                loginName: function () {
                    return $scope.userInfo.loginName;
                }
            }
        });
        instance.result.then(function () {
            $http.post("../logout.ctl", {}).success(function (data, status, headers, config) {
                $window.sessionStorage.setItem("loginName", "");
                $window.location.href = "../";//退出后直接返回主页
            });
        });
    };
}]);

/* Setup Layout Part - Sidebar */
DBApp.controller('SidebarController', ['$scope', '$http', '$modal', '$window', '$state', function ($scope, $http, $modal, $window, $state) {
    $scope.$on('$includeContentLoaded', function () {
        Layout.initSidebar($scope, $http, $modal, $window, $state); // init sidebar
    });
}]);

/* Setup Layout Part - Sidebar */
DBApp.controller('PageHeadController', ['$scope', function ($scope) {
    $scope.$on('$includeContentLoaded', function () {
        Demo.init(); // init theme panel
    });
}]);

/* Setup Layout Part - Footer */
DBApp.controller('FooterController', ['$scope', function ($scope) {
    $scope.$on('$includeContentLoaded', function () {
        Layout.initFooter(); // init footer
    });
}]);


function StateConfigController ($stateProvider, $urlRouterProvider) {

    // Redirect any unmatched url
    $urlRouterProvider.otherwise('/dashboard');

    $stateProvider.state('dashboard', {
        url: '/dashboard',
        views: {
            'mainContentContainer': { //mainContentContainer 对应页面上的ui-view值，用于指定view显示在哪个位置
                controller: 'DashboardController', // 新加载的页面对应controller，需要确保值唯一
                templateUrl: 'welcome.html'//具体需要显示的页面URL路径
            }
        },
        data: {pageTitle: '长安福特服务号管理平台', pageSubTitle: '控制面板 | 欢迎页面'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                //在切换到这个view的时候需要先加载对应的js或其他文件，load当中可以放入数组加载多个文件
                return $ocLazyLoad.load(['DashboardController.js']);
            }]
        }
    }).state('userRoleResource', {
        url: "/userRoleResource",
        views: {
            "mainContentContainer": {
                controller: "userRoleResourceListCtrl",
                templateUrl: "db/db-form-grid.html"
            }
        },
        data: {pageTitle: '权限管理', pageSubTitle: '用户角色权限查询导出'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: ['views/security/userRoleResourceListCtrl.js']
                }]);
            }]
        }
    }).state('operationLogEntry', {
        url: "/operationLogEntry",
        views: {
            "mainContentContainer": {
                controller: "operationLogListCtrl",
                templateUrl: "db/db-form-grid.html"
            }
        },
        data: {pageTitle: '权限管理', pageSubTitle: '权限操作日志查询'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: ['views/security/operationLogListCtrl.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/css/datepicker3.css',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js']
                }]);
            }]
        }
    }).state('securityUserList', {
        url: '/securityUserList',
        views: {
            'mainContentContainer': {
                controller: 'userListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '用户管理', pageSubTitle: '权限管理|用户管理——用户信息维护预览'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: ['views/security/userListCtrl.js', 'views/security/userEditorCtrl.js', 'views/security/changePwdCtrl.js']
                }]);
            }]
        }
    }).state('securityRoleList', {
        url: '/securityRoleList',
        views: {
            'mainContentContainer': {
                controller: 'roleListCtrl',
                templateUrl: 'views/security/roleList.html'
            }
        },
        data: {pageTitle: '角色管理', pageSubTitle: '权限管理|角色管理——系统角色信息维护预览'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true, files: ['views/security/roleListCtrl.js', 'views/security/roleEditorCtrl.js',
                        'views/security/authorizeUserCtrl.js', 'views/security/authorizeResourceCtrl.js',
                        'views/security/selectUserCtrl.js', 'views/security/selectResourceCtrl.js']
                }]);
            }]
        }
    }).state('security-resource-list', {
        url: '/security-resource-list',
        views: {
            'mainContentContainer': {
                controller: 'resourceListCtrl',
                templateUrl: 'views/security/resourceList.html'
            }
        },
        data: {pageTitle: '资源管理', pageSubTitle: '权限管理|资源管理——系统资源信息维护预览'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true, files: ['views/security/resourceListCtrl.js', 'views/security/resourceEditorCtrl.js']
                }]);
            }]
        }
    }).state('resourceEntry', {
        url: '/resourceEntry',
        views: {
            'mainContentContainer': {
                controller: 'resourceEntryCtrl',
                templateUrl: 'db/db-form.html'
            }
        },
        data: {pageTitle: '资源录入', pageSubTitle: '权限管理|资源录入——资源信息维护录入'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load({
                    serie: true,
                    files: ['views/security/resourceEntryCtrl.js']
                });
            }]
        }
    }).state('resourceModify', {
        url: '/resourceModify',
        views: {
            'mainContentContainer': {
                controller: 'resourceModifyCtrl',
                templateUrl: 'views/security/resourceModifyView.html'
            }
        },
        data: {pageTitle: '资源修改', pageSubTitle: '权限管理|资源修改——系统资源信息维护预览'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true, files: ['views/security/resourceModifyCtrl.js']
                }]);
            }]
        }
    }).state('securityImList', {
        url: '/securityImList',
        views: {
            'mainContentContainer': {
                controller: 'imCodeCtrl',
                templateUrl: 'views/im/imCodeTypeList.html'
            }
        },
        data: {pageTitle: '字典管理', pageSubTitle: '权限管理|字典管理——系统枚举分类以及字典项维护预览'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: ['views/im/imCodeTypeListCtrl.js', 'views/im/imCodeTypeEditorCtrl.js', 'views/im/imCodeListEditorCtrl.js']
                }]);
            }]
        }
    }).state('formTest', {
        url: '/formTest',
        views: {
            'mainContentContainer': {
                controller: 'formCtrl',
                templateUrl: 'views/form/form.html'
            }
        },
        data: {pageTitle: '示例代demo', pageSubTitle: 'db-condition-grid,db-form结合的例子'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: ['views/form/form.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/css/datepicker3.css',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js']
                }]);
            }]
        }
    }).state('tree-test', {
        url: '/tree-test',
        views: {
            'mainContentContainer': {
                controller: 'treeCtrl',
                templateUrl: 'views/form/tree.html'
            }
        },
        data: {pageTitle: 'tree', pageSubTitle: 'tree test'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{files: ['views/form/tree.js']}]);
            }]
        }
    }).state('appLinkManage', {//app管理
        url: '/appLinkManage',
        views: {
            'mainContentContainer': {
                controller: 'appLinkListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: 'app管理', pageSubTitle: '企业号|app管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/applink/appLinkListCtrl.js',
                        'views/applink/appLinkEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('redirectConfigManage', {//redirectConfig
        url: '/redirectConfigManage',
        views: {
            'mainContentContainer': {
                controller: 'redirectConfigListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '重定向', pageSubTitle: '企业号|重定向'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/redirectconfig/redirectConfigListCtrl.js',
                        'views/redirectconfig/redirectConfigEditorCtrl.js'
                    ]
                }]);
            }]
        }
    })

        .state('issueManage', {//问题列表
            url: '/issueManage',
            views: {
                'mainContentContainer': {
                    controller: 'issueListCtr',
                    templateUrl: 'db/db-form-grid.html'
                }
            },
            data: {pageTitle: '问题列表', pageSubTitle: '企业号|问题列表'},
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            'views/issue/issueListCtrl.js',
                            'views/question/questionEditorCtrl.js',
                            'views/question/questionAnswerCtrl.js'
                        ]
                    }]);
                }]
            }
        })

        .state('questionManage', {//question
            url: '/questionManage',
            views: {
                'mainContentContainer': {
                    controller: 'questionListCtr',
                    templateUrl: 'db/db-form-grid.html'
                }
            },
            data: {pageTitle: '未回答问题列表', pageSubTitle: '企业号|未回答问题列表'},
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            'views/question/questionListCtrl.js',
                            'views/question/questionEditorCtrl.js',
                            'views/question/questionAnswerCtrl.js'
                        ]
                    }]);
                }]
            }
        }).state('answerManage', {//answer
        url: '/answerManage',
        views: {
            'mainContentContainer': {
                controller: 'answerListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '已回答问题列表', pageSubTitle: '企业号|已回答问题列表'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/question/answerListCtrl.js',
                        'views/question/questionEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('carInfoManage', {//车辆信息
        url: '/carInfoManage',
        views: {
            'mainContentContainer': {
                controller: 'carInfoListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '车辆信息', pageSubTitle: '企业号|车辆信息'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/carinfo/carInfoListCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('userInfoManage', {//微信用户
        url: '/userInfoManage',
        views: {
            'mainContentContainer': {
                controller: 'userInfoListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '微信用户', pageSubTitle: '企业号|微信用户'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/userinfo/userInfoListCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('groupManage', {//微信用户组
        url: '/groupManage',
        views: {
            'mainContentContainer': {
                controller: 'groupListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '微信用户组', pageSubTitle: '企业号|微信用户组'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/group/groupListCtrl.js',
                        'views/group/groupEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('clubDealerManage', {//经销商管理
        url: '/clubDealerManage',
        views: {
            'mainContentContainer': {
                controller: 'dealerListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '经销商管理', pageSubTitle: '企业号|经销商管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/dealer/dealerListCtrl.js',
                        'views/dealer/dealerEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('clubChannelManage', {//渠道商管理
        url: '/clubChannelManage',
        views: {
            'mainContentContainer': {
                controller: 'channelListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '渠道商管理', pageSubTitle: '企业号|渠道商管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/channel/channelListCtrl.js',
                        'views/channel/channelEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('weFordClubMemberManage', {//车主管理
        url: '/weFordClubMemberManage',
        views: {
            'mainContentContainer': {
                controller: 'weFordClubMemberListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '车主管理', pageSubTitle: '企业号|车主管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/userinfo/WeFordClubMemberListCtrl.js',
                        'views/userinfo/WeFordClubMemberEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('auditManage', {//认证审核
        url: '/auditManage',
        views: {
            'mainContentContainer': {
                controller: 'carOwnerAuthenStatusListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '审核管理', pageSubTitle: '企业号|审核管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/userinfo/carOwnerAuthenStatusListCtrl.js',
                        'views/userinfo/carOwnerAuthenStatusEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('unAuthManage', {//解绑审核
        url: '/unAuthManage',
        views: {
            'mainContentContainer': {
                controller: 'weUnauthApplyListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '审核管理', pageSubTitle: '企业号|审核管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/members/weUnauthApplyListCtrl.js',
                        'views/members/weUnauthApplyEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('surveyDetailManage', {//问卷管理
        url: '/surveyDetailManage',
        views: {
            'mainContentContainer': {
                controller: 'SurveyResultsListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '问卷结果', pageSubTitle: '问卷管理|问卷结果'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/survey/SurveyResultsListCtrl.js',
                    ]
                }]);
            }]
        }
    }).state('surveyManage', {//问卷管理
        url: '/surveyManage',
        views: {
            'mainContentContainer': {
                controller: 'SurveyListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '问卷列表', pageSubTitle: '问卷管理|问卷列表'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/survey/SurveyListCtrl.js',
                        'views/survey/SurveyLinkCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('surveyConfigManage', {//问卷管理
        url: '/surveyConfigManage',
        views: {
            'mainContentContainer': {
                controller: 'SurveyConfigListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '问卷配置', pageSubTitle: '问卷管理|问卷配置'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/survey/SurveyConfigListCtrl.js',
                        'views/survey/SurveyConfigEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('surveyCallbackManage', {//问卷管理
        url: '/surveyCallbackManage',
        views: {
            'mainContentContainer': {
                controller: 'SurveyCallbackListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '回调记录', pageSubTitle: '问卷管理|回调记录'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/survey/SurveyCallbackListCtrl.js',
                    ]
                }]);
            }]
        }
    }).state('redirectionManage', {//redirectConfig
        url: '/redirectionManage',
        views: {
            'mainContentContainer': {
                controller: 'RedirectionListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '回调管理', pageSubTitle: '回调管理|重定向'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/hub/RedirectionListCtrl.js',
                        'views/hub/RedirectionEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('authDmsManage', {//DMS 管理
        url: '/authDmsManage',
        views: {
            'mainContentContainer': {
                controller: 'authDmsListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: 'DMS管理', pageSubTitle: '企业号|DMS管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/auth/authDmsListCtrl.js'
                    ]
                }]);
            }]
        } //batchTaskPage
    }).state('batchTaskManage', {//批处理任务管理
        url: '/batchTaskManage',
        views: {
            'mainContentContainer': {
                controller: 'batchTaskListCtr',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: 'DMS管理', pageSubTitle: '企业号|批处理任务管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/batch/batchTaskListCtrl.js',
                        'views/batch/batchTaskEditorCtrl.js',

                    ]
                }]);
            }]
        }
    }).state('eventDetailManage', { //认证激励-代金券活动配置
        url: '/eventDetailManage',
        views: {
            'mainContentContainer': {
                controller: 'eventDetailListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '认证激励', pageSubTitle: '企业号|代金券活动配置'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/coupon/eventDetailListCtrl.js',
                        'views/coupon/eventDetailEditorCtrl.js',
                        'views/coupon/eventDetailAddCtrl.js',
                        // Metronic.getAssetsPath() + 'global/plugins/zl-time/pikaday.css',
                        // Metronic.getAssetsPath() + 'global/plugins/zl-time/datetimepicker.js',
                        // Metronic.getAssetsPath() + 'global/plugins/zl-time/moment.js',
                        // Metronic.getAssetsPath() + 'global/plugins/zl-time/pikaday-edit.js'
                    ]
                }]);
            }]
        }
    }).state('couponManage', { //认证激励-代金券管理
        url: '/couponManage',
        views: {
            'mainContentContainer': {
                controller: 'couponListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '认证激励', pageSubTitle: '企业号|代金券管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/coupon/couponListCtrl.js',
                        'views/coupon/couponBindVinCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('eventOperationRecordManage', { //认证激励-代金券页面访问日志
        url: '/eventOperationRecordManage',
        views: {
            'mainContentContainer': {
                controller: 'eventOperationRecordListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '认证激励', pageSubTitle: '企业号|代金券页面访问日志'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/coupon/eventOperationRecordListCtrl.js'
                        // 'views/coupon/eventDetailEditorCtrl.js',
                    ]
                }]);
            }]
        }
    }).state('eventStatisticsManage', { //认证激励-认证激励统计
        url: '/eventStatisticsManage',
        views: {
            'mainContentContainer': {
                controller: 'eventStatisticsListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '认证激励', pageSubTitle: '企业号|认证激励统计'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/coupon/eventStatisticsListCtrl.js'
                        // 'views/coupon/eventDetailEditorCtrl.js',
                    ]
                }]);
            }]
        }
    }).state('messageSendManage', { //认证激励-短信发送记录
        url: '/messageSendManage',
        views: {
            'mainContentContainer': {
                controller: 'messageSendListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '认证激励', pageSubTitle: '企业号|短信发送记录'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/message/messageSendListCtrl.js'
                        // 'views/coupon/eventDetailEditorCtrl.js',
                    ]
                }]);
            }]
        }
    }).state('couponDmsManage', { //认证激励-DMS下发管理
        url: '/couponDmsManage',
        views: {
            'mainContentContainer': {
                controller: 'couponDmsListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '认证激励', pageSubTitle: '企业号|DMS下发管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    files: [
                        'views/coupon/couponDmsListCtrl.js',
                        'views/coupon/couponDmsEditorCtrl.js',
                    ]
                }]);
            }]
        }
    }).state('pcInfoManager', {//微信个人中心管理-资讯管理
        url: '/pcInfoManager',
        views: {
            'mainContentContainer': {
                controller: 'infoEntityCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '资讯管理', pageSubTitle: '微信个人中心|资讯管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        Metronic.getAssetsPath() + 'global/plugins/tinyEditor/tinyeditor.js',
                        Metronic.getAssetsPath() + 'global/plugins/tinyEditor/style.css',
                        'views/pc/info/infoEntityManageCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('pcIconMenuManager', {//微信个人中心管理-图标菜单管理
        url: '/pcIconMenuManager',
        views: {
            'mainContentContainer': {
                controller: 'iconMenuEntityCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '图标菜单管理', pageSubTitle: '微信个人中心|图标菜单管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/menu/iconMenuEntityListCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('pcIconMenuPublish', {//微信个人中心管理-图标菜单发布
        url: '/pcIconMenuPublish',
        views: {
            'mainContentContainer': {
                controller: 'iconMenuPublishCtrl',
                templateUrl: 'views/pc/menu/iconMenuPublish.html'
            }
        },
        data: {pageTitle: '图标菜单管理', pageSubTitle: '微信个人中心|图标菜单发布'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        Metronic.getAssetsPath() + 'global/plugins/angularjs/plugins/angular-file-upload.min.js',
                        'views/pc/menu/iconMenuPublishCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('messageTemplateManage', {//微信个人中心管理-图标菜单管理
        url: '/messageTemplateManage',
        views: {
            'mainContentContainer': {
                controller: 'messageTemplateListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '消息模板管理', pageSubTitle: '微信个人中心|消息模板管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/message/messageTemplateListCtrl.js',
                        'views/pc/message/messageTemplateEditorCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('infoEntityEntry', {//行车养车录入|内容管理中行车养车录入
        url: '/infoEntityEntry/:id',
        views: {
            'mainContentContainer': {
                controller: 'infoEntityEntryCtrl',
                templateUrl: 'views/pc/info/infoEntityEntry.html'
            }
        },
        data: {pageTitle: '行车养车录入', pageSubTitle: '行车养车录入|内容管理中行车养车录入'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        Metronic.getAssetsPath() + 'global/plugins/angularjs/plugins/angular-file-upload.min.js',
                        'views/pc/info/infoEntityEntryCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('infoEntityManage', {//行车养车管理|内容管理中行车养车管理
        url: '/infoEntityManage',
        views: {
            'mainContentContainer': {
                controller: 'infoEntityManageCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '行车养车管理', pageSubTitle: '行车养车管理|内容管理中行车养车管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/info/infoEntityManageCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('qaInfoEntityManage', {//内容管理中互动问答管理-互动问答管理
        url: '/qaInfoEntityManage',
        views: {
            'mainContentContainer': {
                controller: 'qaInfoEntityManageCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '互动问答管理', pageSubTitle: '互动问答管理|内容管理中互动问答管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/info/qaInfoEntityManageCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('messageContentManage', {//内容管理中互动问答管理-互动问答管理
        url: '/messageContentManage',
        views: {
            'mainContentContainer': {
                controller: 'messageContentManageCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '消息内容管理', pageSubTitle: '消息内容管理|消息管理中消息内容管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/message/messageContentManageCtrl.js',
                        'views/pc/message/selectTemplateCtrl.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-timepicker/css/bootstrap-datetimepicker.css',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-timepicker/js/bootstrap-datetimepicker.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-timepicker/js/locales/bootstrap-datetimepicker.zh-CN.js'
                    ]
                }]);
            }]
        }
    }).state('messageRecordManage', {//内容管理中互动问答管理-互动问答管理
        url: '/messageRecordManage',
        views: {
            'mainContentContainer': {
                controller: 'messageRecordManageCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '消息记录管理', pageSubTitle: '消息记录管理|消息管理中消息记录管理'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/message/messageRecordManageCtrl.js'
                    ]
                }]);
            }]
        }
    }).state('qaInfoEntityEntry', {//消息管理中互动问答录入-互动问答录入
        url: '/qaInfoEntityEntry/:id',
        views: {
            'mainContentContainer': {
                controller: 'qaInfoEntityEntryCtrl',
                templateUrl: 'views/pc/info/qaInfoEntityEntry.html'
            }
        },
        data: {pageTitle: '互动问答录入', pageSubTitle: '互动问答录入|消息管理中互动问答录入'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/info/qaInfoEntityEntryCtrl.js',
                        Metronic.getAssetsPath() + 'global/plugins/wangEditor/wangEditor.js'
                    ]
                }]);
            }]
        }
    }).state('esSearchInfo', {//消息管理中互动问答录入-互动问答录入
        url: '/esSearchInfo',
        views: {
            'mainContentContainer': {
                controller: 'esSearchInfoCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '搜索内容管理配置信息', pageSubTitle: '搜索内容管理配置信息|消息管理中搜索内容管理配置信息'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/esSearchInfoCtrl.js',
                        Metronic.getAssetsPath() + 'global/plugins/wangEditor/wangEditor.js'
                    ]
                }]);
            }]
        }
    }).state('browseMsgRecord', {//统计管理中对于公告浏览量的统计-公告浏览量统计
        url: '/browseMsgRecord',
        views: {
            'mainContentContainer': {
                controller: 'browseMsgRecordCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '公告浏览量统计', pageSubTitle: '公告浏览量统计|统计管理中对于公告浏览量的统计'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/browse/browseMsgRecordCtrl.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/css/datepicker3.css',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js']
                }]);
            }]
        }
    }).state('browseMenuRecord', {//统计管理中对于菜单点击量的统计-菜单点击量统计
        url: '/browseMenuRecord',
        views: {
            'mainContentContainer': {
                controller: 'browseMenuRecordCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '菜单点击量统计', pageSubTitle: '菜单点击量统计|统计管理中对于菜单点击量的统计'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/browse/browseMenuRecordCtrl.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/css/datepicker3.css',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js']
                }]);
            }]
        }
    }).state('complainList', {//统计管理中对于菜单点击量的统计-菜单点击量统计
        url: '/complainList',
        views: {
            'mainContentContainer': {
                controller: 'complainListCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '投诉建议', pageSubTitle: '投诉管理|投诉或建议记录数据'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/complain/complainListCtrl.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/css/datepicker3.css',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js',
                        Metronic.getAssetsPath() + 'global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js']
                }]);
            }]
        }
    }).state('emailConfigEntry', {//统计管理中对于菜单点击量的统计-菜单点击量统计
        url: '/emailConfigEntry',
        views: {
            'mainContentContainer': {
                controller: 'emailConfigEntryCtrl',
                templateUrl: 'views/pc/complain/emailConfigEntryCtrl.html'
            }
        },
        data: {pageTitle: '客诉通知邮箱', pageSubTitle: '投诉管理|投诉或建议发送的客诉通知邮箱'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [
                        'views/pc/complain/emailConfigEntryCtrl.js']
                }]);
            }]
        }
    }).state('activityManage', {//活动管i理|活动发布以及取消
        url: '/activityManage',
        views: {
            'mainContentContainer': {
                controller: 'activityManageCtrl',
                templateUrl: 'db/db-form-grid.html'
            }
        },
        data: {pageTitle: '活动管理', pageSubTitle: '活动管理|活动预览'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: ['views/pc/activity/activityManageCtrl.js']
                }]);
            }]
        }
    }).state('activityEntry', {//活动管i理|活动发布以及取消
        url: '/activityEntry/:id',
        views: {
            'mainContentContainer': {
                controller: 'activityEntryCtrl',
                templateUrl: 'views/pc/activity/activityEntry.html'
            }
        },
        data: {pageTitle: '活动录入', pageSubTitle: '活动管理|活动录入'},
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    serie: true,
                    files: [Metronic.getAssetsPath() + 'global/plugins/angularjs/plugins/angular-file-upload.min.js',
                        'views/pc/activity/activityEntryCtrl.js']
                }]);
            }]
        }
    });
}

/* Init global settings and run the app */
DBApp.run(['$rootScope', 'settings', '$state', '$templateCache', function ($rootScope, settings, $state, $templateCache) {
    $rootScope.$state = $state; // state to be accessed from view
    $templateCache.put('db/db-form.html', '<div class="row"><db-form></db-form></div>');
    $templateCache.put('db/db-form-grid.html', '<div class="row"><db-form-grid></db-form-grid></div>');
    $templateCache.put('db/db-report-grid.html', '<div class="row"><db-report-grid></db-report-grid></div>');
}]);
