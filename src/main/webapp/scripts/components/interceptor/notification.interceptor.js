 'use strict';

angular.module('petcareApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-petcareApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-petcareApp-params')});
                }
                return response;
            }
        };
    });
