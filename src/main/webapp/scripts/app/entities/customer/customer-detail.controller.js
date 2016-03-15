'use strict';

angular.module('petcareApp')
    .controller('CustomerDetailController', function ($scope, $rootScope, $stateParams, entity, Customer, Complaint, Photo) {
        $scope.customer = entity;
        $scope.load = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:customerUpdate', function(event, result) {
            $scope.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
