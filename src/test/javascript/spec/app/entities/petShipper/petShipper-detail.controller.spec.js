'use strict';

describe('Controller Tests', function() {

    describe('PetShipper Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPetShipper, MockVehicle;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPetShipper = jasmine.createSpy('MockPetShipper');
            MockVehicle = jasmine.createSpy('MockVehicle');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PetShipper': MockPetShipper,
                'Vehicle': MockVehicle
            };
            createController = function() {
                $injector.get('$controller')("PetShipperDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:petShipperUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
