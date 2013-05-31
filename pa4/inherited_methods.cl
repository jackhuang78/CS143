class Main {
	out:IO <- new IO;
	
	a:A <- new A;
	b:B <- new B;
	c:C <- new C;
	
	main():Int{
		{
			(*out.out_string(new Object.type_name());
			out.out_string(type_name());
			out.out_string(new A.type_name());
			out.out_string(new B.type_name());
			out.out_string(new C.type_name());		
			out.out_string("\n");*)
			
			
			out.out_string(a.who());
			out.out_string(b.who());
			out.out_string(c.who());

			a<-a;
			out.out_string(a.who());
			a<-b;
			out.out_string(a.who());
			a<-c;
			out.out_string(a.who());			

			
			
			(*out.out_string(new Main.who());
			out.out_string(new A.who());
			out.out_string(new B.who());*)
			
			
			0;
		}
	};
	
	who():String {
		"Main\n"
	};
};

class A  {
	who():String {
		"A\n"
	};
};
class B inherits A{
	who():String {
		"B\n"
	};
};

class C inherits B{
	who():String {
		"C\n"
	};
};
