'use strict';

angular.module('petcareApp').controller('SupplierDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Supplier', 'Review', 'Booking',
        function($scope, $stateParams, $uibModalInstance, entity, Supplier, Review, Booking) {

        $scope.supplier = entity;
        $scope.reviews = Review.query();
        $scope.bookings = Booking.query();
        $scope.load = function(id) {
            Supplier.get({id : id}, function(result) {
                $scope.supplier = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:supplierUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.supplier.id != null) {
                Supplier.update($scope.supplier, onSaveSuccess, onSaveError);
            } else {
                Supplier.save($scope.supplier, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
