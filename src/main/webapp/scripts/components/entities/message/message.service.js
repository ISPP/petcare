'use strict';

angular.module('petcareApp')
    .factory('Message', function ($resource, DateUtils) {
        return $resource('api/messages/:id', {}, {
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
