'use strict';

angular.module('petcareApp')
    .controller('TripDetailController', function ($scope, $rootScope, $stateParams, entity, Trip, Vehicle) {
        $scope.trip = entity;
        $scope.load = function (id) {
            Trip.get({id: id}, function(result) {
                $scope.trip = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:tripUpdate', function(event, result) {
            $scope.trip = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
