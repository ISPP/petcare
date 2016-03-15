'use strict';

describe('Controller Tests', function() {

    describe('Actor Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockActor, MockMessageFolder, MockPhoto, MockComment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockActor = jasmine.createSpy('MockActor');
            MockMessageFolder = jasmine.createSpy('MockMessageFolder');
            MockPhoto = jasmine.createSpy('MockPhoto');
            MockComment = jasmine.createSpy('MockComment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Actor': MockActor,
                'MessageFolder': MockMessageFolder,
                'Photo': MockPhoto,
                'Comment': MockComment
            };
            createController = function() {
                $injector.get('$controller')("ActorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:actorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
