'use strict';

angular.module('petcareApp')
    .factory('VehicleSearch', function ($resource) {
        return $resource('api/_search/vehicles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
