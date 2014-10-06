var mainApp = angular.module('logistika', [
    'logistika.core',
    'mgcrea.ngStrap',
    'angularseed.login-service',
    'ui.router',
    'ngAnimate'
]);

mainApp.run(['$rootScope', '$state', '$stateParams',
    function ($rootScope, $state, $stateParams) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
}]);

mainApp.config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $httpProvider) {

        $httpProvider.interceptors.push('loginValidatorHttpInterceptor');

        $urlRouterProvider.otherwise('/login');

        $stateProvider
            .state('login', {
                url: '/login',
                views: {
                    '': {
                        template: '<code>You need to log in</code><button ui-sref="app">home</button>'
                    }
                }
            })
            // this state hold ui-views and inject shared templates data
            .state('app', {
                url: '/',
                // == [login] ==
                resolve: { isAuthorized: 'LoginService' },
                onEnter: function(isAuthorized, $state){
                    if(!isAuthorized){
                        console.log('Unauthorized access!');
                        $state.go('login');
                    } else {
                        console.log('Access granted.');
                    }
                },
                // == login ==
                templateUrl: 'partials/root.html'
            });
}]);
