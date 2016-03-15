'use strict';

angular.module('petcareApp')
    .controller('ComplaintController', function ($scope, $state, Complaint, ComplaintSearch, ParseLinks) {

        $scope.complaints = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Complaint.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.complaints = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ComplaintSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.complaints = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.complaint = {
                title: null,
                description: null,
                resolution: null,
                status: null,
                creationMoment: null,
                id: null
            };
        };
    });
