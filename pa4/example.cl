
(*  Example cool program testing as many aspects of the code generator
    as possible.
 *)

class Main {
	main():Int { 0 };
};

class A {
	a1:A;
	
	func(a2:A):A {
		{
			new A;
			a1 <- new A;
			a2 <- new A;
			

			a1 <- func(a2);
			a1 <- a2.func(a2);
			
			a1 <- copy();
			a1 <- a2.copy();
			
			
			
			
		}
	};	
};

