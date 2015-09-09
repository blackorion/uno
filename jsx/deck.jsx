import Card from './card';
import * as constants from './constants';

export default class Deck extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
	}
	render() {
		let status = this.props.board.status;
		if(status && status.state == constants.GAME_RUNNING)
			return <div>
				<Card data={status.topCard} />
				<span id='cards-left'>Cards left:<br/><strong>{status.cardsInBank}</strong></span>
			</div>;
		return false;
	}
}
