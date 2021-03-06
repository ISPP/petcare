'use strict';

angular.module('petcareApp')
    .factory('Booking', function ($resource, DateUtils) {
        return $resource('api/bookings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startMoment = DateUtils.convertDateTimeFromServer(data.startMoment);
                    data.endMoment = DateUtils.convertDateTimeFromServer(data.endMoment);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
