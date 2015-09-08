import Greeting from './greeting';
import MenuBar from './menubar';
import Overview from './overview';
import UserHand from './userhand';

export default class BoardBase {
	constructor() {
		this._currentUserId = null;
		this._users = {};
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
	}
	get currentUser(){
		return this._users[this._currentUserId];
	}
	get users(){
		return this._users;
	}
	get status(){
		return this._status;
	}
}
