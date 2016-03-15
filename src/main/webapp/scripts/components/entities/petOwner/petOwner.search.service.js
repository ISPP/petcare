'use strict';

angular.module('petcareApp')
    .factory('PetOwnerSearch', function ($resource) {
        return $resource('api/_search/petOwners/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
