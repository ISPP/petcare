'use strict';

angular.module('petcareApp')
	.controller('PetSitterDeleteController', function($scope, $uibModalInstance, entity, PetSitter) {

        $scope.petSitter = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PetSitter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
