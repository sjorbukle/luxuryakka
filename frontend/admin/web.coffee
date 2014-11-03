gzippo = require 'gzippo'
express = require 'express'
sslify = require 'express-sslify'

app = express()
app.use express.logger 'dev'
app.use sslify.HTTPS(true)
      
app.use gzippo.staticGzip "#{__dirname}/dist"
app.listen process.env.PORT || 5000
