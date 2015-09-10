import Card from './card';
import * as constants from './constants';

export default class Deck extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
	}
	_synthDrawCard(){
		if(this.props.board.isUsersTurn())
			return <div onClick={this.props.board.drawCard.bind(this.props.board)} id="new-card">
				Draw
			</div>;
	}
	render() {
		let status = this.props.board.status;
		if(this.props.board.isGameRunning()){
			return <div>
				{this._synthDrawCard()}
				<Card data={status.topCard} />
				<span id='cards-left'>Cards left:<br/><strong>{status.cardsInBank}</strong></span>
			</div>;
		}
		return false;
	}
}
