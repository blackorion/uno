import Card from './card';

export default class Hand extends React.Component{
	constructor(props) {
		super(props);
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
		this.state = {
			editable:false
		};
	}
	_synthHandNameClassName(){
		let cls = 'hand-name';
		if(!this.state.editable){
			if(this.props.board.isGameRunning() && this.props.board.status.currentPlayerId == this.props.user.id)
				cls += ' hand-name-active';
			if(this.props.user.id == this.props.board.currentUser.id)
				cls += ' hand-name-self';
			else cls += ' hand-name-other';
		} else cls += ' hand-name-edit';
		return cls
	}
	_commitUserNameChange(event){
		let newName = event.target.innerText;
		if(newName.length > 0) this.props.board.currentUser.name = newName;
		this.setState({editable:false});
	}
	_setEditable(){
		if(this.props.user.id == this.props.board.currentUser.id)
			this.setState({editable:true});
	}
	_synthNamePlate(){
		if(this.state.editable){
			return <div contentEditable='true'
				className={this._synthHandNameClassName()}
				dangerouslySetInnerHTML={{__html: this.props.user.name}}
				onBlur={this._commitUserNameChange.bind(this)}></div>;
		} else{
			return <div onDoubleClick={this._setEditable.bind(this)}
				className={this._synthHandNameClassName()}>{this.props.user.name}</div>;
		}

	}
	_synthCardsOnHand(){
		if(this.props.board.isGameRunning() && this.props.user.cardsOnHand > 0)
			return <div className='hand-name-off bluez handhand center-flex-row'>
				{this.props.user.cardsOnHand}
			</div>;
	}
	_synthRoundScore(){
		if(!this.props.board.isGameRunning()){
			return <div className='score center-flex-row'>
				{this.props.user.gameScore}
			</div>;
		}
	}
	render(){
		return <div className='pos-rel'>
			{this._synthCardsOnHand()}
			{this._synthNamePlate()}
			{this._synthRoundScore()}
		</div>;
	}
}

