(* inheritance cycle *)
class X inherits Y{};
class Y inherits Z{};
class Z inherits X{};

(* redefining of basic class *)
class Object{};
class Int{};
class String{};
class Bool{};
class IO{};

(* inheriting basic class *)
class A inherits Object{};
class B inherits Int{};
class C inherits String{};
class D inherits Bool{};
class E inherits IO{};

(* inheriting undefined class *)
class F inherits Double{};

(* inheriting self *)
class G inherits G{};

(* inheriting illegally defined class *)
class AA inherits A{};
class BB inherits B{};
class CC inherits C{};
class DD inherits D{};
class EE inherits E{};
class FF inherits F{};
class GG inherits G{};
class XX inherits X{};
class YY inherits Y{};
class ZZ inherits Z{};




(* redefining user-defined class *)
class A{};
class B{};
class C{};
class D{};
class E{};
class F{};
class G{};
class AA{};
class BB{};
class CC{};
class DD{};
class EE{};
class FF{};
class GG{};
class X{};
class Y{};
class Z{};
class XX{};
class YY{};
class ZZ{};





