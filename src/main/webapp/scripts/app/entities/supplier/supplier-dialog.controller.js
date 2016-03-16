'use strict';

angular.module('petcareApp').controller('SupplierDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Supplier', 'Review', 'Booking', 'Customer', 'PetShipper', 'PetSitter', 'Company',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Supplier, Review, Booking, Customer, PetShipper, PetSitter, Company) {

        $scope.supplier = entity;
        $scope.reviews = Review.query();
        $scope.bookings = Booking.query();
        $scope.customers = Customer.query({filter: 'supplier-is-null'});
        $q.all([$scope.supplier.$promise, $scope.customers.$promise]).then(function() {
            if (!$scope.supplier.customer || !$scope.supplier.customer.id) {
                return $q.reject();
            }
            return Customer.get({id : $scope.supplier.customer.id}).$promise;
        }).then(function(customer) {
            $scope.customers.push(customer);
        });
        $scope.petshippers = PetShipper.query();
        $scope.petsitters = PetSitter.query();
        $scope.companys = Company.query();
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
