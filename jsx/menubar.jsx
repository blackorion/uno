import * as constants from './constants';

export default class MenuBar extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
	}
	_synthEndTurnButton(){
		let user = this.props.board.currentUser;
		if(user.canPlay() && user.canProceed())
			return <button className='btn-good' onClick={this.props.board.endTurn.bind(this.props.board)}>End Turn</button>;
		else
			return <div className='btn-worst'>End Turn</div>;
	}
	_synthButtons(){
		let status = this.props.board.status;
		if(status){
			switch(status.state){
				case constants.GAME_NOT_RUNNING:
					return <button className='btn-good' onClick={this.props.board.startGame.bind(this.props.board)}>Start Game</button>;
				case constants.GAME_RUNNING:
					return this._synthEndTurnButton();
			}
		}
		return <p>Offline.</p>;
	}
	_synthDebugStopGame(){
		let status = this.props.board.status;
		if(status){
			switch(status.state){
				case constants.GAME_RUNNING:
					return <button className='btn-bad' onClick={this.props.board.stopGame.bind(this.props.board)}>Stop Game</button>;
			}
		}

	}
	_synthDebugResetGame(){
		let status = this.props.board.status;
		if(status){
			return <button className='btn-bad' onClick={this.props.board.resetGame.bind(this.props.board)}>Reset</button>;
		}
	}
	_synth
	render() {
		return <div className='main-menu'>
			{this._synthButtons()}
			{this._synthDebugStopGame()}
			{this._synthDebugResetGame()}
		</div>;
	}
}
