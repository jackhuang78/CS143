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
	init(x:Int, z:Bool):C{
		self
	};
	
	inc():Int{
		x + 1
	};
};
