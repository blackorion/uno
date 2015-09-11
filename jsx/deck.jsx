import {StaticCard,StaticColorCard} from './card';
import * as constants from './constants';

export default class Deck extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
	}
	_synthDrawCard(){
		let user = this.props.board.currentUser;
		if(user.canPlay() && !user.canProceed())
			return <div onClick={this.props.board.drawCard.bind(this.props.board)} id='new-card'>
				Draw
			</div>;
	}
	_synthCard(card){
		if(card.value == constants.CARD_WILD || card.value == constants.CARD_WILD_DRAW_FOUR)
			return <StaticColorCard data={card} />;
		else return <StaticCard data={card} />;
	}
	render() {
		let status = this.props.board.status;
		if(this.props.board.isGameRunning()){
			return <div>
				{this._synthDrawCard()}
				{this._synthCard(status.topCard)}
				<span id='cards-left'>Cards left:<br/><strong>{status.cardsInBank}</strong></span>
			</div>;
		}
		return false;
	}
}
