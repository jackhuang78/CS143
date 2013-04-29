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
			
			(* integer precedence *)
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
			
			
			(* if construct *)
			if true then 1 else 2 fi;
			if b1 then x <- 1 else x <-2 fi;
			if b1 = b2 then x <- 1 else x <- 2 fi;
			
			if if b2 then true else false fi 
			then if b1 then 3 else 4 fi
			else if b1 then 5 else 6 fi
			fi;
			
			(* while construct *)
			while b1 loop x <- x + 1 pool;
			while while b2 loop false pool 
			loop while x < 10 loop x <- x + 1 pool
			pool;
			
			(* case construct *)
			case new E of
				b1 : String => true;
			esac;
			
			case x of
				y : Object => 1;
				y : Int => 2;
			esac;
			

		}
	};



};

