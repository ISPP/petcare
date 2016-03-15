'use strict';

describe('Controller Tests', function() {

    describe('Review Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockReview, MockPetOwner, MockSupplier, MockBooking;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockReview = jasmine.createSpy('MockReview');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            MockSupplier = jasmine.createSpy('MockSupplier');
            MockBooking = jasmine.createSpy('MockBooking');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Review': MockReview,
                'PetOwner': MockPetOwner,
                'Supplier': MockSupplier,
                'Booking': MockBooking
            };
            createController = function() {
                $injector.get('$controller')("ReviewDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:reviewUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
