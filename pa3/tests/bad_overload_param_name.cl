class Main {
	main():Int{0};
};

class A {
	x:Int;
	x:Int;
	x:Bool;
	
	funcX():Int {
		x
	};
	
	funcA(a0:Int, a1:Int, a0:Int, a1:Int):Int {
		a0
	};
};

class B inherits A {
	x:Int;
	
	funcX(x:Int):Int {
		x
	};
	
	funcB(b0:Int, b1:Int, b0:Bool, b1:String):Int {
		b0
	};
};

class C inherits B {
	x:Bool;
	
	funcX():Bool {
		x
	};
	
	funcC(c0:Int, c1:Int, c0:Bool):Bool {
		c0
	};
};
