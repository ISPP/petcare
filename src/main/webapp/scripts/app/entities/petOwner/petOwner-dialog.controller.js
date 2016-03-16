'use strict';

angular.module('petcareApp').controller('PetOwnerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PetOwner', 'Pet', 'Review', 'Booking', 'Customer',
        function($scope, $stateParams, $uibModalInstance, $q, entity, PetOwner, Pet, Review, Booking, Customer) {

        $scope.petOwner = entity;
        $scope.pets = Pet.query();
        $scope.reviews = Review.query();
        $scope.bookings = Booking.query();
        $scope.customers = Customer.query({filter: 'petowner-is-null'});
        $q.all([$scope.petOwner.$promise, $scope.customers.$promise]).then(function() {
            if (!$scope.petOwner.customer || !$scope.petOwner.customer.id) {
                return $q.reject();
            }
            return Customer.get({id : $scope.petOwner.customer.id}).$promise;
        }).then(function(customer) {
            $scope.customers.push(customer);
        });
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
