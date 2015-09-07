export default class Card extends React.Component {
	constructor(props) {
		super(props);
	}
	_synthUseMainSVG(){
		return {__html: '<use xlink:href="/img/UNO_cards_deck.svg#g6587-7"></use>'};
	}
	render() {
		return <a className='card' style={this.props.position} href='#'>
			<svg width='85' height='128' viewBox='0 0 242 362' dangerouslySetInnerHTML={this._synthUseMainSVG()}>
			</svg>
		</a>;
	}
}
