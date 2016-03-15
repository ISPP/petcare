'use strict';

angular.module('petcareApp')
    .factory('PetShipper', function ($resource, DateUtils) {
        return $resource('api/petShippers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
