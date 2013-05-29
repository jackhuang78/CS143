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
	init_parent_self(self:PPP) : C{
	   {
		self;
	   }
	};
	init_parent_pppccc(x:PPP) : C{
	   {
		ppp;
	   }
	};
	init_parent_ppppp(x:Int) : PPP{
	   {
		ppp;
	   }
	};
	init_parent_ppp(x:Int) : C{
	   {
		ppp;
	   }
	};
	init_parent_duplicate(x:Int, x:Int, y: String) : C{
	   {
		ppp;
	   }
	};
	init_parent_int(x:Int) : C{
	   {
		self;
	   }
	};
	init_parent(x:SELF_TYPE) : C{
	   {
		self;
	   }
	};
	init_parent2(x:SELF_TYPE) : SELF_TYPE{
	   {
		self;
	   }
	};
	init_parent3(x:Int) : C{
	   {
		a <- x;
		if a < 10 then 
			if b <= 20 then
				a <- 13
			else 
				if b < 20 then
					a <- 13
				else 
					if b = 20 then
						b <- 13
					else 
						if a = 20 then
							b <- 13
						else 
							if a <= 20 then
								a <- 13
							else 
								new C2
							fi
						fi
					fi
				fi
			fi
		else
			if "3123" then
				b <- new C3
			else
				a <- "12334"
			fi
		fi;

		while "asdaweqwe" < 10 loop
			{
				a = ~false;
				b = ~13;
				a = ~23;
				b = ~"1233";
			}
		pool;

		while "yes" loop
			{
				a = a + 1 * a / 2 - ~a;
				b = not isvoid b;
				ff <- self;
			}
		pool;
	
		10 = 10;
		10 = true;
		false = "3123";
		"123123" = "123";
		false = 10;
		true = "123";
		"123123" = false;
		not 10;
		not "12312312";
		not true;
		b = not "123123";
		c = not "123";

		case self of
			a : F => (a <- new F);
			b : B => self;
			c : G => new G;
		esac;

		case self of
			a : C => 20;
			a : C => 30;
			a : C1 => self;
			c : C2 => new C2;
		esac;

		case xx of
			a : C => 20;
			a : C => 30;
			a : C1 => self;
			c : C2 => new C2;
		esac;

		case a of
			self : C => 20;
			self : C => 30;
			self : C1 => self;
			c : C2 => new C2;
		esac;


		let x:Bool<-self, zz:Bool in
		{
			x <- not x;
			zz <- not zz;
			let x:Int, y:Int in
			{
				x <- 2 + 3;
				y <- x;
			};
			
		};

		let x:C2, zz:C3 in
		{
			self;
			
		};

		let x:C2, zz:C3 in
		{
			x <- new C3;
			x <- new C;
			ddddd <- x;
			zz <- zz;
			
		};

		let x:C2, zz:C3 in
		{
			x <- new C3;
			zz <- new C;
			
		};

		let x:PPP, y:Int in
		{
			x <- 2 + 3;
			y <- x;
		};

		let x:Int<-a, y:Int, z:SELF_TYPE in 
		{
			x <- x + y;
			z <- new SELF_TYPE;
			let x:Bool<-true, zz:Bool in
			{
				x <- not x;
				zz <- not zz;
				let x:Int, y:Int in
				{
					x <- 2 + 3;
					y <- x;
				};
				
			};
		};

		let self:Int<-a, y:Int, z:SELF_TYPE in 
		{
			x <- x + y;
			z <- new SELF_TYPE;
			let x:Bool<-self, zz:Bool in
			{
				x <- not x;
				zz <- not zz;
				let x:Int, y:Int in
				{
					x <- 2 + 3;
					y <- x;
				};
				
			};
		};

		

	   }
	};
};

class C2 inherits C {
	a : Int;
	b : Bool;
	init2(x : Int, y : Bool, z: String) : SELF_TYPE {
           {
		a <- x;
		b <- y;
		self;
           }
	};
	init22(x : C, y : C3) : SELF_TYPE {
           {
		a <- x;
		b <- y;
		self;
           }
	};
};

class C3 inherits C2 {
	a : Int;
	b : Bool;
	init3(x : Int, y : Bool, o: Object) : SELF_TYPE  {
           {
		a <- x;
		b <- y;
		
           }
	};
};


class D inherits C {
	self : C;
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
	x : C;
	z : D;
	h : C2;
	g : String;
        t : Main;
	q : C2;
	b : C;
        m : C3;
	main():C {
	 {
	  iioqwe;
          x;
	  q;
	  x <- q;
	  q <- x;
	  x <- new C;
	  x <- new D;
	  y <- new C;
	  z <- new C;
          h <- new C2;
	  h <- self;
	  (new C).init(1,1);
	  (new C).init(1,true,3);
	  (new C).iinit(1,true);
	  (new C);
	  h.init(yes,no);
          h.init(1,true);
	  h.init(1,true);
	  h.init(1,true,"1234");
	  q.init2(1,true,"12333");
	  q.init22(x,q);
          q.init22(x,x);
	  q.init22(q,x);
	  q.init22(m,q);
	  q.init22(q,m);
	  q.init22(m,x);
	  q@C2.init22(x,q);
          q@C2.init22(x,x);
	  q@C2.init22(q,x);
	  q@C2.init22(m,q);
	  q@D.init22(q,m);
	  q@C3.init22(m,x);
	  x@C2.init22(x,q);
          m@C2.init22(x,x);
	  x@C2.init22(q,x);
	  m@C2.init22(m,q);
	  x@D.init22(q,m);
	  m@C3.init22(m,x);
	  m <- q.init2(1,true,"12333");
          g <- h.init(1,true);
	  g <- h.init(1,true,"1234");
	  g <- h@C.init(1,true,"1234");
	  g <- h@C.init(1,true);
          g <- h@CC.init(1,true, "String", 13);
	  g <- h@CC.init(1,true);
	  g <- h@C3.init(1,true);
	  g <- h@C2.init(1,true);
	  q <- h@C.init(1,true,"1234");
	  q <- h@C.init(1,true);
	  b <- h@C.init(1,true,"1234");
	  b <- h@C.init(1,true);
          (new C2).init2(1,true,"1234");
          (new C2).init2(1,true);
	  (new C).init_parent(x);
	  (new C).init_parent(h);
          (new C).init_parent(123);
	  (new C).init_parent2(h);
	  (new C).init_parent2(123);
          (new C).init_parent2("123");
	  (new C).init_parent3(h);
	  (new C).init_parent3(133);
          (new C).init_parent3(m);
	  (new C).init_parent(x);
	  (new C2).init_parent(123);
          (new C2).init_parent(123);
	  (new C2).init_parent2(h);
	  (new C2).init_parent2(123);
          (new C2).init_parent2("123");
	  (new C2).init_parent3(x);
	  (new C2).init_parent3(h);
          (new C2).init_parent3(m);
	
	
	 }
	};
};


