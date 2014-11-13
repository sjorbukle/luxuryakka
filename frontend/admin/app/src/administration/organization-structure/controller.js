define(['angular'], function(angular) {
    angular.module('luxuryakka.organizationStructure')
        .controller('OrgStructureCtrl', ['$scope', '$routeParams', 'orgStructureService',
            function($scope, $routeParams, orgStructureService) {
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
        .controller('OrgStructureFirstCallCtrl', ['$scope', '$routeParams', 'orgStructureService',
            function($scope, $routeParams, orgStructureService) {

                orgStructureService.getAllOrganizationStructureParentLess()
                    .then(function (items) {
                        $scope.items = items.data;
                    });

                $scope.doesItemHaveChild = function(entityType){
                    return entityType != 'CITY';
                };
            }])
        .controller('OrgStructureCreateCtrl', ['$scope', '$routeParams', 'orgStructureService',
            function($scope, $routeParams, orgStructureService) {
                $scope.entityType = '';
                $scope.selectedParent = {};
                $scope.parents = [{name: "PERO"}, {name: "SIME"}];

                $scope.entityTypes = [{name: 'COUNTRY'}, {name: 'REGION'}, {name: 'RIVIERA'}, {name: 'CITY'}];

                $scope.detailDescription = '';

            }])
});
