'use strict';

angular.module('petcareApp').controller('PlaceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Place', 'PetSitter',
        function($scope, $stateParams, $uibModalInstance, entity, Place, PetSitter) {

        $scope.place = entity;
        $scope.petsitters = PetSitter.query();
        $scope.load = function(id) {
            Place.get({id : id}, function(result) {
                $scope.place = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:placeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.place.id != null) {
                Place.update($scope.place, onSaveSuccess, onSaveError);
            } else {
                Place.save($scope.place, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
