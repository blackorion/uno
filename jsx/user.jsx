export default class User {
	constructor(idx,board){
		this._id = idx;
		this._hand = [];
		this._board = board;
	}
	get id(){
		return this._id;
	}
	get hand(){
		return this._hand.map((cardFromHand)=>{
			let card = jQuery.extend(true, {}, cardFromHand);
			if(!this._board.isUsersTurn())
				card.playable = false;
			return card;
		});
	}
	set hand(newHand){
		this._hand = newHand;
	}
	get name(){
		return this._board.userById(this._id).name;
	}
	set name(newName){
		this._board.setUserName(newName);
		$(document).trigger('update:current_user');
	}
}
