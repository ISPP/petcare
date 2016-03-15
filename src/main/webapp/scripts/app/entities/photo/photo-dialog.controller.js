'use strict';

angular.module('petcareApp').controller('PhotoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Photo', 'Customer', 'Pet',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Photo, Customer, Pet) {

        $scope.photo = entity;
        $scope.customers = Customer.query();
        $scope.pets = Pet.query();
        $scope.load = function(id) {
            Photo.get({id : id}, function(result) {
                $scope.photo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:photoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.photo.id != null) {
                Photo.update($scope.photo, onSaveSuccess, onSaveError);
            } else {
                Photo.save($scope.photo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setContent = function ($file, photo) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        photo.content = base64Data;
                        photo.contentContentType = $file.type;
                    });
                };
            }
        };
}]);
