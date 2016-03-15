'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('petSitter', {
                parent: 'entity',
                url: '/petSitters',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.petSitter.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petSitter/petSitters.html',
                        controller: 'PetSitterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petSitter');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('petSitter.detail', {
                parent: 'entity',
                url: '/petSitter/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.petSitter.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petSitter/petSitter-detail.html',
                        controller: 'PetSitterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petSitter');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PetSitter', function($stateParams, PetSitter) {
                        return PetSitter.get({id : $stateParams.id});
                    }]
                }
            })
            .state('petSitter.new', {
                parent: 'petSitter',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petSitter/petSitter-dialog.html',
                        controller: 'PetSitterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    priceNight: null,
                                    priceHour: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('petSitter', null, { reload: true });
                    }, function() {
                        $state.go('petSitter');
                    })
                }]
            })
            .state('petSitter.edit', {
                parent: 'petSitter',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petSitter/petSitter-dialog.html',
                        controller: 'PetSitterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PetSitter', function(PetSitter) {
                                return PetSitter.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petSitter', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('petSitter.delete', {
                parent: 'petSitter',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petSitter/petSitter-delete-dialog.html',
                        controller: 'PetSitterDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PetSitter', function(PetSitter) {
                                return PetSitter.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petSitter', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
