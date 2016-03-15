'use strict';

angular.module('petcareApp').controller('MessageFolderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'MessageFolder', 'Message', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, MessageFolder, Message, User) {

        $scope.messageFolder = entity;
        $scope.messages = Message.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            MessageFolder.get({id : id}, function(result) {
                $scope.messageFolder = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:messageFolderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.messageFolder.id != null) {
                MessageFolder.update($scope.messageFolder, onSaveSuccess, onSaveError);
            } else {
                MessageFolder.save($scope.messageFolder, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
