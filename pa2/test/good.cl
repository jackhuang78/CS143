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
			
			(* precedence 
			x <- a + b * c - b / a;
			x <- a * b + c / b - a;
			x <- (a + b) * (c - b) / a;*)
			
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

class F {
	init0() : Int { 
		{
			(new F).init0();
			init0();
		}
	};
	init(obj:F) : Int { 
		{
			(new F).init(new F);
			init(new F);
			init(new F);
			init(new F);
		}
	};
	init2(obj:F, b: Int) : Int { 
		(new F).init2(new F,2)
	};
	init4(obj:F, b: Int, d: Boolean) : Int { 
		(new F) @E .init4(new F,2,false)
	};

};

class G {
	myfunc() : Int{
		let a: Int , b : Int <- 2, z: Int <- 999, c: String <- "abc" in
		{
			a <- a + b;
		}

	};
};

