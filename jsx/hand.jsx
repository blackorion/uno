import Card from './card';

export default class Hand extends React.Component{
	constructor(props) {
		super(props);
	}
	render(){
		return <div>
			<h4>{this.props.user.name}</h4>
		</div>;
	}
}

