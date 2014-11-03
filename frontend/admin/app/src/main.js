require([
    'angular',
    'jquery',
    'angular-resource',
    'angular-route',
    'envconfig',
    'jwt_services',
    'services',
    'controllers',
    'bootstrap-sass'
], function(angular) {
    'use strict';

    /*App Module*/
    angular.element(document).ready(function () {
        /*smart works go here*/
        var $html = angular.element('html');

    angular.module('webApp', [
        'ngRoute',
        'ngResource',
        'app.controllers',
        'envconfig',
        'com.laplacian.luxuryakka.services',
        'com.laplacian.luxuryakka.jwt_services'
    ])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
        .when('/profile', {
            templateUrl: 'src/views/profile.html',
            controller: 'ProfileCtrl'
        })
        .when('/dashboard', {
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
        .when('/logout', {
            template: '',
            controller: 'LogoutCtrl'
        })
        .otherwise({
            redirectTo: '/login'
        });
    }])
    .run(['$rootScope', '$location', 'TOKEN', function ($rootScope, $location, TOKEN) {
            $rootScope.$on('$routeChangeStart', function (event, next) {
                var token = localStorage.getItem(TOKEN);
                if (!token && !(next.templateUrl == 'src/views/register.html' || next.templateUrl == 'src/views/login.html')) {
                    event.preventDefault();
                    $timeout(function () {
                        $location.path('/login').replace();
                    });
                } else {
                    $rootScope.userSet = token;
                }
            });
    }]);

    /*bootstrap model*/
    angular.bootstrap($html, ['webApp']);
    });
});
