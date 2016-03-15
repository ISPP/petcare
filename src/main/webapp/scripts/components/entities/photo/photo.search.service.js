'use strict';

angular.module('petcareApp')
    .factory('PhotoSearch', function ($resource) {
        return $resource('api/_search/photos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
