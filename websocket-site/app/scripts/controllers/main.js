'use strict';

/**
 * @ngdoc function
 * @name websocketApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the websocketApp
 */
angular.module('websocketApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
