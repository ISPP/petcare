'use strict';

angular.module('petcareApp')
    .controller('SupplierDetailController', function ($scope, $rootScope, $stateParams, entity, Supplier, Review, Booking, Customer, PetShipper, PetSitter, Company) {
        $scope.supplier = entity;
        $scope.load = function (id) {
            Supplier.get({id: id}, function(result) {
                $scope.supplier = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:supplierUpdate', function(event, result) {
            $scope.supplier = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
