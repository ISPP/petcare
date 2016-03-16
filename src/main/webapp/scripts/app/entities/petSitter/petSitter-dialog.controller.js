'use strict';

angular.module('petcareApp').controller('PetSitterDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PetSitter', 'Pet', 'Place', 'Supplier',
        function($scope, $stateParams, $uibModalInstance, $q, entity, PetSitter, Pet, Place, Supplier) {

        $scope.petSitter = entity;
        $scope.pets = Pet.query();
        $scope.places = Place.query();
        $scope.suppliers = Supplier.query({filter: 'petsitter-is-null'});
        $q.all([$scope.petSitter.$promise, $scope.suppliers.$promise]).then(function() {
            if (!$scope.petSitter.supplier || !$scope.petSitter.supplier.id) {
                return $q.reject();
            }
            return Supplier.get({id : $scope.petSitter.supplier.id}).$promise;
        }).then(function(supplier) {
            $scope.suppliers.push(supplier);
        });
        $scope.load = function(id) {
            PetSitter.get({id : id}, function(result) {
                $scope.petSitter = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:petSitterUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.petSitter.id != null) {
                PetSitter.update($scope.petSitter, onSaveSuccess, onSaveError);
            } else {
                PetSitter.save($scope.petSitter, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
