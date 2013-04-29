(* class test *)
class A {
};
class B inherits A {
};
class C {
	a : Int;
};
class D inherits C {
	a : Int;
};
	

class E {

	(* feature/formal test *)
	x : Int;
	y : Int <- 10;
	b1 : Bool;
	b2 : Bool <- true;
	
	zeroArg() : Int { 0 };
	oneArg(a : Int) : Int { 1 };
	twoArg(a : Int, b : Int) : Int { 2 };
	threeArg(a : Int, b : Int, c : Int) : Int { 3 };

	exprTest(a : Int, b : Int, c : Int) : Int {
		{
			(* integer expr *)
			x <- a;
			x <- a + b;
			x <- a - b;
			x <- a * b;
			x <- a / b;
			x <- ~a;
			
			(* precedence *)
			x <- a + b * c - b / a;
			x <- a * b + c / b - a;
			x <- (a + b) * (c - b) / a;
			
			(* boolean expr *)
			b1 <- true;
			b1 <- false;
			b1 <- not false;
			b1 <- not b1;
			b1 <- x < y;
			b1 <- x <= y;
			b1 <- x = y;
			
			
			
			
		}
	};



};

