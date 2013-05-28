
(*  Example cool program testing as many aspects of the code generator
    as possible.
 *)

class Main {
	main():Int { 0 };
};

class A {
	a1:A <- new A;
	x:Int <- 99 + 1;
	s:String <- "abc";
	
	func(a2:A):A {
		{
			new B;
			a1 <- new B;
			a2 <- new B;
			

			a1 <- func(a2);
			a1 <- a2.func(a2);
			
			a1 <- copy();
			a1 <- a2.copy();
			
			
			
			
		}
	};	
};

class B inherits A {
	
};

class C {
	a:Int;
	b:Int <- 1;
	c:Int <- 1 + 2;
};
