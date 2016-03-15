'use strict';

angular.module('petcareApp')
    .factory('AdministratorSearch', function ($resource) {
        return $resource('api/_search/administrators/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
