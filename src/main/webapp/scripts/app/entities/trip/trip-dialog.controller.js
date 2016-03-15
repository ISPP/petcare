'use strict';

angular.module('petcareApp').controller('TripDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trip', 'Vehicle',
        function($scope, $stateParams, $uibModalInstance, entity, Trip, Vehicle) {

        $scope.trip = entity;
        $scope.vehicles = Vehicle.query();
        $scope.load = function(id) {
            Trip.get({id : id}, function(result) {
                $scope.trip = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:tripUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.trip.id != null) {
                Trip.update($scope.trip, onSaveSuccess, onSaveError);
            } else {
                Trip.save($scope.trip, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForMoment = {};

        $scope.datePickerForMoment.status = {
            opened: false
        };

        $scope.datePickerForMomentOpen = function($event) {
            $scope.datePickerForMoment.status.opened = true;
        };
}]);
