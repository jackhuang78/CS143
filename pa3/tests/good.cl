class C {
	a : Int;
	b : Bool;
	init(x : Int, y : Bool) : C {
           {
		a <- x;
		b <- y;
		self;
           }
	};
};

Class Main {
	main():C {
	  (new C).init(1,true)
	};
};

Class D inherits C {
	c:String;
	d:Int <- 10;
	init(x:Int, z:Bool):C{
		self
	};
	
	inc():Int{
		d + 1
	};
};
