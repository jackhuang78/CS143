class A {
	x : Int;
	y : Int <- 10;
	
	zeroArg() : Bool { false };
	oneArg(a : Int) : String { "1" };
	twoArg(a : Int, b : Int) : Int { 2 };
};

Class BB inherits A {
};
