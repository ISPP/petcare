'use strict';

describe('Controller Tests', function() {

    describe('Customer Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCustomer, MockComplaint, MockPhoto, MockUser, MockSupplier, MockPetOwner;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockComplaint = jasmine.createSpy('MockComplaint');
            MockPhoto = jasmine.createSpy('MockPhoto');
            MockUser = jasmine.createSpy('MockUser');
            MockSupplier = jasmine.createSpy('MockSupplier');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Customer': MockCustomer,
                'Complaint': MockComplaint,
                'Photo': MockPhoto,
                'User': MockUser,
                'Supplier': MockSupplier,
                'PetOwner': MockPetOwner
            };
            createController = function() {
                $injector.get('$controller')("CustomerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:customerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
