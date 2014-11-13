require([
    'angular',
    'jquery',
    'angular-resource',
    'angular-sanitize',
    'angular-route',
    'envconfig',

    'jwtServiceModule',
    'coreServiceModule',
    'coreControllerModule',

    'administration',
    'organizationStructure',
    'organizationStructureFactory',
    'organizationStructureController',
    'generalSettings',

    'bootstrap-sass',
    'underscore',
    'angular-ui-select',
    'textAngular'
], function(angular) {
    'use strict';

    /*App Module*/
    angular.element(document).ready(function () {
        /*smart works go here*/
        var $html = angular.element('html');

    angular.module('webApp', [
        'ngRoute',
        'ngResource',
        'ngSanitize',

        'luxuryakka.service.coreModule',
        'luxuryakka.controller.coreModule',

        'envconfig',
        'ui.select',

        'luxuryakka.service.jwtModule',

        'luxuryakka.administration',

        'textAngular'
    ])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
        .when('/profile', {
            templateUrl: 'src/ZZZZviews/profile.html',
            controller: 'ProfileCtrl'
        })
        .when('/dashboard', {
            templateUrl: 'src/ZZZZviews/dashboard.html'
        })
        .when('/administration', {
            templateUrl: 'src/ZZZZviews/administration.html'
        })
        .when('/administration/organization-structure', {
            templateUrl: 'src/administration/organization-structure/views/view.html',
            controller: 'OrgStructureFirstCallCtrl'
        })
        .when('/administration/organization-structure/create', {
            templateUrl: 'src/administration/organization-structure/views/create.html',
            controller: 'OrgStructureCreateCtrl'
        })
        .when('/administration/organization-structure/:parentId', {
            templateUrl: 'src/administration/organization-structure/views/view.html',
            controller: 'OrgStructureCtrl'
        })
        .when('/administration/general-settings', {
            templateUrl: 'src/administration/general-settings/views/view.html'
        })
        .when('/register', {
            templateUrl: 'src/ZZZZviews/register.html',
            controller: 'RegisterCtrl'
        })
        .when('/login', {
            templateUrl: 'src/ZZZZviews/login.html',
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
    .run(['$rootScope', '$location', 'TOKEN', '$timeout', '$templateCache', function ($rootScope, $location, TOKEN, $timeout, $templateCache) {
            $rootScope.$on('$routeChangeStart', function (event, next) {
                var token = localStorage.getItem(TOKEN);
                if (!token && !(next.originalPath == '/register' || next.originalPath == '/login')) {
                    event.preventDefault();
                    $timeout(function () {
                        $location.path('/login').replace();
                    });
                } else {
                    if (!$rootScope.hasOwnProperty('userSet')) {
                        $rootScope.userSet = token;
                    }
                }
            });


            var history = [];
            $rootScope.$on('$routeChangeSuccess', function() {
                history.push($location.$$path);
            });

            $rootScope.back = function () {
                var prevUrl = history.length > 1 ? history.splice(-2)[0] : "/";
                $location.path(prevUrl);
            };

            console.log( $templateCache.get('select2/select.tpl.html') );

            $('#textAngular-editableFix-010203040506070809').hide(); // FIXME: remove this when lib gets updated. It prevents white pixel bug.

        }]);

    /*bootstrap model*/
    angular.bootstrap($html, ['webApp']);
    });
});
