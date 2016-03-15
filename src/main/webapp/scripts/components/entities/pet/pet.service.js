'use strict';

angular.module('petcareApp')
    .factory('Pet', function ($resource, DateUtils) {
        return $resource('api/pets/:id', {}, {
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
