'use strict';

angular.module('petcareApp')
    .controller('MessageDetailController', function ($scope, $rootScope, $stateParams, entity, Message, User, MessageFolder) {
        $scope.message = entity;
        $scope.load = function (id) {
            Message.get({id: id}, function(result) {
                $scope.message = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:messageUpdate', function(event, result) {
            $scope.message = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
