'use strict';

angular.module('petcareApp').controller('CommentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comment', 'User', 'Complaint',
        function($scope, $stateParams, $uibModalInstance, entity, Comment, User, Complaint) {

        $scope.comment = entity;
        $scope.users = User.query();
        $scope.complaints = Complaint.query();
        $scope.load = function(id) {
            Comment.get({id : id}, function(result) {
                $scope.comment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:commentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.comment.id != null) {
                Comment.update($scope.comment, onSaveSuccess, onSaveError);
            } else {
                Comment.save($scope.comment, onSaveSuccess, onSaveError);
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
