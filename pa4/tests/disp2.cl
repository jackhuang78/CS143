class Main {

	a:A;
	d:D;
	
	main():Int {{

		a <- new D;
		a.who();
		a.who2();
		a <- new C;
		a.who();
		a.who2();
		a <- new B;
		a.who();
		a.who2();	
		a <- new A;
		a.who();
		a.who2();
		
		d <- new D;
		d.who();
		d.who2();
		d@A.who();
		d@A.who2();	
		d@B.who();
		d@B.who2();
		d@C.who();
		d@C.who2();	
		
		0;
	}};
	

	

	
};

class A {
	out:IO <- new IO;
	who():IO {{
		out.out_string("A\n");
	}};
	
	who2():IO {{
		out.out_string("A2\n");
	}};

};

class B inherits A {
	who():IO {{
		out.out_string("B\n");
	}};

};

class C inherits B {
	who():IO {{
		out.out_string("C\n");
	}};
};

class D inherits C {
	who2():IO {{
		out.out_string("D2\n");
	}};
};
