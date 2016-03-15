'use strict';

angular.module('petcareApp')
	.controller('PlaceDeleteController', function($scope, $uibModalInstance, entity, Place) {

        $scope.place = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Place.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
