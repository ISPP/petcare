'use strict';

angular.module('petcareApp')
    .factory('PetSitterSearch', function ($resource) {
        return $resource('api/_search/petSitters/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
