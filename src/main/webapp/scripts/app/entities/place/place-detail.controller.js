'use strict';

angular.module('petcareApp')
    .controller('PlaceDetailController', function ($scope, $rootScope, $stateParams, entity, Place, PetSitter) {
        $scope.place = entity;
        $scope.load = function (id) {
            Place.get({id: id}, function(result) {
                $scope.place = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:placeUpdate', function(event, result) {
            $scope.place = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
