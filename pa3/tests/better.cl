
class Main {
	a:F;
	b:G;
	c:H;
	
	main():Int{
		{
			a <- new F;
			b <- new G;
			c <- new H;
			0;
		}
	};
};

class A {

	ww:A;
	xx:Int;
	yy:Bool;
	zz:String;

	a:A <- new A;
	b:Int <- 3;
	c:Bool <- true;
	d:String <- "abc";
	e:SELF_TYPE <- new SELF_TYPE;
	f:A <- new SELF_TYPE;
	
	init(w:A, x:Int, y:Bool, z:String):SELF_TYPE {
		{
			w <- new A;
			w <- new SELF_TYPE;
			x <- 4;
			y <- true;
			y <- false;
			z <- "abc";
			
			self.func(x, y, z);
			w.func(x,y,z);
				
			self;
		}
		
		
	};
	
	func(a:Int, b:Bool, c:String):Int {
		{
			
			if a < 10 then 
				if a <= 20 then
					new O
				else 
					new F
				fi
			else
				if a = 30 then
					b <- new G
				else
					c <- new M
				fi
			fi;	
						
			while a < 10 loop
				{
					a = a + 1 * a / 2 - ~a;
					b = not isvoid b;
				}
			pool;
			
			case self of
				a : F => (a <- new F);
				b : B => self;
				c : G => new G;
			esac;			



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
			
			
			
			0;
		}
	};
	

};

class B inherits A {

	init(w:A, x:Int, y:Bool, z:String):SELF_TYPE {
		{
			w <- new SELF_TYPE;
			w <- new B;
			x <- 4;
			y <- true;
			y <- false;
			z <- "abc";
			
			self@A.func(x,y,z);
			w@A.func(x, y, z);
				
			self;
		}
		
		
	};
	
	func(a:Int, b:Bool, c:String):Int {
		{
			1;
		}
	};

};

class C inherits B {
};
class D inherits A {
};
class E inherits D {
};
class F inherits E {
};
class G inherits D {
};
class H inherits C {
};
class I inherits H {
};
class J inherits G {
};
class K inherits C {
};
class L inherits E {
};
class M inherits D {
};
class N inherits F {
};
class O inherits A {
};
class P inherits B {
};
