import * as constants from './constants';

export default class Greeting extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:current_user',{},()=>{
			this.forceUpdate();
		});
		$(document).on('update:game_status',{},()=>{
			this.forceUpdate();
		});
	}
	_synthGreeting(){
		let user = this.props.board.currentUser;
		if(user) return <h3>Hello, {user.name}!</h3>;
		return <h3>Hello, 500 Server Error!</h3>;
	}
	render() {
		if(!this.props.board.isGameRunning()){
			return <div className='greeting'>
				{this._synthGreeting()}
				<p>Welcome to </p>
				<h1 className='glow'>The Super UNO VI: Hyper Arcade Edition II HD Remix!</h1>
			</div>
		}
		return false;
	}
}
