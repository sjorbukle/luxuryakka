define(['angular'], function(angular) {
    angular.module('luxuryakka.authentication', ['luxuryakka.authenticationBackend']);

    angular.module('luxuryakka.authenticationBackend', ['envconfig']);
});