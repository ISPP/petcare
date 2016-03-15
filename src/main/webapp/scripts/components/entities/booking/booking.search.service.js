'use strict';

angular.module('petcareApp')
    .factory('BookingSearch', function ($resource) {
        return $resource('api/_search/bookings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
