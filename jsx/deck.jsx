import {StaticCard,StaticColorCard,PlayableCard} from './card';
import * as constants from './constants';

export default class Deck extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
		$(document).on('update:user_cards',{},()=>{
			this.forceUpdate();
		});
	}
	_synthDrawCard(){
		let user = this.props.board.currentUser;
		if(user.canPlay()){
			if(user.drawnCard){
				return <PlayableCard board={this.props.board} card={user.drawnCard} />;
			} else{
				return <div onClick={this.props.board.drawCard.bind(this.props.board)} className='placeholder new-card'>
					Draw
				</div>;
			}
		}
	}
	_synthCard(card){
		if(card){
		if(card.value == constants.CARD_WILD || card.value == constants.CARD_WILD_DRAW_FOUR)
			return <StaticColorCard data={card} />;
		else return <StaticCard data={card} />;
		}else{
			return <div className='placeholder'>Empty</div>;
		}
	}
	render() {
		let status = this.props.board.status;
		if(this.props.board.isGameRunning()){
			return <div>
				<span className='shift-left'>
					{this._synthDrawCard()}
				</span>
				{this._synthCard(status.topCard)}
				<span className='cards-left'>Cards left:<br/><strong>{status.cardsInBank}</strong></span>
			</div>;
		}
		return false;
	}
}
