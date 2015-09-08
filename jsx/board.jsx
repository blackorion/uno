import User from './user';
import Card from './card';
import Hand from './hand';
import GameClient from './gameclient';
import Greeting from './greeting';
import MenuBar from './menubar';

export default class Board {
	constructor() {
		this._currentUser = null;
		this._gameClient = new GameClient('http://10.160.70.117:8080');

		this._greeter = React.render(
			<Greeting board={this}/>,
				document.getElementById('greeter')
		);
		this._menubar = React.render(
			<MenuBar board={this}/>,
				document.getElementById('menubar')
		);
		this._info = null;
	}
	get currentUser(){
		return this._currentUser;
	}
	initiateGame(){
		this._gameClient.currentUser((data)=>{
			console.log('>>> CURRENT USER',data);
			this._currentUser =  new User(0,data.username);
			this._gameClient.connect();
			$(document).trigger('update:current_user');
		});
		this._updateInfo();
		this._setupEvents();
	}
	_setupEvents(){
		this._gameClient.subscribe('/topic/events',(data)=>{
			this._info = data;
			$(document).trigger('update:game_info');
		});
	}
	_updateInfo(){
		this._gameClient.info((data)=>{
			console.log('>>> INFO',data);
			this._info = data;
			$(document).trigger('update:game_info');
		});
	}
	get info(){
		return this._info;
	}
	createNewUser(f){
		this._gameClient.createUser((data)=>{
			console.log('>>> CREATE USER',data);
			this._currentUser =  new User(0,data.username);
			$(document).trigger('update:current_user');
			if(typeof f === 'function') f();
		});
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
