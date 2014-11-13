define(['angular'], function(angular) {
    angular.module('luxuryakka.controller.orgStructureModule', [])
    .controller('OrgStructureCtrl', ['$scope', '$routeParams', 'TOKEN', 'orgStructureService', 'Helper',
    function($scope, $routeParams, TOKEN, orgStructureService, Helper) {
        var parentIdParam = $routeParams.parentId;

        console.log(parentIdParam);

        orgStructureService.getAllOrganizationStructureByParent(parentIdParam)
        .then(function (items) {
            $scope.items = items.data;
        });

        $scope.doesItemHaveChild = function(entityType){
            return entityType != 'CITY';
        };
    }])
    .controller('OrgStructureFirstCallCtrl', ['$scope', '$routeParams', 'TOKEN', 'orgStructureService', 'Helper',
    function($scope, $routeParams, TOKEN, orgStructureService, Helper) {

        orgStructureService.getAllOrganizationStructureParentLess()
        .then(function (items) {
            $scope.items = items.data;
        });

        $scope.doesItemHaveChild = function(entityType){
            return entityType != 'CITY';
        };
    }])
    .controller('OrgStructureCreateCtrl', ['$scope', '$routeParams', 'TOKEN', 'orgStructureService', 'Helper',
        function($scope, $routeParams, TOKEN, orgStructureService, Helper) {
            $scope.entityType = '';
            $scope.selectedParent = {};
            $scope.parents = [{name: "PERO"}, {name: "SIME"}];

            $scope.entityTypes = [{name: 'COUNTRY'}, {name: 'REGION'}, {name: 'RIVIERA'}, {name: 'CITY'}];

            $scope.detailDescription = '';

    }])
});
