'use strict';

angular.module('petcareApp')
    .controller('ComplaintDetailController', function ($scope, $rootScope, $stateParams, entity, Complaint, Administrator, Comment, Customer) {
        $scope.complaint = entity;
        $scope.load = function (id) {
            Complaint.get({id: id}, function(result) {
                $scope.complaint = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:complaintUpdate', function(event, result) {
            $scope.complaint = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
