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
			this._client.subscribe('/topic/events',(data)=>{
				console.log('<<< WS INFO',data);
			});
			this._client.subscribe('/queue/error',(data)=>{
				console.log('<<< WS ERROR',data);
			});
		});
	}
	subscribe(chan, f){
		$(document).on('ws:ready',{},()=>{
			this._client.subscribe(chan,(data)=>{
				f(JSON.parse(data.body));
			});
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
		// .error((error)=>{
		// 	console.log('!!!!! ERROR !!!!!',error);
		// 	if(typeof errorCallback === 'function') errorCallback();
		// });
	}
	createUser(f){
		this._ajaxWithSession('/api/players/create','POST',f);
	}
	startGame(f){
		this._ajaxWithSession('/api/game/start','GET',f);
	}
	currentUser(f){
		this._ajaxWithSession('/api/players/me','GET',f);
	}
	stopGame(f){
		this._ajaxWithSession('/api/game/stop','GET',f);
	}
	info(f){
		this._ajaxWithSession('/api/game/info','GET',f);
	}
}
