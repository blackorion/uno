export default class Greeting extends React.Component {
	constructor(props) {
		super(props);
		$(document).on('update:current_user',{},()=>{
			this.forceUpdate();
		});
	}
	render() {
		let user = this.props.board.currentUser;
		if(user)
			return <h3>Hello, {user.name}!</h3>;
		return <h3>Hello, 500 Server Error!</h3>;
	}
}
