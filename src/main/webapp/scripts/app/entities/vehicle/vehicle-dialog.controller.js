'use strict';

angular.module('petcareApp').controller('VehicleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Vehicle', 'PetShipper', 'Trip',
        function($scope, $stateParams, $uibModalInstance, entity, Vehicle, PetShipper, Trip) {

        $scope.vehicle = entity;
        $scope.petshippers = PetShipper.query();
        $scope.trips = Trip.query();
        $scope.load = function(id) {
            Vehicle.get({id : id}, function(result) {
                $scope.vehicle = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:vehicleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.vehicle.id != null) {
                Vehicle.update($scope.vehicle, onSaveSuccess, onSaveError);
            } else {
                Vehicle.save($scope.vehicle, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
