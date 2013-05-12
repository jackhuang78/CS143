(* inheritance cycle *)
class X inherits Y{};
class Y inherits Z{};
class Z inherits X{};

(* inheriting self *)
class G inherits G{};

(* inheriting illegally defined class *)
class GG inherits G{};
class XX inherits X{};
class YY inherits Y{};
class ZZ inherits Z{};





