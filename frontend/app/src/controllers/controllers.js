define(['angular'], function(angular) {
    angular.module('app.controllers', [])
    .controller('MainCtrl', ['$scope', 'Helper', '$location', 'luxuryakka','TOKEN','$rootScope',
        function($scope, Helper, $location, luxuryakka, TOKEN, $rootScope) {
        $scope.pageTitleValue = 'Luxury Akka';

        $scope.isActive = function(route) {
            return route === $location.path();
        };

        $scope.isLoggedIn = false;

        $rootScope.$watch('userSet', function( token ){
            if(token){
                var userID = Helper.deserializeJWT(token).userId;
                luxuryakka.getUserById(parseInt(userID))
                    .then(function (userData) {
                        $scope.isLoggedIn = true;
                        $scope.userFullName = userData.data.firstName + ' ' + userData.data.lastName;
                    });
            }
        });
    }])
    .controller('ProfileCtrl', ['$scope', 'TOKEN', 'luxuryakka', 'Helper',
    function($scope, TOKEN, luxuryakka, Helper) {
        var token = localStorage.getItem(TOKEN);
        var userID = Helper.deserializeJWT(token).userId;
        luxuryakka.getUserById(parseInt(userID))
            .then(function (response) {
                $scope.user = response.data;
            });
    }])
    .controller('RegisterCtrl', ['$scope', 'luxuryakka', '$location',
        function($scope, luxuryakka, $location) {
            $scope.user = {};

            $scope.registerAccount = function () {
                luxuryakka.registerAccount($scope.user)
                    .then(function () {
                        $location.path('/login');
                    }, function (err) {

                    });
            };
        }])
    .controller('LoginCtrl', ['$scope','luxuryakka', 'TOKEN','$location','$rootScope',
        function($scope, luxuryakka, TOKEN, $location, $rootScope) {

            $scope.formValidation = false;
            $scope.errorMsg = null;

            $scope.login = function () {
                $scope.formValidation = true;
                $scope.errorMsg = null;
                var username = $("#username").val();
                var password = $("#password").val();
                makeLoginRequest(username, password);
            };

            function makeLoginRequest(user, pass) {
                $scope.requestSend = true;
                luxuryakka.authenticate(user, pass)
                    .then(function (response) {
                        $scope.requestSend = false;
                        console.log(JSON.stringify(response));

                        localStorage.setItem(TOKEN, response.data.token);
                        $rootScope.userSet = response.data.token ;
                        $location.path('/dashboard');
                    }, function (err) {
                        $scope.errorMsg = err.data;
                        $scope.requestSend = false;
                        localStorage.removeItem(TOKEN);
                    });
            }
        }])
        .controller('LogoutCtrl', ['$scope', 'TOKEN','$location',
        function ($scope, TOKEN, $location) {

            if(localStorage.getItem(TOKEN)) {
                localStorage.removeItem(TOKEN);
                location.reload(true);
            }
            $location.path('/login');
        }]);
});
