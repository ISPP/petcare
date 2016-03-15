'use strict';

describe('Controller Tests', function() {

    describe('PetOwner Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPetOwner, MockPet, MockReview, MockBooking;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            MockPet = jasmine.createSpy('MockPet');
            MockReview = jasmine.createSpy('MockReview');
            MockBooking = jasmine.createSpy('MockBooking');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PetOwner': MockPetOwner,
                'Pet': MockPet,
                'Review': MockReview,
                'Booking': MockBooking
            };
            createController = function() {
                $injector.get('$controller')("PetOwnerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:petOwnerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
