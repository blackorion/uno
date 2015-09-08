import Hand from './hand';

export default class Overview extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:users',{},()=>{
			this.forceUpdate();
		});
	}
	_synthHands(users){
		return Object.keys(users).map((user_id)=>{
			return <Hand board={this.props.board} key={user_id} user={users[user_id]} />;
		});
	}
	render(){
		let users = this.props.board.users;
		if (Object.keys(users).length > 0){
			return <div className='center-flex-row' id='hands'>
				{this._synthHands(users)}
			</div>;
		}
		return <p>The room is empty.</p>;
	}
}
