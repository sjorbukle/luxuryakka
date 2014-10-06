/**
 * Created by Laplacian d.o.o. on 5/18/14.
 */
angular.module('angularseed.login-service', [])
// Factory for HttpInterceptor logic
.factory('loginValidatorHttpInterceptor', ['$q', function($q) {
    return {
        'request': function(config) {
            console.log('request:' + JSON.stringify(config));
            return $q.when(config);
        },
        'response': function(response) {
            return $q.when(response);
        },
        'responseError': function(rejection) {
            console.log(' > Response error:' + JSON.stringify(rejection));
            return $q.reject(rejection);
        }
    };
}])
// Authorization header injector
.factory('authentication', ['$http',
    function ($http) {
        return {
            setAuth : function (token) {
                $http.defaults.headers.common['Authorization'] = token;
            },
            resetAuth : function () {
                delete $http.defaults.headers.common['Authorization'];
            }
        };
    }])
// Authorization header injector
.factory('LoginService', function () {
        return true; // FIXME: change this to proper authentication request
});
