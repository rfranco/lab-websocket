'use strict';

/**
 * @ngdoc function
 * @name websocketApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the websocketApp
 */
angular.module('websocketApp')
  .controller('MainCtrl', function ($scope, WSocketService) {

    $scope.time = '';
    $scope.sentMessages = [];

    var ws = WSocketService.socket('ws://192.168.1.12:9000/websocket',
    function() {
      console.log("Socket has been opened!");
    },
    function(response) {
      $scope.sentMessages.push(response.data);
      $scope.$apply();
    });

    $scope.sendMessage = function(message) {
      ws.send(message);
    };

  });
