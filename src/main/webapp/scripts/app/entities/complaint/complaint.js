'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('complaint', {
                parent: 'entity',
                url: '/complaints',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.complaint.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/complaint/complaints.html',
                        controller: 'ComplaintController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('complaint');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('complaint.detail', {
                parent: 'entity',
                url: '/complaint/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.complaint.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/complaint/complaint-detail.html',
                        controller: 'ComplaintDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('complaint');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Complaint', function($stateParams, Complaint) {
                        return Complaint.get({id : $stateParams.id});
                    }]
                }
            })
            .state('complaint.new', {
                parent: 'complaint',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/complaint/complaint-dialog.html',
                        controller: 'ComplaintDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    description: null,
                                    resolution: null,
                                    status: null,
                                    creationMoment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('complaint', null, { reload: true });
                    }, function() {
                        $state.go('complaint');
                    })
                }]
            })
            .state('complaint.edit', {
                parent: 'complaint',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/complaint/complaint-dialog.html',
                        controller: 'ComplaintDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Complaint', function(Complaint) {
                                return Complaint.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('complaint', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('complaint.delete', {
                parent: 'complaint',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/complaint/complaint-delete-dialog.html',
                        controller: 'ComplaintDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Complaint', function(Complaint) {
                                return Complaint.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('complaint', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
