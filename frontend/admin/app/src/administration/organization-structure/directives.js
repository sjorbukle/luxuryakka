define(['angular'], function(angular) {
    angular.module('luxuryakka.organizationStructure')
        .directive('laOrgStructBreadcrumb', ['orgStructureService', '$q', function (orgStructureService, $q) {
            return {
                restrict: 'A',
                scope: {
                    item: '=',
                    showDetails: '='
                },
                templateUrl: 'src/administration/organization-structure/views/directives/breadcrumb.html',
                link: function(scope, element, attrs, controller) {

                    scope.$watch('item', function (item){

                        if (item && item.hasOwnProperty('treePath')){
                            if(scope.showDetails) {
                                var treePath = scope.item.treePath.split('.');
                                scope.bcItemPromises = [];
                                scope.bcItems = [];

                                treePath.forEach(function(entry) {
                                    scope.bcItemPromises.push(orgStructureService.getOrganizationStructureById(entry));
                                });

                                $q.all(scope.bcItemPromises)
                                .then(function (resolvedResponse) {
                                    resolvedResponse.forEach(function(dataEntry) {
                                        scope.bcItems.push(dataEntry.data);
                                    });
                                });
                            }
                       }
                    });
                }
            }

    }]);
});