'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('place', {
                parent: 'entity',
                url: '/places',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.place.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/place/places.html',
                        controller: 'PlaceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('place');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('place.detail', {
                parent: 'entity',
                url: '/place/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.place.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/place/place-detail.html',
                        controller: 'PlaceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('place');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Place', function($stateParams, Place) {
                        return Place.get({id : $stateParams.id});
                    }]
                }
            })
            .state('place.new', {
                parent: 'place',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/place/place-dialog.html',
                        controller: 'PlaceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    address: null,
                                    city: null,
                                    hasGarden: false,
                                    hasPatio: false,
                                    building: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('place', null, { reload: true });
                    }, function() {
                        $state.go('place');
                    })
                }]
            })
            .state('place.edit', {
                parent: 'place',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/place/place-dialog.html',
                        controller: 'PlaceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Place', function(Place) {
                                return Place.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('place', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('place.delete', {
                parent: 'place',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/place/place-delete-dialog.html',
                        controller: 'PlaceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Place', function(Place) {
                                return Place.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('place', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
