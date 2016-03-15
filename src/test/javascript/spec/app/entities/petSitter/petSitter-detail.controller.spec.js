'use strict';

describe('Controller Tests', function() {

    describe('PetSitter Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPetSitter, MockPet, MockPlace;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPetSitter = jasmine.createSpy('MockPetSitter');
            MockPet = jasmine.createSpy('MockPet');
            MockPlace = jasmine.createSpy('MockPlace');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PetSitter': MockPetSitter,
                'Pet': MockPet,
                'Place': MockPlace
            };
            createController = function() {
                $injector.get('$controller')("PetSitterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:petSitterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
