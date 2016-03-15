'use strict';

describe('Controller Tests', function() {

    describe('Pet Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPet, MockPetOwner, MockPetSitter, MockPhoto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPet = jasmine.createSpy('MockPet');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            MockPetSitter = jasmine.createSpy('MockPetSitter');
            MockPhoto = jasmine.createSpy('MockPhoto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Pet': MockPet,
                'PetOwner': MockPetOwner,
                'PetSitter': MockPetSitter,
                'Photo': MockPhoto
            };
            createController = function() {
                $injector.get('$controller')("PetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:petUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
