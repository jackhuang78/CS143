
(*
 *  execute "coolc bad.cl" to see the error messages that the coolc parser
 *  generates
 *
 *  execute "parsetest < bad.cl" to see the error messages that your parser
 *  generates
 *)

(* no error *)
class A {
};

(* error:  b is not a type identifier *)
Class b inherits A {
};

(* error:  a is not a type identifier *)
Class C inherits a {
	func() : Int{
		while 100 > 34 loop 
		pool
	}
};

(* error:  keyword inherits is misspelled *)
Class D inherts A {
	
};


class F {
	init0() : Int { 
		{
			(* error in block *)
			asd qwe 2 ;
		}
	};
	init(obj:F) : Int { 
		{
			(* error in block *)
			init[);
			init(new F)
			asd qwe 3;
		}
	};
	init2(obj:F, b: Int) : Int {
		let a : Int <- 1 in		
		let b : Int <- , c : Int in 
		let d : Int <- 3 a : Int <- 4 in a 	
	
	};
	init4(obj:F, b: Int, d: Bool) : Int { 
		(new F) @E .init4(new F,2,false)
	};

};


(* error:  closing brace is missing *)
Class e inherits A {
;

