'use strict';

describe('Controller Tests', function() {

    describe('Supplier Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSupplier, MockReview, MockBooking, MockCustomer, MockPetShipper, MockPetSitter, MockCompany;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSupplier = jasmine.createSpy('MockSupplier');
            MockReview = jasmine.createSpy('MockReview');
            MockBooking = jasmine.createSpy('MockBooking');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockPetShipper = jasmine.createSpy('MockPetShipper');
            MockPetSitter = jasmine.createSpy('MockPetSitter');
            MockCompany = jasmine.createSpy('MockCompany');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Supplier': MockSupplier,
                'Review': MockReview,
                'Booking': MockBooking,
                'Customer': MockCustomer,
                'PetShipper': MockPetShipper,
                'PetSitter': MockPetSitter,
                'Company': MockCompany
            };
            createController = function() {
                $injector.get('$controller')("SupplierDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:supplierUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
