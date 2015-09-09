import Greeting from './greeting';
import MenuBar from './menubar';
import Overview from './overview';
import UserHand from './userhand';
import Deck from './deck';

export default class BoardBase {
	constructor() {
		this._currentUserId = null;
		this._users = [];
		this._status = null;

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
		return this.userById(this._currentUserId);
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
		return this._status.currentPlayerId == this._currentUserId;
	}
}
