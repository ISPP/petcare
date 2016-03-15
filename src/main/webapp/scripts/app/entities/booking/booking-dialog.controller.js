'use strict';

angular.module('petcareApp').controller('BookingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Booking', 'PetOwner', 'Supplier', 'Review',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Booking, PetOwner, Supplier, Review) {

        $scope.booking = entity;
        $scope.petowners = PetOwner.query();
        $scope.suppliers = Supplier.query();
        $scope.reviews = Review.query({filter: 'booking-is-null'});
        $q.all([$scope.booking.$promise, $scope.reviews.$promise]).then(function() {
            if (!$scope.booking.review || !$scope.booking.review.id) {
                return $q.reject();
            }
            return Review.get({id : $scope.booking.review.id}).$promise;
        }).then(function(review) {
            $scope.reviews.push(review);
        });
        $scope.load = function(id) {
            Booking.get({id : id}, function(result) {
                $scope.booking = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:bookingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.booking.id != null) {
                Booking.update($scope.booking, onSaveSuccess, onSaveError);
            } else {
                Booking.save($scope.booking, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStartMoment = {};

        $scope.datePickerForStartMoment.status = {
            opened: false
        };

        $scope.datePickerForStartMomentOpen = function($event) {
            $scope.datePickerForStartMoment.status.opened = true;
        };
        $scope.datePickerForEndMoment = {};

        $scope.datePickerForEndMoment.status = {
            opened: false
        };

        $scope.datePickerForEndMomentOpen = function($event) {
            $scope.datePickerForEndMoment.status.opened = true;
        };
}]);
