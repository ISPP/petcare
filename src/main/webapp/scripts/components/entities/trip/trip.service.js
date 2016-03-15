'use strict';

angular.module('petcareApp')
    .factory('Trip', function ($resource, DateUtils) {
        return $resource('api/trips/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.moment = DateUtils.convertDateTimeFromServer(data.moment);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
