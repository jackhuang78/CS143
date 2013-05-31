class Main {
	x:Int;
	y:Int <- 55;
	s:String;
	t:String <- "abc";
	m:Bool;
	n:Bool <- false;
	
	inn:IO;
	out:IO <- new IO;
	
	main():Int{
		{
			new IO.out_string("var.cl\n");
			
			new IO.out_int(x);			new IO.out_string("\n");
			new IO.out_int(y);			new IO.out_string("\n");
			new IO.out_string(s);		new IO.out_string("\n");
			new IO.out_string(t);		new IO.out_string("\n");
			
			if m then	new IO.out_string("m is true\n") 
			else 		new IO.out_string("m is false\n") fi;
			
			if n then	new IO.out_string("n is true\n") 
			else 		new IO.out_string("n is false\n") fi;
						
			x <- 33;
			s <- "def";
			m <- true;
			new IO.out_int(x);			new IO.out_string("\n");
			new IO.out_string(s);		new IO.out_string("\n");
			if m then	new IO.out_string("m is true\n") 
			else 		new IO.out_string("m is false\n") fi;
			
			out.out_string("out IO\n");
			if isvoid inn then	out.out_string("in is void\n")
			else				out.out_string("in is not void\n") fi;
			
			0;
		}
	};	
};
