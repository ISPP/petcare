'use strict';

angular.module('petcareApp')
	.controller('MessageFolderDeleteController', function($scope, $uibModalInstance, entity, MessageFolder) {

        $scope.messageFolder = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MessageFolder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
