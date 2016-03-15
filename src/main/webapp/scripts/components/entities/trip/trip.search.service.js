'use strict';

angular.module('petcareApp')
    .factory('TripSearch', function ($resource) {
        return $resource('api/_search/trips/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
