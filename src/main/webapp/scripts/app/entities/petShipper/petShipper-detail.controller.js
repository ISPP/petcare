'use strict';

angular.module('petcareApp')
    .controller('PetShipperDetailController', function ($scope, $rootScope, $stateParams, entity, PetShipper, Vehicle, Supplier) {
        $scope.petShipper = entity;
        $scope.load = function (id) {
            PetShipper.get({id: id}, function(result) {
                $scope.petShipper = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:petShipperUpdate', function(event, result) {
            $scope.petShipper = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
