import SvgCard from './svgdeck';

export default class Card extends React.Component {
	constructor(props) {
		super(props);
	}
	_synthClass(){
		if(this.props.data.playable == false)
			return 'card-dimmed';
		if(this._hasCallback())
			return 'card-action';
		return 'card-static';
	}
	_hasCallback(){
		return typeof this.props.action === 'function';
	}
	_synthUseSvg(){
		let svgCard = new SvgCard(this.props.data.value,this.props.data.color);
		return svgCard.synthUseSvg();
	}
	_callCallback(){
		if(this._hasCallback()) this.props.action(this.props.data);
	}
	render() {
		return <svg onClick={this._callCallback.bind(this)} className={this._synthClass()} width='85' height='128' viewBox='0 0 242 362' dangerouslySetInnerHTML={this._synthUseSvg()}>
			</svg>;
	}
}
