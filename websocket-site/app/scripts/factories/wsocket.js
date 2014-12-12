'use strict';

angular.module('websocketApp')
  .factory('WSocketService', function($q) {

    var Service = {};

    function socket(url, open, callback) {
      var ws = new WebSocket(url);

      ws.onopen = open;

      ws.onmessage = callback;

      return {
        send: function(message) {
          ws.send(message);
        }
      };
    }

    Service.socket = socket;
    return Service;
  });