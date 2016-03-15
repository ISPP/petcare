'use strict';

angular.module('petcareApp').controller('MessageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Message', 'User', 'MessageFolder',
        function($scope, $stateParams, $uibModalInstance, entity, Message, User, MessageFolder) {

        $scope.message = entity;
        $scope.users = User.query();
        $scope.messagefolders = MessageFolder.query();
        $scope.load = function(id) {
            Message.get({id : id}, function(result) {
                $scope.message = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:messageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.message.id != null) {
                Message.update($scope.message, onSaveSuccess, onSaveError);
            } else {
                Message.save($scope.message, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForMoment = {};

        $scope.datePickerForMoment.status = {
            opened: false
        };

        $scope.datePickerForMomentOpen = function($event) {
            $scope.datePickerForMoment.status.opened = true;
        };
}]);
