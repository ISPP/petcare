'use strict';

angular.module('petcareApp')
    .factory('Photo', function ($resource, DateUtils) {
        return $resource('api/photos/:id', {}, {
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
