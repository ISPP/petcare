'use strict';

angular.module('petcareApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


