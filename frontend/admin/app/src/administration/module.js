define(['angular'], function(angular) {
    angular.module(
        'luxuryakka.administration', [
            'luxuryakka.generalSettings',
            'luxuryakka.organizationStructure'
        ]
    );

    angular.module('luxuryakka.administrationBackend', []);
});
