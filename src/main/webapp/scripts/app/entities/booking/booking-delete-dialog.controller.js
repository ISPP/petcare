'use strict';

angular.module('petcareApp')
	.controller('BookingDeleteController', function($scope, $uibModalInstance, entity, Booking) {

        $scope.booking = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Booking.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
