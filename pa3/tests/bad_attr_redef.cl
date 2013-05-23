class Main {
	main():SELF_TYPE{self};
};

class A {
	x:Int;
	xx:Bool;
	x:Int;	(* redefine with same type *)
	x:Bool; (* redefine with different type *)
	
	func(y:Int, z:Int):Int{x};
	func(y:Int, z:Int):Bool{xx};		(* redefine with different return type *)
	func():Int{x};					(* redefine with different number of param *)
	func(x:Int, z:Int):Int{x};		(* redefine with different name of param *)
	func(x:Bool, z:Bool):Int{0};	(* redefine with different type of param *)
	
	
};

class B inherits A {
	x:Int;		(* redefine parent with same type *)
	x:Bool;		(* redefine paretn with different type *)
	
	func(y:Int, z:Int):Int{x};
	func(y:Int, z:Int):Bool{xx};		(* redefine with different return type *)
	func():Int{x};					(* redefine with different number of param *)
	func(x:Int, z:Int):Int{x};		(* redefine with different name of param *)
	func(x:Bool, z:Bool):Int{0};	(* redefine with different type of param *)

};

class C inherits A {
	func(y:Int, z:Int):Bool{xx};		(* redefine with different return type *)
	func():Int{x};					(* redefine with different number of param *)
	func(x:Int, z:Int):Int{x};		(* redefine with different name of param *)
	func(x:Bool, z:Bool):Int{0};	(* redefine with different type of param *)

};

class D inherits A {
	func(x:Bool, z:Bool):Int{0};	(* redefine with different type of param *)
};

class E inherits A {
	func(x:Bool):Bool{x};	(* multiple errors *)
};

class F inherits A {
	func(x:Bool):Int{0};	(* multiple errors *)
};

class G inherits A {
	func(x:Bool, z:Int):Int{0};	(* multiple errors *)
};

class AA {
	func(x:Bool, z:Int):Int{0};
};

class BB inherits AA {
	func(x:Int, z:String):Int{0};
};


