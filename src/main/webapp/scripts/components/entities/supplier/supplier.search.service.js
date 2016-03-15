'use strict';

angular.module('petcareApp')
    .factory('SupplierSearch', function ($resource) {
        return $resource('api/_search/suppliers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
