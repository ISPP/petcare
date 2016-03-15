'use strict';

angular.module('petcareApp')
    .controller('MessageFolderDetailController', function ($scope, $rootScope, $stateParams, entity, MessageFolder, Message, User) {
        $scope.messageFolder = entity;
        $scope.load = function (id) {
            MessageFolder.get({id: id}, function(result) {
                $scope.messageFolder = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:messageFolderUpdate', function(event, result) {
            $scope.messageFolder = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
