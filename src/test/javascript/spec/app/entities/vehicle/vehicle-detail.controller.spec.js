'use strict';

describe('Controller Tests', function() {

    describe('Vehicle Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVehicle, MockPetShipper, MockTrip;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVehicle = jasmine.createSpy('MockVehicle');
            MockPetShipper = jasmine.createSpy('MockPetShipper');
            MockTrip = jasmine.createSpy('MockTrip');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Vehicle': MockVehicle,
                'PetShipper': MockPetShipper,
                'Trip': MockTrip
            };
            createController = function() {
                $injector.get('$controller')("VehicleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:vehicleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
