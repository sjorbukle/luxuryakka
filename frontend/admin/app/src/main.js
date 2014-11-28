require([
    'angular',
    'jquery',
    'angular-resource',
    'angular-sanitize',
    'angular-route',
    'envconfig',

    'authentication',
    'authenticationFactory',

    'user',
    'userFactory',
    'userController',

    'administration',
    'organizationStructure',
    'organizationStructureFactory',
    'organizationStructureController',
    'organizationStructureDirectives',
    'generalSettings',
    'generalSettingsController',
    'generalSettingsFactory',
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

//  Main appModule instance
    var appModule = angular.module('webApp', [
        'ngRoute',
        'ngResource',
        'ngSanitize',

        'envconfig',
        'ui.select',

        'luxuryakka.authenticationBackend',
        'luxuryakka.user',
        'luxuryakka.administration',

        'textAngular'
    ]);

//  MainCtrl definition
    appModule.controller('MainCtrl', ['$scope', 'Helper', '$location', 'userService','TOKEN','$rootScope',
        function($scope, Helper, $location, userService, TOKEN, $rootScope) {
            $scope.pageTitleValue = 'Luxury Akka';

            $scope.isActive = function(route) {
                var location = $location.path();
                return location.substring(0, route.length) === route;
            };

            $scope.isLoggedIn = false;

            $rootScope.$watch('userSet', function( token ){
                if(token){
                    var userID = Helper.deserializeJWT(token).userId;
                    userService.getUserById(parseInt(userID))
                        .then(function (userData) {
                            $scope.isLoggedIn = true;
                            $scope.userFullName = userData.data.firstName + ' ' + userData.data.lastName;
                        });
                }
            });
        }]);

//  App routes configuration
    appModule.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
        .when('/profile', {
            templateUrl: 'src/user/views/profile.html',
            controller: 'ProfileCtrl'
        })
        .when('/dashboard', {
            templateUrl: 'src/dashboard.html'
        })

        .when('/administration', {
            templateUrl: 'src/administration/views/administration.html'
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
        .when('/administration/organization-structure/edit/:id', {
            templateUrl: 'src/administration/organization-structure/views/edit.html',
            controller: 'OrgStructureEditCtrl'
        })

        .when('/administration/general-settings', {
            templateUrl: 'src/administration/general-settings/views/view.html',
            controller: 'GeneralSettingsCtrl'
        })

        .when('/register', {
            templateUrl: 'src/user/views/register.html',
            controller: 'RegisterCtrl'
        })
        .when('/login', {
            templateUrl: 'src/user/views/login.html',
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
