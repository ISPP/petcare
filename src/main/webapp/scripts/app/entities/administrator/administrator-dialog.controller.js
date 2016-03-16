'use strict';

angular.module('petcareApp').controller('AdministratorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Administrator', 'Complaint', 'User',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Administrator, Complaint, User) {

        $scope.administrator = entity;
        $scope.complaints = Complaint.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Administrator.get({id : id}, function(result) {
                $scope.administrator = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:administratorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.administrator.id != null) {
                Administrator.update($scope.administrator, onSaveSuccess, onSaveError);
            } else {
                Administrator.save($scope.administrator, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
