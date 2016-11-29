var gameModule = angular.module('gameModule', []);

gameModule.controller('newGameController', ['$rootScope', '$scope', '$http', '$location',

	function (rootScope, scope, http, location) {

	rootScope.gameId = null;
	scope.newGameData = null;

	scope.newGameOptions = {
			availableMapTypes: [
				{name: 'USA'},
				{name: 'CANADA'},
				{name: 'MEXICO'}
				],
				availableNumPlayers: [
					{name: '2'},
					{name: '4'},
					{name: '6'},
					{name: '8'}
					]
	};

	scope.createNewGame = function () {

		var data = scope.newGameData;
		var params = JSON.stringify(data);

		http.post("/game/create", params, {
			headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			}
		}).success(function (data, status, headers, config) {
			rootScope.gameId = data.id;
			location.path('/player/setup');
		}).error(function (data, status, headers, config) {
			location.path('/player/panel');
		});
	}
}
]);

gameModule.controller('playerSetupController', ['$rootScope', '$scope', '$http', '$location',

	function (rootScope, scope, http, location) {

	scope.playerGames = [];
	scope.playerColors = [];

	http.get('/game/players/list').success(function (data) {
		scope.playerGames = data;
	}).error(function (data, status, headers, config) {
		location.path('/player/panel');
	});

	http.get('/game/colors/list').success(function (data) {
		scope.playerColors = data;
	}).error(function (data, status, headers, config) {
		location.path('/player/panel');
	});

	scope.createPlayers = function () {

		var data = scope.playerGames;
		var params = JSON.stringify(data);

		http.post('/game/begin', params, {
			headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			}
		}).success(function (data) {
			location.path('/game/' + data.gameId);
		}).error(function (data, status, headers, config) {
			location.path('/player/setup');
		});
	}

	scope.update = function (item) {
		var index = scope.playerGames.playerColor.indexOf(item);
		scope.playerColors.splice(index, 1);     
	}
}
]);


gameModule.controller('gamesToJoinController', ['$scope', '$http', '$location',
	function (scope, http, location) {

	scope.gamesToJoin = [];

	http.get('/game/list').success(function (data) {
		scope.gamesToJoin = data;
	}).error(function (data, status, headers, config) {
		location.path('/player/panel');
	});


	scope.joinGame = function (id) {

		var params = {"id" : id}

		http.post('/game/join', params, {
			headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			}
		}).success(function (data) {
			location.path('/game/' + data.id);
		}).error(function (data, status, headers, config) {
			location.path('/player/panel');
		});
	}

}]);


gameModule.controller('playerGamesController', ['$scope', '$http', '$location', '$routeParams',
	function (scope, http, location, routeParams) {

	scope.playerGames = [];

	http.get('/game/player/list').success(function (data) {
		scope.playerGames = data;
	}).error(function (data, status, headers, config) {
		location.path('/player/panel');
	});

	scope.loadGame = function (id) {
		console.log(id);
		location.path('/game/' + id);
	}

}]);


gameModule.controller('gameController', ['$rootScope', '$routeParams', '$scope', '$http',
	function (rootScope, routeParams, scope, http) {
	var gameStatus;
	var i = 2;
	scope.show = false;
	
	getInitialData();
	
	scope.selectdestinationCard= function() {
		scope.show = !scope.show;
	}
	
	scope.pickTrainCard= function(data) {
		var params = JSON.stringify(data);
		
		if(i==1)
		{http.post("/game/picksecondtraincard", params,i , {
			headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			}
		}).success(function (data) {
			scope.faceUpCards = data;
			location.path('/game/' + data.gameId);
		}).error(function (data, status, headers, config) {
			location.path('/player/setup');
		});
		i = 2;
		}
		else if(i==2)
		{
			http.post("/game/pickfirsttraincard", params, {
				headers: {
					'Content-Type': 'application/json; charset=UTF-8'
				}
			}).success(function (data) {
				scope.faceUpCards = data;
				location.path('/game/' + data.gameId);
			}).error(function (data, status, headers, config) {
				location.path('/player/setup');
			});
			i--;
		}
		
	}

	function getInitialData	() {

		scope.faceUpCards = [];

		http.get('/game/' + routeParams.id).success(function (data) {
			scope.gameProperties = data;
			gameStatus = scope.gameProperties.gameStatus;
			getMoveHistory();
		}).error(function (data, status, headers, config) {
			scope.errorMessage = "Failed do load game properties";
		});

		http.get('/game/destCards/list').success(function (data) {
			scope.dCards = data;
		}).error(function (data, status, headers, config) {
			location.path('/player/panel');
		});


		http.get('/game/faceUp/list').success(function (data) {
			scope.faceUpCards = data;
		}).error(function (data, status, headers, config) {
			location.path('/player/panel');
		});

		http.get('/game/playerInfo').success(function (data) {
			scope.playerInfo = data;
		}).error(function (data, status, headers, config) {
			location.path('/player/panel');
		});
	}

	function getMoveHistory() {
		scope.movesInGame = [];

		return  http.get('/move/list').success(function (data) {
			scope.movesInGame = data;
			scope.playerMoves = [];

			//paint the board with positions from the retrieved moves
			angular.forEach(scope.movesInGame, function(move) {
				scope.rows[move.boardRow - 1][move.boardColumn - 1].letter = move.playerPieceCode;
			});
		}).error(function (data, status, headers, config) {
			scope.errorMessage = "Failed to load moves in game"
		});
	}

	function checkPlayerTurn() {
		return http.get('/move/turn').success(function (data) {
			scope.playerTurn = data;
		}).error(function (data, status, headers, config) {
			scope.errorMessage = "Failed to get the player turn"
		});
	}

	function getNextMove() {

		scope.nextMoveData = []

		// COMPUTER IS A SECOND PLAYER
		if(!scope.gameProperties.secondPlayer) {
			http.get("/move/autocreate").success(function (data, status, headers, config) {
				scope.nextMoveData = data;
				getMoveHistory().success(function () {
					var gameStatus = scope.movesInGame[scope.movesInGame.length - 1].gameStatus;
					if (gameStatus != 'IN_PROGRESS') {
						alert(gameStatus)
					}
				});
			}).error(function (data, status, headers, config) {
				scope.errorMessage = "Can't send the move"
			});

			// SECOND PLAYER IS A REAL USER
		} else {
			console.log(' another player move');
		}
	}

	function checkIfBoardCellAvailable(boardRow, boardColumn) {

		for (var i = 0; i < scope.movesInGame.length; i++) {
			var move = scope.movesInGame[i];
			if (move.boardColumn == boardColumn && move.boardRow == boardRow) {
				return false;
			}
		}
		return true;
	}

	scope.rows = [
		[
			{'id': '11', 'letter': '', 'class': 'box'},
			{'id': '12', 'letter': '', 'class': 'box'},
			{'id': '13', 'letter': '', 'class': 'box'}
			],
			[
				{'id': '21', 'letter': '', 'class': 'box'},
				{'id': '22', 'letter': '', 'class': 'box'},
				{'id': '23', 'letter': '', 'class': 'box'}
				],
				[
					{'id': '31', 'letter': '', 'class': 'box'},
					{'id': '32', 'letter': '', 'class': 'box'},
					{'id': '33', 'letter': '', 'class': 'box'}
					]
		];

	angular.forEach(scope.rows, function (row) {
		row[0].letter = row[1].letter = row[2].letter = '';
		row[0].class = row[1].class = row[2].class = 'box';
	});

	scope.markPlayerMove = function (column) {
		checkPlayerTurn().success(function () {

			var boardRow = parseInt(column.id.charAt(0));
			var boardColumn = parseInt(column.id.charAt(1));
			var params = {'boardRow': boardRow, 'boardColumn': boardColumn}

			if (checkIfBoardCellAvailable(boardRow, boardColumn) == true) {
				// if player has a turn
				if (scope.playerTurn == true) {

					http.post("/move/create", params, {
						headers: {
							'Content-Type': 'application/json; charset=UTF-8'
						}
					}).success(function () {
						getMoveHistory().success(function () {

							var gameStatus = scope.movesInGame[scope.movesInGame.length - 1].gameStatus;
							if (gameStatus == 'IN_PROGRESS') {
								getNextMove();
							} else {
								alert(gameStatus)
							}
						});

					}).error(function (data, status, headers, config) {
						scope.errorMessage = "Can't send the move"
					});

				}
			}
		});
	};
}
]);
