var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  WECHAT_REDIRECT: '"https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzAxNzY0Mzk3OA==#wechat_redirect"'
})
