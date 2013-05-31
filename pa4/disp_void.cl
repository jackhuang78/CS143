class Main {
	out:IO <- new IO;
	
	main():Int {{
		
		let a:A <- new A in {
			out.out_string(a.func());
		};
		

		
		let s:String in {
			out.out_string(s.type_name());
		};
		
		let a:A in {
			out.out_string(a.type_name());
			out.out_string(a.func());
		};
		
		0;
	}};
};

class A {
	func():String {
		"A.func()\n"
	};
};
