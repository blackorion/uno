export default class UserHand extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:user_cards',{},()=>{
			this.forceUpdate();
		});
	}
	_synthCards(hand){
		return hand.map((card,i)=>{
			return <p key={i}>{card.value} {card.color}</p>
		});
	}
	render(){
		let user = this.props.board.currentUser;
		if (user && user.hand.length > 0){
			return <div id='user-hand' className='center-flex-row'>
				{this._synthCards(user.hand)}
			</div>;
		}
		return <p>You have no cards.</p>;
	}
}
