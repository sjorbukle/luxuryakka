define(['angular'], function(angular) {
    angular.module('luxuryakka.user')
        .controller('ProfileCtrl', ['$scope', 'TOKEN', 'userService', 'Helper',
            function($scope, TOKEN, userService, Helper) {
                var token = localStorage.getItem(TOKEN);
                var userID = Helper.deserializeJWT(token).userId;
                userService.getUserById(parseInt(userID))
                    .then(function (response) {
                        $scope.user = response.data;
                    });
            }])
        .controller('RegisterCtrl', ['$scope', 'userService', '$location',
            function($scope, userService, $location) {
                $scope.user = {};

                $scope.registerAccount = function () {
                    userService.registerAccount($scope.user)
                        .then(function () {
                            $location.path('/login');
                        }, function (err) {

                        });
                };
            }])
        .controller('LoginCtrl', ['$scope','authenticationService', 'TOKEN','$location','$rootScope',
            function($scope, authenticationService, TOKEN, $location, $rootScope) {

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
                    authenticationService.authenticate(user, pass)
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
