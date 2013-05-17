class C {
	a : Int;
	b : Bool;
	init(x : Int, y : Bool) : C {
           {
		a <- x;
		b <- y;
		c;
		d;
		self;
           }
	};
};

class C2 inherits C {
	a : Int;
	b : Bool;
	init(x : Int, y : Bool, z: String) : SELF_TYPE {
           {
		a <- x;
		b <- y;
           }
	};
};

class C3 inherits C2 {
	a : Int;
	b : Bool;
	init(x : Int, y : Bool) : SELF_TYPE  {
           {
		a <- x;
		b <- y;
           }
	};
};


class D inherits C {
	a : Str;
	b : Bool;
	z : String;
	y : Int;
	o : Int;
	x : D;
	i : C;
	init(x : Int, y : Bool) : C {
           {
		a <- x;
		b <- y;
		c;
		self;
           }
	};
	init(x : Int, y : Bool) : C {
           {
		a <- x;
		b <- y;
		c;
		self;
           }
	};
	god(x: Int, y: Bool) : C{
	   {
		b <- x;
		b <- y;
		o <- y;
		o <- o;
		self <- o;
		b <- true;
		b <- x + true;
		b <- "str";
		y <- 11;
		y <- "str";
		self <- y;
		y <- self;
		y <- 11 + 12;
		y <- 11 + true;
		y <- false - 12;
		y <- 11 * false;
		y <- "123" / 13;
		z <- 13;
		z <- "123";
		z <- "123" + "123";

	   }
	};
	new_(x: Int, y: Bool, z: Object) : C{
	   {
		z <- new Object;
	        z <- new DDDDD; 
		

	   }
	};

};

Class Main {
	main():C {
	 {
	  (new C).init(1,1);
	  (new C).init(1,true,3);
	  (new C).iinit(1,true);
	  (new C);
	 }
	};
};


