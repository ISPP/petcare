'use strict';

angular.module('petcareApp').controller('PetDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pet', 'PetOwner', 'PetSitter', 'Photo',
        function($scope, $stateParams, $uibModalInstance, entity, Pet, PetOwner, PetSitter, Photo) {

        $scope.pet = entity;
        $scope.petowners = PetOwner.query();
        $scope.petsitters = PetSitter.query();
        $scope.photos = Photo.query();
        $scope.load = function(id) {
            Pet.get({id : id}, function(result) {
                $scope.pet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:petUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pet.id != null) {
                Pet.update($scope.pet, onSaveSuccess, onSaveError);
            } else {
                Pet.save($scope.pet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
