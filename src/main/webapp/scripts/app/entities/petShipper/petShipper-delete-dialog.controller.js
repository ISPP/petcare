'use strict';

angular.module('petcareApp')
	.controller('PetShipperDeleteController', function($scope, $uibModalInstance, entity, PetShipper) {

        $scope.petShipper = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PetShipper.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
