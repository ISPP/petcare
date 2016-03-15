'use strict';

angular.module('petcareApp')
    .factory('ComplaintSearch', function ($resource) {
        return $resource('api/_search/complaints/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
