'use strict';

describe('Controller Tests', function() {

    describe('Message Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMessage, MockUser, MockMessageFolder;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMessage = jasmine.createSpy('MockMessage');
            MockUser = jasmine.createSpy('MockUser');
            MockMessageFolder = jasmine.createSpy('MockMessageFolder');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Message': MockMessage,
                'User': MockUser,
                'MessageFolder': MockMessageFolder
            };
            createController = function() {
                $injector.get('$controller')("MessageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:messageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
