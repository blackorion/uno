import * as constants from './constants';

export default class MenuBar extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:game_info',{},()=>{
			this.forceUpdate();
		});
	}
	_synthStartStop(){
		let info = this.props.board.info;
		if(info){
			switch(info.state){
				case constants.GAME_NOT_RUNNING:
					return <button className='btn' onClick={this.props.board.startGame.bind(this.props.board)}>Start Game</button>;
				case constants.GAME_RUNNING:
					return <button className='btn' onClick={this.props.board.stopGame.bind(this.props.board)}>Stop Game</button>;

			}
		}
	}
	render() {
		return <div id='main-menu'> {this._synthStartStop()} </div>;
	}
}
