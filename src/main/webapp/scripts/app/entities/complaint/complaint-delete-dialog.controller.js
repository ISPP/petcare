'use strict';

angular.module('petcareApp')
	.controller('ComplaintDeleteController', function($scope, $uibModalInstance, entity, Complaint) {

        $scope.complaint = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Complaint.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
