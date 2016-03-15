'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('messageFolder', {
                parent: 'entity',
                url: '/messageFolders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.messageFolder.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/messageFolder/messageFolders.html',
                        controller: 'MessageFolderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('messageFolder');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('messageFolder.detail', {
                parent: 'entity',
                url: '/messageFolder/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.messageFolder.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/messageFolder/messageFolder-detail.html',
                        controller: 'MessageFolderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('messageFolder');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MessageFolder', function($stateParams, MessageFolder) {
                        return MessageFolder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('messageFolder.new', {
                parent: 'messageFolder',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/messageFolder/messageFolder-dialog.html',
                        controller: 'MessageFolderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('messageFolder', null, { reload: true });
                    }, function() {
                        $state.go('messageFolder');
                    })
                }]
            })
            .state('messageFolder.edit', {
                parent: 'messageFolder',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/messageFolder/messageFolder-dialog.html',
                        controller: 'MessageFolderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MessageFolder', function(MessageFolder) {
                                return MessageFolder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('messageFolder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('messageFolder.delete', {
                parent: 'messageFolder',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/messageFolder/messageFolder-delete-dialog.html',
                        controller: 'MessageFolderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MessageFolder', function(MessageFolder) {
                                return MessageFolder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('messageFolder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
