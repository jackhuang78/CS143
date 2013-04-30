
(*
 *  execute "coolc bad.cl" to see the error messages that the coolc parser
 *  generates
 *
 *  execute "parsetest < bad.cl" to see the error messages that your parser
 *  generates
 *)

class FeatureError {
	a1 : Int;
	b : ;
	a2 : Int;
	c Int;
	a3 : Int;
	: Int;
	a4:Int;
	d ;
	a5:Int;
	: ;
	a6:Int;
	Int ;
	a7:Int;
	e : Int;
	a8:Int;
	f g : Int;
	a9:Int;
};

class LetError {
	
}



(* no error *)
class A {
};

(* error:  b is not a type identifier *)
Class b inherits A {
};

(* error:  a is not a type identifier *)
Class C inherits a {
};

(* error:  keyword inherits is misspelled *)
Class D inherts A {
	
};


(*
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
		(new F).init2(new F,2)
	};
	init4(obj:F, b: Int, d: Bool) : Int { 
		(new F) @E .init4(new F,2,false)
	};

};


(* error:  closing brace is missing *)
Class E inherits A {
;
*)
