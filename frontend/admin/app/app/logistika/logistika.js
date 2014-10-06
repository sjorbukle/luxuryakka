/**
 * Created by Laplacian d.o.o. on 5/14/14.
 */

angular.module('logistika.core', ['ui.router','angularseed.login-service'])
.config(['$stateProvider', function ($stateProvider) {
    $stateProvider
        .state('app.orderlist', {
            url: 'orderlist',
            views: {
                'content': {
                    templateUrl: 'app/logistika/views/orderlist.html'
                },
                'hints_bar': {
                    template: '<p>Orderlist details</p>'
                }
            }
        })
        .state('app.general-settings', {
            url: 'general-settings',
            views: {
                'content': {
                    templateUrl: 'app/logistika/views/general-settings.html',
                    controller: 'GeneralSettingsCtrl'
                }
            }
        });
}]);
