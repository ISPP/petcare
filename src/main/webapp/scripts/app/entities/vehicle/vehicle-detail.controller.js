'use strict';

angular.module('petcareApp')
    .controller('VehicleDetailController', function ($scope, $rootScope, $stateParams, entity, Vehicle, PetShipper, Trip) {
        $scope.vehicle = entity;
        $scope.load = function (id) {
            Vehicle.get({id: id}, function(result) {
                $scope.vehicle = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:vehicleUpdate', function(event, result) {
            $scope.vehicle = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
