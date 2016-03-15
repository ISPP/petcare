'use strict';

describe('Controller Tests', function() {

    describe('Trip Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTrip, MockVehicle;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTrip = jasmine.createSpy('MockTrip');
            MockVehicle = jasmine.createSpy('MockVehicle');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Trip': MockTrip,
                'Vehicle': MockVehicle
            };
            createController = function() {
                $injector.get('$controller')("TripDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:tripUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
