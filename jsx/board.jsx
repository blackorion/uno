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
			this._users[data.id] = new CurrentUser(data.id,data.username);
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
		this._users = {};
		for(let newUser of newUsers){
			if (newUser.id == this.currentUserId)
				this._users[this.currentUserId] = currentUser;
			else this._users[newUser.id] = new User(newUser.id,newUser.username);
		}
		$(document).trigger('update:users');
	}
	_setUserHand(hand){
		for(let idx in hand)
			if(hand[idx].color == 'DARK') hand[idx].color = 'BLACK_PERSON';
		this.currentUser.hand = hand;
		$(document).trigger('update:user_cards');
	}
	_setupEvents(){
		this._gameClient.subscribe('/topic/game.info',this._setStatus.bind(this));
		this._gameClient.subscribe('/app/game.info',this._setStatus.bind(this));
		this._gameClient.subscribe('/app/game.players',this._setUsers.bind(this));
		this._gameClient.subscribe('/topic/game.players',this._setUsers.bind(this));
		this._gameClient.subscribe('/app/game.cards',this._setUserHand.bind(this));
		this._gameClient.subscribe('/user/topic/game.cards',this._setUserHand.bind(this));
	}
	startGame(){
		console.log('>>> START GAME');
		this._gameClient.startGame();
	}
	stopGame(){
		console.log('>>> STOP GAME');
		this._gameClient.stopGame();
	}
}
