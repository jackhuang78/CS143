class A {
	x : Int;
	y : Int <- 10;
	
	zeroArg() : Int { 0 };
	oneArg(a : Int) : Int { 1 };
	twoArg(a : Int, b : Int) : Int { 2 };
	threeArg(a : Int, b : Int, c : Int) : Int { 3 };

};

Class BB inherits A {
};
