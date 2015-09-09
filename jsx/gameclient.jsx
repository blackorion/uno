export default class GameClient {
	constructor(url){
		this._url = url;
	}
	connect(){
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
	subscribeSingle(chan, f){
		$(document).on('ws:ready',{},()=>{
			this._client.subscribe(chan,(data)=>{
				f(JSON.parse(data.body));
			})
		});
	}
	subscribe(chan, f){
		this.subscribeSingle('/app/'+chan,f);
		this.subscribeSingle('/topic/'+chan,f);
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

	// Events
	setStatusUpdateCallback(f){
		this.subscribe('game.info',f);
	}
	setUserUpdateCallback(f){
		this.subscribe('game.players',f);
	}
	setUserHandUpdateCallback(f){
		this.subscribeSingle('/app/game.cards',f);
		this.subscribeSingle('/user/topic/game.cards',f);
	}

	// Actions
	startGame(f){
		this._client.send('/app/game.control',{},JSON.stringify({action:'START'}));
	}
	currentUser(f){
		this._ajaxWithSession('/api/players/me','GET',f);
	}
	stopGame(f){
		this._client.send('/app/game.control',{},JSON.stringify({action:'STOP'}));
	}
	playCard(card_){
		console.log('>>> WS SENDING',JSON.stringify(card_));
		this._client.send('/app/game.playcard',{},JSON.stringify({'card':card_}));
	}
}
