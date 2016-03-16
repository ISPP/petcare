'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('booking', {
                parent: 'entity',
                url: '/bookings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.booking.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/booking/bookings.html',
                        controller: 'BookingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('booking');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('booking.detail', {
                parent: 'entity',
                url: '/booking/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'petcareApp.booking.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/booking/booking-detail.html',
                        controller: 'BookingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('booking');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Booking', function($stateParams, Booking) {
                        return Booking.get({id : $stateParams.id});
                    }]
                }
            })
            .state('booking.new', {
                parent: 'booking',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/booking/booking-dialog.html',
                        controller: 'BookingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    startMoment: null,
                                    endMoment: null,
                                    status: null,
                                    price: null,
                                    night: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('booking', null, { reload: true });
                    }, function() {
                        $state.go('booking');
                    })
                }]
            })
            .state('booking.edit', {
                parent: 'booking',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/booking/booking-dialog.html',
                        controller: 'BookingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Booking', function(Booking) {
                                return Booking.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('booking', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('booking.delete', {
                parent: 'booking',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/booking/booking-delete-dialog.html',
                        controller: 'BookingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Booking', function(Booking) {
                                return Booking.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('booking', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
