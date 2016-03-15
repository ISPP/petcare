'use strict';

angular.module('petcareApp')
    .controller('PetSitterController', function ($scope, $state, PetSitter, PetSitterSearch, ParseLinks) {

        $scope.petSitters = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PetSitter.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.petSitters = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PetSitterSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.petSitters = result;
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
            $scope.petSitter = {
                priceNight: null,
                priceHour: null,
                id: null
            };
        };
    });
