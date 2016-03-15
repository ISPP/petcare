'use strict';

angular.module('petcareApp').controller('ActorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Actor', 'MessageFolder', 'Photo', 'Comment',
        function($scope, $stateParams, $uibModalInstance, entity, Actor, MessageFolder, Photo, Comment) {

        $scope.actor = entity;
        $scope.messagefolders = MessageFolder.query();
        $scope.photos = Photo.query();
        $scope.comments = Comment.query();
        $scope.load = function(id) {
            Actor.get({id : id}, function(result) {
                $scope.actor = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('petcareApp:actorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.actor.id != null) {
                Actor.update($scope.actor, onSaveSuccess, onSaveError);
            } else {
                Actor.save($scope.actor, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
