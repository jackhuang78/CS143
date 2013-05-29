class Main {
	out:IO <- new IO;
	x:Int;
	y:Int;
	z:Int;
	s:String;
	o:Object <- new Object;
	
	main():Int {
		{
			x <- 1;
			y <- 2;
			z <- y;
			out.out_string("x <- 1\n");
			out.out_string("x <- 2\n");
			out.out_string("x <- y\n");

			s <- if x < y then "x < y\n" else "x !< y\n" fi;
			out.out_string(s);
			s <- if x <= y then	"x <= y\n" else "x !<= y\n" fi;
			out.out_string(s);
			s <- if x = y then "x == y\n" else "x != y\n" fi;
			out.out_string(s);

			s <- if z < y then "z < y\n" else "z !< y\n" fi;
			out.out_string(s);			
			s <- if z <= y then "z <= y\n" else "z !<= y\n" fi;
			out.out_string(s);			
			s <- if z = y then "z == y\n" else "z != y\n" fi;
			out.out_string(s);
						
			s <- if not x < y then "x !< y\n" else "x < y\n" fi;
			out.out_string(s);
			s <- if not x <= y then	"x !<= y\n" else "x <= y\n" fi;
			out.out_string(s);
			s <- if not x = y then "x != y\n" else "x == y\n" fi;
			out.out_string(s);

			s <- if not z < y then "z !< y\n" else "z < y\n" fi;
			out.out_string(s);
			s <- if not z <= y then "z !<= y\n" else "z <= y\n" fi;
			out.out_string(s);
			s <- if not z = y then "z != y\n" else "z == y\n" fi;
			out.out_string(s);
						
			x <- 5;
			out.out_string("x <- 5\n");
			s <- if isvoid o then "o is void\n" else "o is not void\n" fi;
			out.out_string(s);
			
			o <- while 0 < x loop 
			{
				out.out_string("while x is ");
				out.out_int(x);
				out.out_string(" loop...\n");				
				x <- x - 1;
			} pool;
			
			out.out_string("now x is ");
			out.out_int(x);
			out.out_string("\n");				
			
			s <- if isvoid o then "o is void\n" else "o is not void\n" fi;
			out.out_string(s);


			if true then out.out_string("true\n") else out.out_string("false") fi;
			if false then out.out_string("true\n") else out.out_string("false") fi;
		
			0;
		}		
	};
};
