'use strict';

describe('Controller Tests', function() {

    describe('MessageFolder Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMessageFolder, MockMessage, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMessageFolder = jasmine.createSpy('MockMessageFolder');
            MockMessage = jasmine.createSpy('MockMessage');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'MessageFolder': MockMessageFolder,
                'Message': MockMessage,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("MessageFolderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:messageFolderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
