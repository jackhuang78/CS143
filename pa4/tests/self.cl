class Main {
	
	out:IO <- new IO;
	
	main():Int {{
		out.out_string(new Main.who());
		out.out_string(new A.who());
		out.out_string(new B.who());
				
		test();
		new A.test();
		new B.test();
		
		ss();
		new A.ss();
		new B.ss();
		
		letself();
		new A.letself();
		new B.letself();
		
		
		
		0;
	}};
	
	ss():Int {{
		out.out_string(self@Main.who());
		out.out_string(new SELF_TYPE@Main.who());		
		0;
	}};
	
	test():Int {{
		out.out_string(who());
		out.out_string(self.who());
		out.out_string(new SELF_TYPE.who());
		0;
	}};
	
	who():String {
		"Main\n"
	};
	
	letself():Int {{
		let s:SELF_TYPE <- self in {
			s.test();
			s.ss();

		};
		
		
		let s:Main <- self in {
			s.test();
			s.ss();
		};
	}};
};

class A inherits Main {



	who():String {
		"A\n"
	};
};

class B inherits A {

	ss():Int {{
		out.out_string(self@A.who());
		out.out_string(new SELF_TYPE@A.who());		
		0;
	}};
	
	who():String {
		"B\n"
	};
	
	letself():Int {{
		let s:SELF_TYPE <- self in {
			s.test();
			s.ss();
		};
		
		let s:A <- self in {
			s.test();
			s.ss();
		};
	}};
};
