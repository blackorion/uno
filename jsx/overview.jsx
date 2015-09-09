import Hand from './hand';
import * as constants from './constants';

export default class Overview extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:users',{},()=>{
			this.forceUpdate();
		});
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
	}
	_synthHands(){
		let users = this.props.board.users;
		if (users.length > 0){
			return users.map((user)=>{
				return <Hand board={this.props.board} key={user.id} user={user} />;
			});
		}
		return <p>The room is empty.</p>;
	}
	_synthGameDirectionClass(){
		let status = this.props.board.status;
		if(status){
			if(status.gameDirection == constants.GAME_DIRECTION_RIGHT)
				return 'bg-right';
			return 'bg-left';
		}
		return '';
	}
	render(){
		return <div className={'center-flex-row '+this._synthGameDirectionClass()} id='hands'>
			{this._synthHands()}
		</div>;
	}
}
