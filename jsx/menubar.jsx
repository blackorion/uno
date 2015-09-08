import * as constants from './constants';

export default class MenuBar extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
	}
	_synthStartStop(){
		let status = this.props.board.status;
		if(status){
			switch(status.state){
				case constants.GAME_NOT_RUNNING:
					return <button className='btn good' onClick={this.props.board.startGame.bind(this.props.board)}>Start Game</button>;
				case constants.GAME_RUNNING:
					return <button className='btn bad' onClick={this.props.board.stopGame.bind(this.props.board)}>Stop Game</button>;
			}
		}
		return <p>Offline.</p>;
	}
	render() {
		return <div id='main-menu'>{this._synthStartStop()}</div>;
	}
}
