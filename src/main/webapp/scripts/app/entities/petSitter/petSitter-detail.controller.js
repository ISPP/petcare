'use strict';

angular.module('petcareApp')
    .controller('PetSitterDetailController', function ($scope, $rootScope, $stateParams, entity, PetSitter, Pet, Place) {
        $scope.petSitter = entity;
        $scope.load = function (id) {
            PetSitter.get({id: id}, function(result) {
                $scope.petSitter = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:petSitterUpdate', function(event, result) {
            $scope.petSitter = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
