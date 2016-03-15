'use strict';

angular.module('petcareApp')
    .controller('PetOwnerDetailController', function ($scope, $rootScope, $stateParams, entity, PetOwner, Pet, Review, Booking) {
        $scope.petOwner = entity;
        $scope.load = function (id) {
            PetOwner.get({id: id}, function(result) {
                $scope.petOwner = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:petOwnerUpdate', function(event, result) {
            $scope.petOwner = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
