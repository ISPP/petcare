'use strict';

angular.module('petcareApp')
    .controller('AdministratorDetailController', function ($scope, $rootScope, $stateParams, entity, Administrator, Complaint) {
        $scope.administrator = entity;
        $scope.load = function (id) {
            Administrator.get({id: id}, function(result) {
                $scope.administrator = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:administratorUpdate', function(event, result) {
            $scope.administrator = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
