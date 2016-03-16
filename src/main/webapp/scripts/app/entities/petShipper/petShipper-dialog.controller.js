'use strict';

angular.module('petcareApp').controller('PetShipperDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PetShipper', 'Vehicle', 'Supplier',
        function($scope, $stateParams, $uibModalInstance, $q, entity, PetShipper, Vehicle, Supplier) {

        $scope.petShipper = entity;
        $scope.vehicles = Vehicle.query();
        $scope.suppliers = Supplier.query({filter: 'petshipper-is-null'});
        $q.all([$scope.petShipper.$promise, $scope.suppliers.$promise]).then(function() {
            if (!$scope.petShipper.supplier || !$scope.petShipper.supplier.id) {
                return $q.reject();
            }
            return Supplier.get({id : $scope.petShipper.supplier.id}).$promise;
        }).then(function(supplier) {
            $scope.suppliers.push(supplier);
        });
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
