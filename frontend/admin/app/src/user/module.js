define(['angular'], function(angular) {
    angular.module('luxuryakka.user', ['luxuryakka.userBackend', 'luxuryakka.authenticationBackend']);

    angular.module('luxuryakka.userBackend', []);
});