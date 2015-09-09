import Card from './card';
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
		let user = this.props.board.currentUser;
		if (user.hand && user.hand.length > 0){
			return user.hand.map((cardFromHand,i)=>{
				let card = jQuery.extend(true, {}, cardFromHand);
				if(!this.props.board.isUsersTurn())
					card.playable = false;
				if(card.playable)
					return <Card key={i} action={this.props.board.playCard.bind(this.props.board)} data={card} />;
				return <Card key={i} data={card} />;
			});
		}
		return <p>You don't have any cards.</p>;
	}
	render(){
		let status = this.props.board.status;
		if(status && status.state == constants.GAME_RUNNING){
			return <div id='user-hand' className='center-flex-row'>
				{this._synthCards()}
			</div>;
		}
		return false;
	}
}
