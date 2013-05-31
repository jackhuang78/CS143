class Main {
	out:IO <- new IO;
	a:A;
	ab:AB;
	ac:AC;
	acd:ACD;
	b:B;
	bc:BC;
	m:Main;
	s:String;
	x:Int;
	y:Int;
	o:Object;
	
	
	main():Int {
		{
			out.out_string("letcase.cl\n");

			
			
			
			o <- new A;
			s <- case o of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
			esac;
			out.out_string(s.concat("\n"));
			
			o <- new AB;
			s <- case o of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
			esac;
			out.out_string(s.concat("\n"));
					
			o <- new AC;
			s <- case o of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
			esac;
			out.out_string(s.concat("\n"));

			
			o <- new ACD;
			s <- case o of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
			esac;
			out.out_string(s.concat("\n"));
				
			o <- new BD;
			s <- case o of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
				o:Object => o.type_name();
			esac;
			out.out_string(s.concat("\n"));

			o <- new IO;
			s <- case o of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
				o:Object => o.type_name();
			esac;
			out.out_string(s.concat("\n"));
			
			x <- 10;
			y <- 100;
			out.out_int(x);
			out.out_string("\ncase0x\n");
			out.out_int(y);
			out.out_string("\ncase0y\n");
			case x of x:Int =>	{	
				x <- x + 1;
				y <- y + 1;
				out.out_int(x);
				out.out_string("\ncase1x\n");
				out.out_int(y);
				out.out_string("\ncase1y\n");
				
				case y of y:Int => {
					x <- x + 2;
					y <- y + 2;
					out.out_int(x);
					out.out_string("\ncase2x\n");
					out.out_int(y);
					out.out_string("\ncase2y\n");							
					
					case x of y:Int => 	{
						x <- x + 3;
						y <- y + 3;
						out.out_int(x);
						out.out_string("\ncase3x\n");
						out.out_int(y);
						out.out_string("\ncase3y\n");							
												
						case y of x:Int => 	{
							x <- x + 3;
							y <- y + 3;
							out.out_int(x);
							out.out_string("\ncase4x\n");
							out.out_int(y);
							out.out_string("\ncase4y\n");							
						};esac;
					
						out.out_int(x);
						out.out_string("\ncase3x\n");
						out.out_int(y);
						out.out_string("\ncase3y\n");
					};esac;	
					
					out.out_int(x);
					out.out_string("\ncase2x\n");
					out.out_int(y);
					out.out_string("\ncase2y\n");
				}; esac;
				
				out.out_int(x);
				out.out_string("\ncase1x\n");
				out.out_int(y);
				out.out_string("\ncase1y\n");				
				
			}; esac;
			out.out_int(x);
			out.out_string("\ncase0x\n");
			out.out_int(y);
			out.out_string("\ncase0y\n");

					
			0;
		}
	};
};

class A inherits Main {};
class AB inherits A {};
class AC inherits A {};
class ACD inherits AC{};
class B inherits Main {};
class BC inherits B {};
class BD inherits B {};

