'use strict';

angular.module('petcareApp').controller('AdministratorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Administrator', 'Complaint',
        function($scope, $stateParams, $uibModalInstance, entity, Administrator, Complaint) {

        $scope.administrator = entity;
        $scope.complaints = Complaint.query();
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
