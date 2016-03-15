'use strict';

angular.module('petcareApp')
    .factory('MessageFolderSearch', function ($resource) {
        return $resource('api/_search/messageFolders/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
