import SvgCard from './svgdeck';
import * as constants from './constants';

export class StaticCard extends React.Component {
	_synthClass() {
		if(this.props.data.playable == false)
			return 'card-dimmed';
		return 'card-static';
	}
	_synthUseSvg() {
		let svgCard = new SvgCard(this.props.data.value,this.props.data.color);
		return svgCard.synthUseSvg();
	}
	render() {
		return <svg className={this._synthClass()} width='85' height='128' viewBox='0 0 242 362' dangerouslySetInnerHTML={this._synthUseSvg()}>
		</svg>;
	}
}

export class StaticColorCard extends StaticCard {
	_synthColorClass(){
		let cls = 'card-col-off ';
		switch(this.props.data.color){
			case constants.COLOR_RED:
				return cls + 'c-red';
			case constants.COLOR_GREEN:
				return cls + 'c-green';
			case constants.COLOR_YELLOW:
				return cls + 'c-yellow';
			case constants.COLOR_BLUE:
				return cls + 'c-blue';
		}
	}
	render() {
		return <span className='pos-rel'>
			<svg className={this._synthClass()} width='85' height='128' viewBox='0 0 242 362' dangerouslySetInnerHTML={this._synthUseSvg()}>
			</svg>
			<div className={this._synthColorClass()}></div>
		</span>;
	}
}

export class ActionCard extends StaticCard {
	_synthClass(){
		if(this.props.data.playable == false)
			return 'card-dimmed';
		return 'card-action';
	}
	_callCallback(){
		this.props.action(this.props.data);
	}
	render() {
		return <svg onClick={this._callCallback.bind(this)} className={this._synthClass()} width='85' height='128' viewBox='0 0 242 362' dangerouslySetInnerHTML={this._synthUseSvg()}>
		</svg>;
	}
}

export class ColorSelectCard extends ActionCard {
	constructor(props){
		super(props);
		this.state = {
			isSelectingColor: false
		};
		$(document).on('card:triggered',()=>{
			this.setState({isSelectingColor:false});
		});
	}
	componentWillUnmount(){
		$(document).off('card:triggered');
	}
	_toggleColorSelector(){
		if(!this.state.isSelectingColor){
			$(document).trigger('card:triggered');
			this.setState({isSelectingColor:true});
		}
	}
	_playColor(color){
		this.props.data.color = color;
		this.setState({isSelectingColor:false});
		this._callCallback();
	}
	render() {
		if(!this.state.isSelectingColor){
			return <svg onClick={this._toggleColorSelector.bind(this)} className={this._synthClass()} width='85' height='128' viewBox='0 0 242 362' dangerouslySetInnerHTML={this._synthUseSvg()}>
			</svg>;
		} else {
			return <div className='color-select'>
				<div onClick={this._playColor.bind(this,constants.COLOR_RED)} className='red c-red'></div>
				<div onClick={this._playColor.bind(this,constants.COLOR_BLUE)} className='blue c-blue'></div>
				<div onClick={this._playColor.bind(this,constants.COLOR_YELLOW)} className='yellow c-yellow'></div>
				<div onClick={this._playColor.bind(this,constants.COLOR_GREEN)} className='green c-green'></div>
			</div>;
		}
	}
}

export class PlayableCard extends React.Component {
	constructor(props){
		super(props)
	}
	render(){
		if(this.props.card.playable){
			if(this.props.card.color == constants.COLOR_BLACK)
				return <ColorSelectCard action={this.props.board.playCard.bind(this.props.board)} data={this.props.card} />;
			else
				return <ActionCard action={this.props.board.playCard.bind(this.props.board)} data={this.props.card} />;
		}
		return <StaticCard data={this.props.card} />;
	}
}
