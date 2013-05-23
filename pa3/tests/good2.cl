class Main {
	main():Int{0};
};

class A inherits Main {
	x:Int;
	y:Bool;
	z:String;
	o:Object;
	m:Main;
	s:SELF_TYPE;
	a:A;
	b:AB;
	
	xx:Int <- 1;
	yy:Bool <- true;
	zz:String <- "str";
	oo:Object <- new Object;
	mm:Main <- new Main;
	ss:SELF_TYPE <- self;
	aa:A <- new A;
	bb:AB <- new AB;
	
	(* Id, True, False, Int, String, New *)
	var():SELF_TYPE {
		{
			1;
			true;
			false;
			"str";
			"";
			new A;
			new AB;
			new Object;
			self;
		}
	};
	
	(* Assign *)
	assign():SELF_TYPE {
		{
			x <- x <- 1;
			y <- true;
			z <- "str";
			o <- new Object;
			s <- self;
			s <- new SELF_TYPE;

			m <- new Main;
			m <- new A;
			m <- new AB;
			m <- new SELF_TYPE;
			
			a <- new A;
			a <- new AB;
			a <- new SELF_TYPE;
			
			b <- new AB;
			self;			
		}
	};
	
	op():SELF_TYPE {
		{
			x + x;
			x - x;
			x * x;
			x / x;
			~x;
			x < 10;
			x <= 10;
			x = 10;
			not true;
			
			true = y;
			"abc" = z;
			
			
		}
	};
	
	cond():SELF_TYPE {
		{
			if y then new ABC else new ABD fi;
			if not y then new SELF_TYPE else new AB fi;
			if false then new Main else new IO fi;
			if true then x else 1 fi;
			
			
			while y loop x pool;
			while true loop new SELF_TYPE pool;
			
			self;
		
		}
	};
	
	(* parameters, let, let-init, case *)
	localvar(x:Bool, y:String, z:Int):SELF_TYPE {
		{
			x;y;z;m;o;s;a;b;
			
			let x:String, y:Int, z:Bool in 
			{
				x;y;z;m;o;s;a;b;
				let x:A, y:AB, z:ABC in{x;y;z;m;o;s;a;b;};
				x;y;z;m;o;s;a;b;
			};			
			x;y;z;m;o;s;a;b;
			
			let x:IO, y:Main, z:SELF_TYPE in{x;y;z;m;o;s;a;b;};			
			x;y;z;m;o;s;a;b;
			
			let x:A<-new A, y:A<-new AB, z:A<-new ABC, m:Main<-new ACB in 
			{	
				x;y;z;m;o;s;a;b;
			};
			
			case self of 
				x:A => new AB;
				y:AB => new A;
				z:ABC => 1;
			esac;
			
			case self of 
				x:A => {x;y;z;m;o;s;a;b;};
				y:AB => {x;y;z;m;o;s;a;b;};
				z:ABC => {x;y;z;m;o;s;a;b;};
			esac;
			
			
			self;		
			
		}
	};
	
	fun(a:Int, b:A, c:AB):SELF_TYPE {
		self
	};
};


class AB inherits A{
	fun(a:Int, b:A, c:AB):SELF_TYPE {
		self
	};
};
class AC inherits A{};
class AD inherits A{};
class ABC inherits AB{};
class ABD inherits AB{};
class ACB inherits AC{};
class ACD inherits AC{};
class ADB inherits AD{};
class ADC inherits AD{};

class Z {
	init(a:A, ab:AB, abc:ABC):Int {
		{
			a.fun(1, a, ab);
			a@A.fun(1,a,ab);
			ab.fun(1,a,ab);
			ab@A.fun(1,a,ab);
			ab@AB.fun(1,a,ab);
			
			a.main();
			a@Main.main();
			ab.main();
			ab@A.main();
			ab@Main.main();
			
			0;
		}
	};
};


