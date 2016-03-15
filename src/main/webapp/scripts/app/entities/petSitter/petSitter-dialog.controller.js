'use strict';

angular.module('petcareApp').controller('PetSitterDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PetSitter', 'Pet', 'Place',
        function($scope, $stateParams, $uibModalInstance, entity, PetSitter, Pet, Place) {

        $scope.petSitter = entity;
        $scope.pets = Pet.query();
        $scope.places = Place.query();
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
