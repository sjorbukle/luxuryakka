define(["angular"], function(angular) {
 angular.module('envconfig', [])

.constant('ENV', {name:'development',apiEndpoint:'http://0.0.0.0:9000'})

.constant('TOKEN', 'luxury-akka-token')

.constant('REFRESH_TOKEN_VALID_TIME', 300000)

; });