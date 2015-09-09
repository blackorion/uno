import {User,CurrentUser} from './user';
import GameClient from './gameclient';
import BoardBase from './boardbase';

export default class Board extends BoardBase {
	constructor() {
		super();
		this._gameClient = new GameClient('http://10.160.70.117:8080');
	}
	initiateGame(){
		this._gameClient.currentUser((data)=>{
			console.log('>>> CURRENT USER',data);
			this._currentUserId = data.id;
			this._users.push(new CurrentUser(data.id,data.username));
			this._gameClient.connect();
			$(document).trigger('update:current_user');
		});
		this._setupEvents();
	}
	_setStatus(newStatus){
			this._status = newStatus;
			$(document).trigger('update:game_status');
	}
	_setUsers(newUsers){
		let currentUser = this.currentUser;
		this._users = [];
		for(let newUser of newUsers){
			if (newUser.id == this.currentUserId)
				this._users.push(currentUser);
			else this._users.push(new User(newUser.id,newUser.username));
		}
		$(document).trigger('update:users');
	}
	_setUserHand(hand){
		this.currentUser.hand = hand;
		$(document).trigger('update:user_cards');
	}
	_setupEvents(){
		this._gameClient.setUserUpdateCallback(this._setUsers.bind(this));
		this._gameClient.setStatusUpdateCallback(this._setStatus.bind(this));
		this._gameClient.setUserHandUpdateCallback(this._setUserHand.bind(this));
	}
	startGame(){
		console.log('>>> START GAME');
		this._gameClient.startGame();
	}
	stopGame(){
		console.log('>>> STOP GAME');
		this._gameClient.stopGame();
	}
	playCard(card){
		// delete card['playable'];
		this._gameClient.playCard(card);
	}
}
