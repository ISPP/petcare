'use strict';

describe('Controller Tests', function() {

    describe('Booking Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBooking, MockPetOwner, MockSupplier, MockReview;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBooking = jasmine.createSpy('MockBooking');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            MockSupplier = jasmine.createSpy('MockSupplier');
            MockReview = jasmine.createSpy('MockReview');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Booking': MockBooking,
                'PetOwner': MockPetOwner,
                'Supplier': MockSupplier,
                'Review': MockReview
            };
            createController = function() {
                $injector.get('$controller')("BookingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:bookingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
