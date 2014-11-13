define(['angular'], function(angular) {
    angular.module(
        'luxuryakka.administration', [
            'luxuryakka.module.generalSettingsBackend',
            'luxuryakka.organizationStructure',
            'luxuryakka.generalSettings'
        ]
    );

    angular.module('luxuryakka.administrationBackend', []);
});