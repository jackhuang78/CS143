class Main inherits IO{

	x:Int;
	main():Int {{

		x <- 1;
		while true loop {
			let a1:A<-new A, a2:A<-new A in {
				a1.setA(a2);
				a2.setA(a1);
			};
			
			out_int(x);
			out_string("\n");
			x <- x + 1;
		} pool;
		0;
	}};

};

class A {
	o:A;
	setA(a:A):A {
		o <- a
	};
};
