'use strict';

angular.module('petcareApp')
    .controller('PhotoDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Photo, Customer, Pet) {
        $scope.photo = entity;
        $scope.load = function (id) {
            Photo.get({id: id}, function(result) {
                $scope.photo = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:photoUpdate', function(event, result) {
            $scope.photo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
