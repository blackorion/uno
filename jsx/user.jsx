export default class User {
	constructor(idx,_name){
		this.id_ = idx;
		this.name_ = _name;
	}
	get id(){
		return this.id_;
	}
	get name(){
		return this.name_;
	}
}
