define(['angular'], function(angular) {
    angular.module(
        'luxuryakka.administration', [
            'luxuryakka.generalSettings',
            'luxuryakka.organizationStructure',
            'luxuryakka.generalSettings'
        ]
    );

    angular.module('luxuryakka.administrationBackend', []);
});