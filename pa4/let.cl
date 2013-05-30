class Main {
	x:Int;
	y:Int;
	out:IO <- new IO;
	b:Bool;
	s:String;
	
	main():Int {
		{
			x <- 12;
			y <- 345;
			out.out_int(x);
			out.out_string("x0\n");
			out.out_int(y);
			out.out_string("y0\n");
			
			
			let x:Int<-678 in {
				out.out_int(x);
				out.out_string("x1\n");
				out.out_int(y);
				out.out_string("y1\n");
			};
			
			
			let x:Int in {
				out.out_int(x);
				out.out_string("x2\n");
				out.out_int(y);
				out.out_string("y2\n");
			};
			
			let x:Int<-789, y:Int<-x in {
				out.out_int(x);
				out.out_string("x3\n");
				out.out_int(y);
				out.out_string("y3\n");
			};
			
			let x:Int, y:Int<-x in {
				out.out_int(x);
				out.out_string("x4\n");
				out.out_int(y);
				out.out_string("y4\n");
			};		
			
			let x:Int<-789, y:Int<-x in {
				out.out_int(x);
				out.out_string("x5\n");
				out.out_int(y);
				out.out_string("y5\n");
			};	
			
			let x:Int<-234 in {
				out.out_int(x);
				out.out_string("x6\n");
				out.out_int(y);
				out.out_string("y6\n");
				let y:Int<-345 in {
					out.out_int(x);
					out.out_string("x7\n");
					out.out_int(y);
					out.out_string("y7\n");
				};
				out.out_int(x);
				out.out_string("x6\n");
				out.out_int(y);
				out.out_string("y6\n");
			};
			
			b<-true;
			s<-"str";
			let b:Bool, s:String in {
				if b then out.out_string("true1\n")
				else out.out_string("false1\n") fi;
				out.out_string(s.concat("1\n"));
				
				let b:Bool<-true, s:String<-"asdf" in {
					if b then out.out_string("true2\n")	
					else out.out_string("false2\n") fi;
					out.out_string(s.concat("2\n"));
					
					let b:Bool<-false in {
						if b then out.out_string("true3\n")	
						else out.out_string("false3\n") fi;
					};
					if b then out.out_string("true2\n")	
					else out.out_string("false2\n") fi;
				};
				out.out_string(s.concat("1\n"));
				
				if b then out.out_string("true1\n")
				else out.out_string("false1\n") fi;
			};
			
			0;
		}
	};
};
