'use strict';

angular.module('petcareApp')
    .factory('PetShipperSearch', function ($resource) {
        return $resource('api/_search/petShippers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
