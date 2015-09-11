import {ActionCard,StaticCard,ColorSelectCard} from './card';
import * as constants from './constants';

export default class UserHand extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:user_cards',{},()=>{
			this.forceUpdate();
		});
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
	}
	_synthCards(){
		let hand = this.props.board.currentUser.hand.reverse();
		if (hand.length > 0){
			return hand.map((card,i)=>{
				if(card.playable){
					if(card.color == constants.COLOR_BLACK)
						return <ColorSelectCard action={this.props.board.playCard.bind(this.props.board)} key={i} data={card} />;
					else
						return <ActionCard key={i} action={this.props.board.playCard.bind(this.props.board)} data={card} />;
				}
				return <StaticCard key={i} data={card} />;
			});
		}
		return <p>You don't have any cards.</p>;
	}
	render(){
		if(this.props.board.isGameRunning()){
			return <div id='user-hand' className='center-flex-row'>
				{this._synthCards()}
			</div>;
		}
		return false;
	}
}
