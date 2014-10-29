define(["angular"], function(angular) {
 angular.module('envconfig', [])

.constant('ENV', {name:'production',apiEndpoint:'http://luxury-akka-josip.herokuapp.com'})

.constant('TOKEN', 'luxury-akka-token')

.constant('REFRESH_TOKEN_VALID_TIME', 300000)

; });