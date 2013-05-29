class Main {
	out:IO <- new IO;
	
	main():Int{
		{
			out.out_string(new Object.type_name());
			out.out_string(type_name());
			out.out_string(new A.type_name());
			out.out_string(new B.type_name());
			out.out_string(new C.type_name());		
			0;
		}
	};
};

class A{};
class B inherits A{};
class C inherits B{};
