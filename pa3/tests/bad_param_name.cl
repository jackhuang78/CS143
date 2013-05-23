class Main {
	main():Int{0};
};

class A {
	(* same parameter name *)
	funcA(a0:Int, a0_:Int, a0:Int, a0_:Int):Int{0};
	
	(* same name with different types *)
	funcB(a0:Int, a0_:Int, a0:Bool, a0_:Int):Int{0};
	funcC(a0:Int, a0_:Int, a0:Int, a0_:Bool):Int{0};
};

class B inherits A {
	(* type mismatch before duplicate parameter names *)
	funcA(a0:Bool, a0_:Int, a0:Int, a0_:Int):Int{0};
	
	(* type mismatch after duplicate parameter names *)
	funcB(a0:Int, a0_:Int, a0:Int, a0_:Bool):Int{0};
	
};

