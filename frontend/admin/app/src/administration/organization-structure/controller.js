define(['angular'], function(angular) {
    angular.module('luxuryakka.organizationStructure')
        .controller('OrgStructureCtrl', ['$scope', '$routeParams', 'orgStructureService',
            function($scope, $routeParams, orgStructureService) {
                var parentIdParam = $routeParams.parentId;

                $scope.entity = {
                    showDetails: true
                };

                orgStructureService.getOrganizationStructureById(parentIdParam)
                .then(function (result) {
                    $scope.entity.item = result.data;
                });

                orgStructureService.getAllOrganizationStructureByParent(parentIdParam)
                .then(function (result) {
                    $scope.entity.children = result.data;
                });

                $scope.doesItemHaveChild = function(entityType){
                    return entityType != 'CITY';
                };
            }])
        .controller('OrgStructureFirstCallCtrl', ['$scope', '$routeParams', 'orgStructureService',
            function($scope, $routeParams, orgStructureService) {

                $scope.entity = {
                    showDetails: false
                };

                orgStructureService.getAllOrganizationStructureParentLess()
                .then(function (result) {
                    $scope.entity.children = result.data;
                });

                $scope.doesItemHaveChild = function(entityType){
                    return entityType != 'CITY';
                };
            }])
        .controller('OrgStructureCreateCtrl', ['$scope', '$routeParams', '$location', 'orgStructureService',
            function($scope, $routeParams, $location, orgStructureService) {
                $scope.entity = {
                    entityType: '',
                    parentsDisabled: false
                };

                orgStructureService.getAllOrganizationStructureTypes()
                .then(function (result) {
                    $scope.entity.entityTypes = result.data;
                });

                $scope.$watch('entity.entityType', function (current, old) {
                    console.log(JSON.stringify(current));
                    if(current.hasOwnProperty('parent')) {
                        if(current.name == 'COUNTRY') {
                            $scope.entity.parentSearchTextDescription = "Disabled. Parent is not allowed for COUNTRY type.";
                            $scope.entity.parentsDisabled = true;
                        } else {
                            $scope.entity.parentSearchTextDescription = "Please select a parent";
                            $scope.entity.parentsDisabled = false;
                            orgStructureService.getAllOrganizationStructureByType(current.parent)
                                .then(function (result) {
                                    console.log(JSON.stringify(result.data));
                                    $scope.entity.parents = result.data;
                                });
                        }
                    }

                });

                $scope.submitForm = function() {
                    var formJson = {
                        name             : $scope.entity.name,
                        entityType       : $scope.entity.entityType.name,
                        description      : $scope.entity.description,
                        shortDescription : $scope.entity.shortDescription
                    };

                    if ($scope.entity.hasOwnProperty('parent')) {
                        formJson.parentId = $scope.entity.parent.id;
                    }

                    orgStructureService.createOrganizationStructure(formJson)
                    .then(function (response) {
                        $location.path('/administration/organization-structure/' + response.data.id);
                    },
                    function (err) {
                        console.log(JSON.stringify(err));
                    });
                };
            }])
        .controller('OrgStructureEditCtrl', ['$scope', '$routeParams', '$location', 'orgStructureService',
            function($scope, $routeParams, $location, orgStructureService) {
                var idParam = $routeParams.id;
                orgStructureService.getOrganizationStructureById(idParam)
                    .then(function (result) {
                        $scope.entity = angular.copy(result.data);
                        $scope.entity.oldName = angular.copy($scope.entity.name);
                        if($scope.entity.hasOwnProperty('parentId')) {
                            orgStructureService.getOrganizationStructureById($scope.entity.parentId)
                                .then(function (result) {
                                    $scope.entity.parent = angular.copy(result.data);
                                });
                        } else {
                            $scope.entity.parent = {name : '-'};
                        }
                    });

                $scope.submitForm = function() {
                    var formJson = {
                        id               : $scope.entity.id,
                        name             : $scope.entity.name,
                        description      : $scope.entity.description,
                        shortDescription : $scope.entity.shortDescription
                    };

                    orgStructureService.editOrganizationStructure(formJson)
                        .then(function (response) {
                            $location.path('/administration/organization-structure/' + response.data.id);
                        },
                        function (err) {
                            console.log(JSON.stringify(err));
                        });
                };
            }])
});
