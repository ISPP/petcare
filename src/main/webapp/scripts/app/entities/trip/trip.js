'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trip', {
                parent: 'entity',
                url: '/trips',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.trip.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/trip/trips.html',
                        controller: 'TripController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trip');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trip.detail', {
                parent: 'entity',
                url: '/trip/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.trip.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/trip/trip-detail.html',
                        controller: 'TripDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trip');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Trip', function($stateParams, Trip) {
                        return Trip.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trip.new', {
                parent: 'trip',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/trip/trip-dialog.html',
                        controller: 'TripDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    descriptionText: null,
                                    distance: null,
                                    moment: null,
                                    cost: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('trip', null, { reload: true });
                    }, function() {
                        $state.go('trip');
                    })
                }]
            })
            .state('trip.edit', {
                parent: 'trip',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/trip/trip-dialog.html',
                        controller: 'TripDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Trip', function(Trip) {
                                return Trip.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trip', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('trip.delete', {
                parent: 'trip',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/trip/trip-delete-dialog.html',
                        controller: 'TripDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Trip', function(Trip) {
                                return Trip.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trip', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
