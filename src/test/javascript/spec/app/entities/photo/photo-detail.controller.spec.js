'use strict';

describe('Controller Tests', function() {

    describe('Photo Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPhoto, MockCustomer, MockPet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPhoto = jasmine.createSpy('MockPhoto');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockPet = jasmine.createSpy('MockPet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Photo': MockPhoto,
                'Customer': MockCustomer,
                'Pet': MockPet
            };
            createController = function() {
                $injector.get('$controller')("PhotoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:photoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
