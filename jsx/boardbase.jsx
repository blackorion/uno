import Greeting from './greeting';
import MenuBar from './menubar';
import Overview from './overview';
import UserHand from './userhand';
import Deck from './deck';
import GameClient from './gameclient';
import * as constants from './constants';

export default class BoardBase extends GameClient {
	constructor(url) {
		super(url);
		this._users = [];
		this._status = null;
		this._currentUser = null;

		this._greeter = React.render(
			<Greeting board={this}/>,
				document.getElementById('greeter')
		);
		this._menubar = React.render(
			<MenuBar board={this}/>,
				document.getElementById('menubar')
		);
		this._overview = React.render(
			<Overview board={this}/>,
				document.getElementById('hand-others')
		);
		this._userhand = React.render(
			<UserHand board={this}/>,
				document.getElementById('own-hand')
		);
		this._deck = React.render(
			<Deck board={this}/>,
				document.getElementById('deck')
		);
	}
	get currentUser(){
		return this._currentUser;
	}
	userById(user_id){
		for(let user of this._users) if(user.id == user_id) return user;
	}
	get users(){
		return this._users;
	}
	get status(){
		return this._status;
	}
	isUsersTurn(){
		return this._status && this._status.currentPlayerId == this._currentUser.id;
	}
	isGameRunning(){
		return this._status && this._status.state == constants.GAME_RUNNING;
	}
}
