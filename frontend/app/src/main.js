require([
    'angular',
    'jquery',
    'angular-resource',
    'angular-route',
    'constants',
    'jwt_services',
    'services',
    'controllers'
], function(angular) {
    'use strict';

    /*App Module*/
    angular.module('webApp', [
        'ngRoute',
        'ngResource',
        'app.controllers',
        'com.laplacian.luxuryakka.services',
        'com.laplacian.luxuryakka.jwt_services'
    ])
    .config(function ($routeProvider) {
        $routeProvider
        .when('/', {
            templateUrl: 'src/views/dashboard.html',
            controller: 'MainCtrl'
        })
        .when('/register', {
            templateUrl: 'src/views/register.html',
            controller: 'RegisterCtrl'
        })
        .when('/login', {
            templateUrl: 'src/views/login.html',
            controller: 'LoginCtrl'
        })
        .otherwise({
            redirectTo: '/login'
        });
    })
    .run(function ($rootScope, $location, TOKEN) {
        $rootScope.$on("$routeChangeStart", function(event, next, current) {
            var token = localStorage.getItem(TOKEN);
            if(!token && next.templateUrl != 'src/views/register.html') {
                $location.path("/login");
            } else {
                $rootScope.userSet = token;
            }
        });
    });
});
