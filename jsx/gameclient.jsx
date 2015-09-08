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
		});
	}
	_debugSubscribe(chan){
		this._client.subscribe(chan,(data)=>{
			console.log('<<< WS '+chan,data);
		});
	}
	subscribe(chan, f){
		$(document).on('ws:ready',{},()=>{
			this._client.subscribe(chan,(data)=>{
				f(JSON.parse(data.body));
			})
		});
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
	startGame(f){
		this._client.send('/app/game.control',{},JSON.stringify({action:'START'}));
	}
	currentUser(f){
		this._ajaxWithSession('/api/players/me','GET',f);
	}
	stopGame(f){
		this._client.send('/app/game.control',{},JSON.stringify({action:'STOP'}));
	}
}
