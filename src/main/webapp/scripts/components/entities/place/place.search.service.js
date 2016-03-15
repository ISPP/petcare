'use strict';

angular.module('petcareApp')
    .factory('PlaceSearch', function ($resource) {
        return $resource('api/_search/places/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
