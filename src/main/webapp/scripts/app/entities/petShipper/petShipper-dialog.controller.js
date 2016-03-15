'use strict';

angular.module('petcareApp').controller('PetShipperDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PetShipper', 'Vehicle',
        function($scope, $stateParams, $uibModalInstance, entity, PetShipper, Vehicle) {

        $scope.petShipper = entity;
        $scope.vehicles = Vehicle.query();
        $scope.load = function(id) {
            PetShipper.get({id : id}, function(result) {
                $scope.petShipper = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:petShipperUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.petShipper.id != null) {
                PetShipper.update($scope.petShipper, onSaveSuccess, onSaveError);
            } else {
                PetShipper.save($scope.petShipper, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
