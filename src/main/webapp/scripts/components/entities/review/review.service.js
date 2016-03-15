'use strict';

angular.module('petcareApp')
    .factory('Review', function ($resource, DateUtils) {
        return $resource('api/reviews/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationMoment = DateUtils.convertDateTimeFromServer(data.creationMoment);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
