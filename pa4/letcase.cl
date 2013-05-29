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
	
	main():Int {
		{
			a <- new A;
			s <- case a of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
			esac;
			out.out_string(s.concat("\n"));
			
			a <- new AB;
			s <- case a of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
			esac;
			out.out_string(s.concat("\n"));
					
			a <- new AC;
			s <- case a of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
			esac;
			out.out_string(s.concat("\n"));

			
			a <- new ACD;
			s <- case a of
				o:A		=>	o.type_name();
				o:AB	=>	o.type_name();
				o:AC	=>	o.type_name();
				o:B		=>	o.type_name();
				o:BC	=>	o.type_name();
			esac;
			out.out_string(s.concat("\n"));
				
			

		
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

