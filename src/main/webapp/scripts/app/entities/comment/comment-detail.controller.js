'use strict';

angular.module('petcareApp')
    .controller('CommentDetailController', function ($scope, $rootScope, $stateParams, entity, Comment, User, Complaint) {
        $scope.comment = entity;
        $scope.load = function (id) {
            Comment.get({id: id}, function(result) {
                $scope.comment = result;
            });
        };
        var unsubscribe = $rootScope.$on('petcareApp:commentUpdate', function(event, result) {
            $scope.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
