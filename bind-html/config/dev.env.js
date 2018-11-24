var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  WECHAT_REDIRECT: '"https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzAxNzY0Mzk3OA==#wechat_redirect"',
  AUTH_REDIRECT: '"http://point.xiqing.info/static/home.html?state=31"',
  RAVEN_CDN: '"https://636ab3cc84d64684b7d04d4618f65b71@sentry.io/207153"'
})
