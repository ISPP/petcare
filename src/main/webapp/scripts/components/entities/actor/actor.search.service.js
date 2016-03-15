'use strict';

angular.module('petcareApp')
    .factory('ActorSearch', function ($resource) {
        return $resource('api/_search/actors/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
