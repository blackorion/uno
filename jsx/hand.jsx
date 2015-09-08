import Card from './card';

export default class Hand extends React.Component{
	constructor(props) {
		super(props);
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
	}
	_synthHandNameClassName(){
		if(this.props.board.status && this.props.board.status.currentPlayerId == this.props.user.id)
			return 'hand-name hand-name-active';
		return 'hand-name hand-name-inactive';
	}
	render(){
		return <div>
			<div className={this._synthHandNameClassName()}>{this.props.user.name}</div>
		</div>;
	}
}

