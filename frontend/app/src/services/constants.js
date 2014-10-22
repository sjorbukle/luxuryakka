define(['angular'], function(angular) {
    angular.module('com.laplacian.luxuryakka.constants', [])
        .constant('REFRESH_TOKEN_VALID_TIME', 300000)
        .constant('TOKEN', 'luxury-akka-token')
        .constant('CLOUD', 'localhost:9000');
});