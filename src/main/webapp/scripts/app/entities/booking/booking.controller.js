'use strict';

angular.module('petcareApp')
    .controller('BookingController', function ($scope, $state, Booking, BookingSearch, ParseLinks) {

        $scope.bookings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Booking.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.bookings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BookingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bookings = result;
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
            $scope.booking = {
                code: null,
                description: null,
                startMoment: null,
                endMoment: null,
                status: null,
                price: null,
                night: false,
                id: null
            };
        };
    });
