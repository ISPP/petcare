'use strict';

describe('Controller Tests', function() {

    describe('Comment Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockComment, MockUser, MockComplaint;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockComment = jasmine.createSpy('MockComment');
            MockUser = jasmine.createSpy('MockUser');
            MockComplaint = jasmine.createSpy('MockComplaint');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Comment': MockComment,
                'User': MockUser,
                'Complaint': MockComplaint
            };
            createController = function() {
                $injector.get('$controller')("CommentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:commentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
