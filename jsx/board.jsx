import User from './user';
import GameClient from './gameclient';
import BoardBase from './boardbase';

export default class Board extends BoardBase {
	initiateGame(){
		this._greetServer((data)=>{
			console.log('>>> CURRENT USER',data);
			this._currentUser = new User(data.id,this);
			this._users = [data];
			this._connect();
			$(document).trigger('update:current_user');
		});
		this._setupEvents();
	}
	_setStatus(newStatus){
		this._status = newStatus;
		$(document).trigger('update:game_status');
	}
	_setUsers(newUsers){
		this._users = newUsers;
		$(document).trigger('update:users');
	}
	_setUserHand(hand){
		this._currentUser.hand = hand;
		$(document).trigger('update:user_cards');
	}
	_setupEvents(){
		this._setUserUpdateCallback(this._setUsers.bind(this));
		this._setStatusUpdateCallback(this._setStatus.bind(this));
		this._setUserHandUpdateCallback(this._setUserHand.bind(this));
	}
}
