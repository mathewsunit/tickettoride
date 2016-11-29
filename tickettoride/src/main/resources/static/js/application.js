var ticTacToe = angular.module('ticTacToe', ['ngRoute','gameModule']);




ticTacToe.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/about', {
            templateUrl: 'templates/about.html'
        }).
        when('/player/setup', {
            templateUrl: 'templates/player-panel2.html',
            controller: 'playerSetupController'
        }).
        when('/game/:id', {
            templateUrl: 'templates/game-board.html',
            controller: 'gameController'
        }).
        when('/player/panel', {
            templateUrl: 'templates/player-panel.html',
            controller: 'newGameController'
        }).
        otherwise({
            redirectTo: '/player/panel'
        });
}]);
