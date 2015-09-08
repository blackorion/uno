export class User {
	constructor(idx,name_){
		this._id = idx;
		this._name = name_;
	}
	get id(){
		return this._id;
	}
	get name(){
		return this._name;
	}
}

export class CurrentUser extends User {
	constructor(idx,name_){
		super(idx,name_);
		this._hand = [];
	}
	get hand(){
		return this._hand;
	}
	set hand(newHand){
		this._hand = newHand;
	}
}
