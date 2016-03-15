'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('supplier', {
                parent: 'entity',
                url: '/suppliers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.supplier.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supplier/suppliers.html',
                        controller: 'SupplierController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('supplier');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('supplier.detail', {
                parent: 'entity',
                url: '/supplier/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.supplier.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supplier/supplier-detail.html',
                        controller: 'SupplierDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('supplier');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Supplier', function($stateParams, Supplier) {
                        return Supplier.get({id : $stateParams.id});
                    }]
                }
            })
            .state('supplier.new', {
                parent: 'supplier',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/supplier/supplier-dialog.html',
                        controller: 'SupplierDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    rating: null,
                                    blocked: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('supplier', null, { reload: true });
                    }, function() {
                        $state.go('supplier');
                    })
                }]
            })
            .state('supplier.edit', {
                parent: 'supplier',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/supplier/supplier-dialog.html',
                        controller: 'SupplierDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Supplier', function(Supplier) {
                                return Supplier.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('supplier', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('supplier.delete', {
                parent: 'supplier',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/supplier/supplier-delete-dialog.html',
                        controller: 'SupplierDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Supplier', function(Supplier) {
                                return Supplier.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('supplier', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
