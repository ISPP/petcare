'use strict';

describe('Controller Tests', function() {

    describe('Complaint Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockComplaint, MockAdministrator, MockComment, MockCustomer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockComplaint = jasmine.createSpy('MockComplaint');
            MockAdministrator = jasmine.createSpy('MockAdministrator');
            MockComment = jasmine.createSpy('MockComment');
            MockCustomer = jasmine.createSpy('MockCustomer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Complaint': MockComplaint,
                'Administrator': MockAdministrator,
                'Comment': MockComment,
                'Customer': MockCustomer
            };
            createController = function() {
                $injector.get('$controller')("ComplaintDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'petcareApp:complaintUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
