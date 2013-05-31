(* hairy  . . .*)

class Foo inherits Bazz {

	outFoo:IO <- new IO.out_string(self.type_name().concat(" start Foo\n"));

     a : Razz <- case self of
		      n : Razz => (new Bar);
		      n : Foo => (new Razz);
		      n : Bar => n;
   	         esac;
	outFoo3:IO <- new IO.out_string(self.type_name().concat(" mid Foo\n"));
	
     b : Int <- a.doh() + g.doh() + doh() + printh();

     doh() : Int {{
	     outFoo.out_string(self.type_name().concat(" doh Foo start\n"));
     	(let i : Int <- h in { h <- h + 2; i; } );
     }};
     
   	outFoo2:IO <- new IO.out_string(self.type_name().concat(" end Foo\n"));

};

class Bar inherits Razz {

	outBar:IO <- new IO.out_string(self.type_name().concat(" start Bar\n"));

     c : Int <- doh();

     d : Object <- printh();
     
    outBar2:IO <- new IO.out_string(self.type_name().concat(" end Bar\n"));
};


class Razz inherits Foo {

	outRazz:IO <- new IO.out_string(self.type_name().concat(" start Razz\n"));

     e : Bar <- case self of
		  n : Razz => (new Bar);
		  n : Bar => n;
		esac;

	outRazz3:IO <- new IO.out_string(self.type_name().concat(" mid Razz\n"));

     f : Int <- a@Bazz.doh() + g.doh() + e.doh() + doh() + printh();

	outRazz2:IO <- new IO.out_string(self.type_name().concat(" end Razz\n"));
};

class Bazz inherits IO {

	out:IO <- new IO.out_string(self.type_name().concat(" start Bazz\n"));

     h : Int <- 1;

     g : Foo  <- case self of
		     	n : Bazz => (new Foo);
		     	n : Razz => (new Bar);
			n : Foo  => (new Razz);
			n : Bar => n;
		  esac;

     i : Object <- printh();

	out2:IO <- new IO.out_string(self.type_name().concat(" end Bazz\n"));

     printh() : Int { { out_int(h); 0; } };

     doh() : Int {{ 
     	out.out_string(self.type_name().concat(" doh Bazz start\n"));
     	(let i: Int <- h in { h <- h + 1; i; } ) ;
     }};
};

(* scary . . . *)
class Main {
  a : Bazz <- new Bazz;
  b : Foo <- new Foo;
  c : Razz <- new Razz;
  d : Bar <- new Bar;

  main(): String { "do nothing" };

};





