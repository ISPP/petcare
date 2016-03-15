'use strict';

angular.module('petcareApp').controller('ReviewDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Review', 'PetOwner', 'Supplier', 'Booking',
        function($scope, $stateParams, $uibModalInstance, entity, Review, PetOwner, Supplier, Booking) {

        $scope.review = entity;
        $scope.petowners = PetOwner.query();
        $scope.suppliers = Supplier.query();
        $scope.bookings = Booking.query();
        $scope.load = function(id) {
            Review.get({id : id}, function(result) {
                $scope.review = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:reviewUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.review.id != null) {
                Review.update($scope.review, onSaveSuccess, onSaveError);
            } else {
                Review.save($scope.review, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreationMoment = {};

        $scope.datePickerForCreationMoment.status = {
            opened: false
        };

        $scope.datePickerForCreationMomentOpen = function($event) {
            $scope.datePickerForCreationMoment.status.opened = true;
        };
}]);
