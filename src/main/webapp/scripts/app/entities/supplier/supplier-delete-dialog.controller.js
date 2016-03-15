'use strict';

angular.module('petcareApp')
	.controller('SupplierDeleteController', function($scope, $uibModalInstance, entity, Supplier) {

        $scope.supplier = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Supplier.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
