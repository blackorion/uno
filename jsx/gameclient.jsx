export default class GameClient {
	constructor(url){
		this._url = url;
	}
	_connect(){
		this._socket = new SockJS(this._url+'/ws');
		this._client = Stomp.over(this._socket);
		this._client.debug = null;
		this._client.connect({},(frame)=>{
			$(document).trigger('ws:ready');
			this._debugSubscribe('/topic/events');
			this._debugSubscribe('/queue/errors');
			this._debugSubscribe('/app/game.players');
			this._debugSubscribe('/app/game.info');
			this._debugSubscribe('/topic/game.info');
			this._debugSubscribe('/user/topic/game.cards');
			this._debugSubscribe('/topic/game.players');
			this._debugSubscribe('/app/game.cards');
			this._debugSubscribe('/user/queue/game.cards');
			// this._debugSubscribe('/app/game.playcard');
		});
	}
	_debugSubscribe(chan){
		this._client.subscribe(chan,(data)=>{
			console.log('<<< WS '+chan,data);
		});
	}
	_subscribeSingle(chan, f){
		$(document).on('ws:ready',{},()=>{
			this._client.subscribe(chan,(data)=>{
				f(JSON.parse(data.body));
			})
		});
	}
	_subscribe(chan, f){
		this._subscribeSingle('/app/'+chan,f);
		this._subscribeSingle('/topic/'+chan,f);
	}
	_ajaxWithSession(url,methodx,f){
		$.ajax({
			dataType: 'json',
			url: this._url+url,
			xhrFields:{
				withCredentials: true
			},
			method: methodx,
			success:f
		});
	}
	_sendAction(actionx,datax){
		this._client.send('/app/game.players.actions',{},JSON.stringify({action:actionx,data:datax}));
	}

	// Events
	_setStatusUpdateCallback(f){
		this._subscribe('game.info',f);
	}
	_setUserUpdateCallback(f){
		this._subscribe('game.players',f);
	}
	_setUserHandUpdateCallback(f){
		this._subscribeSingle('/app/game.cards',f);
		this._subscribeSingle('/user/topic/game.cards',f);
	}

	// Actions
	startGame(f){
		console.log('>>> WS START GAME');
		this._client.send('/app/game.control',{},JSON.stringify({action:'START'}));
	}
	_greetServer(f){
		this._ajaxWithSession('/api/players/me','GET',f);
	}
	stopGame(f){
		console.log('>>> WS STOP GAME');
		this._client.send('/app/game.control',{},JSON.stringify({action:'STOP'}));
	}
	playCard(card_){
		console.log('>>> WS SENDING',JSON.stringify(card_));
		this._sendAction('PLAY_CARD',{card:card_});
	}
	drawCard(){
		console.log('>>> WS DRAW CARD');
		this._sendAction('DRAW_CARD',{});
	}
	setUserName(newName){
		console.log('>>> WS NEW NAME [NOOP]',newName);
	}
	endTurn(){
		console.log('>>> WS END TURN');
		this._sendAction('END_TURN',{});
	}
}
