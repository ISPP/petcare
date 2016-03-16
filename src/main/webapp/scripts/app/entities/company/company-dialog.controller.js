'use strict';

angular.module('petcareApp').controller('CompanyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Company', 'Supplier',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Company, Supplier) {

        $scope.company = entity;
        $scope.suppliers = Supplier.query({filter: 'company-is-null'});
        $q.all([$scope.company.$promise, $scope.suppliers.$promise]).then(function() {
            if (!$scope.company.supplier || !$scope.company.supplier.id) {
                return $q.reject();
            }
            return Supplier.get({id : $scope.company.supplier.id}).$promise;
        }).then(function(supplier) {
            $scope.suppliers.push(supplier);
        });
        $scope.load = function(id) {
            Company.get({id : id}, function(result) {
                $scope.company = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:companyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.company.id != null) {
                Company.update($scope.company, onSaveSuccess, onSaveError);
            } else {
                Company.save($scope.company, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
