class Main inherits IO{
	
	x:Int;
	a:A;

	main():Int {{
	
		x <- 1;
	
		while true loop {
			--a<-new A;
			--x <- x + 1;
			--out_int(x);
			--out_string("a\n");
			let a:A <- new A in 1;
			
		} pool;
		
		0;	
	}};
};

class A {
};
