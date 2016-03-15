'use strict';

describe('Controller Tests', function() {

    describe('Administrator Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAdministrator, MockComplaint;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAdministrator = jasmine.createSpy('MockAdministrator');
            MockComplaint = jasmine.createSpy('MockComplaint');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Administrator': MockAdministrator,
                'Complaint': MockComplaint
            };
            createController = function() {
                $injector.get('$controller')("AdministratorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:administratorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
