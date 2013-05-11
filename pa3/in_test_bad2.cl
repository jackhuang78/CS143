class C inherits Main{
	
};

Class E inherits C{

};

Class F inherits E{

};

Class Main inherits F{
	main():C {
		new C
	};
};

Class G inherits F {
};



