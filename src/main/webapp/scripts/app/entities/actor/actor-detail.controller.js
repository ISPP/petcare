'use strict';

angular.module('petcareApp')
    .controller('ActorDetailController', function ($scope, $rootScope, $stateParams, entity, Actor, MessageFolder, Photo, Comment) {
        $scope.actor = entity;
        $scope.load = function (id) {
            Actor.get({id: id}, function(result) {
                $scope.actor = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:actorUpdate', function(event, result) {
            $scope.actor = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
