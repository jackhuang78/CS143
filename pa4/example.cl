
(*  Example cool program testing as many aspects of the code generator
    as possible.
 *)

class Main {
	x:Int <- 11;	
	y:Int;
	w:String <- "abc";
	z:String;
	main():Int { x <- 22 };
};

class A inherits Main {
	aa:Bool;
	
	foo():Bool {
		aa <- foo()
	};
};

