export default class User {
	constructor(idx,board){
		this._id = idx;
		this._hand = [];
		this._board = board;
	}
	_user(){
		return this._board.userById(this._id);
	}
	get id(){
		return this._id;
	}
	get hand(){
		return this._hand.map((cardFromHand)=>{
			let card = jQuery.extend(true, {}, cardFromHand);
			if(!this.canPlay())
				card.playable = false;
			return card;
		});
	}
	set hand(newHand){
		this._hand = newHand;
	}
	get name(){
		return this._user().name;
	}
	set name(newName){
		this._board.setUserName(newName);
		$(document).trigger('update:current_user');
	}
	canProceed(){
		return !this._user().shouldPlay;
	}
	canPlay(){
		return this._id == this._board.status.currentPlayerId;
	}
}
