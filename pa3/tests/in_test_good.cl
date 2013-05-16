class C inherits Main{
	
};

Class Main {
	a : Int;
	b : Bool;
	main():C {
		new C
	};
};

Class Def inherits Main{
	name : String;
};

Class D inherits Def{
	x : Int;
	func():Int {
		x <- x + x * x
	};

};

Class X inherits D{

};

Class S inherits Object{

};


