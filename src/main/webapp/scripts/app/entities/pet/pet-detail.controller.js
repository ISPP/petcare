'use strict';

angular.module('petcareApp')
    .controller('PetDetailController', function ($scope, $rootScope, $stateParams, entity, Pet, PetOwner, PetSitter, Photo) {
        $scope.pet = entity;
        $scope.load = function (id) {
            Pet.get({id: id}, function(result) {
                $scope.pet = result;
            });

        };
        var unsubscribe = $rootScope.$on('petcareApp:petUpdate', function(event, result) {
            $scope.pet = result;
        });
        $scope.setImage = function(imageUrl) {
            $scope.mainImageUrl = imageUrl;
        };
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
