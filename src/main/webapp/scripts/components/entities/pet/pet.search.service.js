'use strict';

angular.module('petcareApp')
    .factory('PetSearch', function ($resource) {
        return $resource('api/_search/pets/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
