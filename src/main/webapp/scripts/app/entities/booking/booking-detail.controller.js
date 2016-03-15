'use strict';

angular.module('petcareApp')
    .controller('BookingDetailController', function ($scope, $rootScope, $stateParams, entity, Booking, PetOwner, Supplier, Review) {
        $scope.booking = entity;
        $scope.load = function (id) {
            Booking.get({id: id}, function(result) {
                $scope.booking = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:bookingUpdate', function(event, result) {
            $scope.booking = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
