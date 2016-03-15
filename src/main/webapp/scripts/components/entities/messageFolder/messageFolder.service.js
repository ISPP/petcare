'use strict';

angular.module('petcareApp')
    .factory('MessageFolder', function ($resource, DateUtils) {
        return $resource('api/messageFolders/:id', {}, {
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
