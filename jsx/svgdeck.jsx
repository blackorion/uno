import * as c from './constants';

const svgGroupIds = [
	['g6694',     c.CARD_WILD_DRAW_FOUR,  c.COLOR_BLACK],
	['g9888',     c.CARD_WILD,            c.COLOR_BLACK],

	['g9459-4',   c.CARD_DRAW_TWO,        c.COLOR_BLUE],
	['g6643-5',   c.CARD_REVERSE,         c.COLOR_BLUE],
	['g6635-9',   c.CARD_SKIP,            c.COLOR_BLUE],
	['g6555-6',   c.CARD_ZERO,            c.COLOR_BLUE],
	['g6563-2',   c.CARD_ONE,             c.COLOR_BLUE],
	['g6571-4',   c.CARD_TWO,             c.COLOR_BLUE],
	['g6579-3',   c.CARD_THREE,           c.COLOR_BLUE],
	['g6587-5',   c.CARD_FOUR,            c.COLOR_BLUE],
	['g6595-4',   c.CARD_FIVE,            c.COLOR_BLUE],
	['g6603-2',   c.CARD_SIX,             c.COLOR_BLUE],
	['g6611-6',   c.CARD_SEVEN,           c.COLOR_BLUE],
	['g6619-3',   c.CARD_EIGHT,           c.COLOR_BLUE],
	['g6627-83',  c.CARD_NINE,            c.COLOR_BLUE],

	['g9433-0',   c.CARD_DRAW_TWO,        c.COLOR_YELLOW],
	['g6643-8',   c.CARD_REVERSE,         c.COLOR_YELLOW],
	['g6635-3',   c.CARD_SKIP,            c.COLOR_YELLOW],
	['g6555-0',   c.CARD_ZERO,            c.COLOR_YELLOW],
	['g6563-5',   c.CARD_ONE,             c.COLOR_YELLOW],
	['g6571-0',   c.CARD_TWO,             c.COLOR_YELLOW],
	['g6579-6',   c.CARD_THREE,           c.COLOR_YELLOW],
	['g6587-1',   c.CARD_FOUR,            c.COLOR_YELLOW],
	['g6595-7',   c.CARD_FIVE,            c.COLOR_YELLOW],
	['g6603-6',   c.CARD_SIX,             c.COLOR_YELLOW],
	['g6611-5',   c.CARD_SEVEN,           c.COLOR_YELLOW],
	['g6619-5',   c.CARD_EIGHT,           c.COLOR_YELLOW],
	['g6627-8',   c.CARD_NINE,            c.COLOR_YELLOW],

	['g9446-1',   c.CARD_DRAW_TWO,        c.COLOR_GREEN],
	['g6643-0',   c.CARD_REVERSE,         c.COLOR_GREEN],
	['g6635-7',   c.CARD_SKIP,            c.COLOR_GREEN],
	['g6555-7',   c.CARD_ZERO,            c.COLOR_GREEN],
	['g6563-3',   c.CARD_ONE,             c.COLOR_GREEN],
	['g6571-2',   c.CARD_TWO,             c.COLOR_GREEN],
	['g6579-7',   c.CARD_THREE,           c.COLOR_GREEN],
	['g6587-7',   c.CARD_FOUR,            c.COLOR_GREEN],
	['g6595-1',   c.CARD_FIVE,            c.COLOR_GREEN],
	['g6603-5',   c.CARD_SIX,             c.COLOR_GREEN],
	['g6611-7',   c.CARD_SEVEN,           c.COLOR_GREEN],
	['g6619-6',   c.CARD_EIGHT,           c.COLOR_GREEN],
	['g6627-0',   c.CARD_NINE,            c.COLOR_GREEN],

	['g9420-2',   c.CARD_DRAW_TWO,        c.COLOR_RED],
	['g6643',     c.CARD_REVERSE,         c.COLOR_RED],
	['g6635',     c.CARD_SKIP,            c.COLOR_RED],
	['g6555',     c.CARD_ZERO,            c.COLOR_RED],
	['g6563',     c.CARD_ONE,             c.COLOR_RED],
	['g6571',     c.CARD_TWO,             c.COLOR_RED],
	['g6579',     c.CARD_THREE,           c.COLOR_RED],
	['g6587',     c.CARD_FOUR,            c.COLOR_RED],
	['g6595',     c.CARD_FIVE,            c.COLOR_RED],
	['g6603',     c.CARD_SIX,             c.COLOR_RED],
	['g6611',     c.CARD_SEVEN,           c.COLOR_RED],
	['g6619',     c.CARD_EIGHT,           c.COLOR_RED],
	['g6627',     c.CARD_NINE,            c.COLOR_RED]
];

export default class SvgCard {
	constructor(action,color){
		this._action = action;
		this._color = color;
		this._id = this._findId();
	}
	_findId(){
		for(let record of svgGroupIds)
			if(record[1] == this._action && record[2] == this._color)
				return record[0];
	}
	synthUseSvg(){
		return {__html: '<use xlink:href="/img/UNO_cards_deck.svg#'+this._id+'"></use>'};
	}
}
