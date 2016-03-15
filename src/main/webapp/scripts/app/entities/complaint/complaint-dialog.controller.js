'use strict';

angular.module('petcareApp').controller('ComplaintDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Complaint', 'Administrator', 'Comment', 'Customer',
        function($scope, $stateParams, $uibModalInstance, entity, Complaint, Administrator, Comment, Customer) {

        $scope.complaint = entity;
        $scope.administrators = Administrator.query();
        $scope.comments = Comment.query();
        $scope.customers = Customer.query();
        $scope.load = function(id) {
            Complaint.get({id : id}, function(result) {
                $scope.complaint = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:complaintUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.complaint.id != null) {
                Complaint.update($scope.complaint, onSaveSuccess, onSaveError);
            } else {
                Complaint.save($scope.complaint, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreationMoment = {};

        $scope.datePickerForCreationMoment.status = {
            opened: false
        };

        $scope.datePickerForCreationMomentOpen = function($event) {
            $scope.datePickerForCreationMoment.status.opened = true;
        };
}]);
