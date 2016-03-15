'use strict';

angular.module('petcareApp').controller('PetOwnerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PetOwner', 'Pet', 'Review', 'Booking',
        function($scope, $stateParams, $uibModalInstance, entity, PetOwner, Pet, Review, Booking) {

        $scope.petOwner = entity;
        $scope.pets = Pet.query();
        $scope.reviews = Review.query();
        $scope.bookings = Booking.query();
        $scope.load = function(id) {
            PetOwner.get({id : id}, function(result) {
                $scope.petOwner = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:petOwnerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.petOwner.id != null) {
                PetOwner.update($scope.petOwner, onSaveSuccess, onSaveError);
            } else {
                PetOwner.save($scope.petOwner, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
