'use strict';

angular.module('petcareApp')
	.controller('ReviewDeleteController', function($scope, $uibModalInstance, entity, Review) {

        $scope.review = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Review.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
