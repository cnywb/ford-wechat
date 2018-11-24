var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  LOGIN_URL: '"http://www.changanfordwechat.com/fordwechat/control?state=3002"',
  BIND_URL: '"http://www.changanfordwechat.com/fordwechat/control?state=4002"',
  RAVEN_CDN: '"https://e1e5208bd0a540ce94eef8a57764c1d0@sentry.io/211296"'
})
