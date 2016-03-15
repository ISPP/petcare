'use strict';

angular.module('petcareApp')
    .controller('ReviewDetailController', function ($scope, $rootScope, $stateParams, entity, Review, PetOwner, Supplier, Booking) {
        $scope.review = entity;
        $scope.load = function (id) {
            Review.get({id: id}, function(result) {
                $scope.review = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:reviewUpdate', function(event, result) {
            $scope.review = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
