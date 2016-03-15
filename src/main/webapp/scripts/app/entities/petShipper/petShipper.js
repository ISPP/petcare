'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('petShipper', {
                parent: 'entity',
                url: '/petShippers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.petShipper.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petShipper/petShippers.html',
                        controller: 'PetShipperController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petShipper');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('petShipper.detail', {
                parent: 'entity',
                url: '/petShipper/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.petShipper.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petShipper/petShipper-detail.html',
                        controller: 'PetShipperDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petShipper');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PetShipper', function($stateParams, PetShipper) {
                        return PetShipper.get({id : $stateParams.id});
                    }]
                }
            })
            .state('petShipper.new', {
                parent: 'petShipper',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petShipper/petShipper-dialog.html',
                        controller: 'PetShipperDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('petShipper', null, { reload: true });
                    }, function() {
                        $state.go('petShipper');
                    })
                }]
            })
            .state('petShipper.edit', {
                parent: 'petShipper',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petShipper/petShipper-dialog.html',
                        controller: 'PetShipperDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PetShipper', function(PetShipper) {
                                return PetShipper.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petShipper', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('petShipper.delete', {
                parent: 'petShipper',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petShipper/petShipper-delete-dialog.html',
                        controller: 'PetShipperDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PetShipper', function(PetShipper) {
                                return PetShipper.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petShipper', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
