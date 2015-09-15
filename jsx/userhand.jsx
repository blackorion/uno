import {PlayableCard} from './card';
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
				return <PlayableCard board={this.props.board} card={card} key={i} />;
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
