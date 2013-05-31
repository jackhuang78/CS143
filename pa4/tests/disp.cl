class Main {
	out:IO<-new IO;

	a:Int;
	b:Bool;
	c:String;

	main():Int {{
		out.out_int(func1(1, true, "test1"));
		out.out_int(func2(2, false, "test2"));
		
		out.out_int(new A.func1(3, false, "test3"));
		
		out.out_string("\n");
		~1;
	}};
	
	func1(a:Int, b:Bool, c:String):Int {{
		out.out_string("\nMain.func1: ");
		out.out_int(a);
		if b then out.out_string(" true ")
		else out.out_string(" false ") fi;
		out.out_string(c.concat("\n"));		
		1;
	}};
	
	func2(b:Int, c:Bool, a:String):Int {{
		out.out_string("\nMain.func2: ");
		func1(b, c, a);
		2;
	}};
};


class A inherits Main {

	func1(d:Int, e:Bool, f:String):Int {{
		out.out_string("\nA.func1: ");
		new A@Main.func1(d, e, f);
		3;
	}};
};
