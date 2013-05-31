class Main {
	i1:Int;
	i2:Int;
	i3:Int;
	i4:Int;
	b1:Bool;
	b2:Bool;
	b3:Bool;
	b4:Bool;
	s1:String;
	s2:String;
	s3:String;
	s4:String;
	s5:String;
	o1:Object;
	o2:Object;
	o3:Object;
	o4:Object;
	o5:Object;
	
	out:IO <- new IO;
	
	main():Int {{
		
		i1 <- 1;
		i2 <- 1;
		i3 <- 2;
		i4 <- i1;
		
		toString("i1 = i1", i1 = i1);		
		toString("i1 = i2", i1 = i2);
		toString("i1 = i3", i1 = i3);
		toString("i1 = i4", i1 = i4);
		toString("i2 = i1", i2 = i1);		
		toString("i2 = i2", i2 = i2);
		toString("i2 = i3", i2 = i3);
		toString("i2 = i4", i2 = i4);
		toString("i3 = i1", i3 = i1);		
		toString("i3 = i2", i3 = i2);
		toString("i3 = i3", i3 = i3);
		toString("i3 = i4", i3 = i4);
		toString("i4 = i1", i4 = i1);		
		toString("i4 = i2", i4 = i2);
		toString("i4 = i3", i4 = i3);
		toString("i4 = i4", i4 = i4);
		
		b1 <- true;
		b2 <- false;
		b3 <- true;
		b4 <- b1;
		
		toString("b1 = b1", b1 = b1);		
		toString("b1 = b2", b1 = b2);
		toString("b1 = b3", b1 = b3);
		toString("b1 = b4", b1 = b4);
		toString("b2 = b1", b2 = b1);		
		toString("b2 = b2", b2 = b2);
		toString("b2 = b3", b2 = b3);
		toString("b2 = b4", b2 = b4);
		toString("b3 = b1", b3 = b1);		
		toString("b3 = b2", b3 = b2);
		toString("b3 = b3", b3 = b3);
		toString("b3 = b4", b3 = b4);
		toString("b4 = b1", b4 = b1);		
		toString("b4 = b2", b4 = b2);
		toString("b4 = b3", b4 = b3);
		toString("b4 = b4", b4 = b4);		
		
		s1 <- "abc";
		s2 <- "def";
		s3 <- "abc";
		s4 <- s1;
		s5 <- new String;
		s5.concat("abc");
		toString("s1 = s1", s1 = s1);		
		toString("s1 = s2", s1 = s2);
		toString("s1 = s3", s1 = s3);
		toString("s1 = s4", s1 = s4);
		toString("s1 = s5", s1 = s5);
		toString("s2 = s1", s2 = s1);		
		toString("s2 = s2", s2 = s2);
		toString("s2 = s3", s2 = s3);
		toString("s2 = s4", s2 = s4);
		toString("s2 = s5", s2 = s5);
		toString("s3 = s1", s3 = s1);		
		toString("s3 = s2", s3 = s2);
		toString("s3 = s3", s3 = s3);
		toString("s3 = s4", s3 = s4);
		toString("s3 = s5", s3 = s5);
		toString("s4 = s1", s4 = s1);		
		toString("s4 = s2", s4 = s2);
		toString("s4 = s3", s4 = s3);
		toString("s4 = s4", s4 = s4);
		toString("s4 = s5", s4 = s5);
		toString("s5 = s1", s5 = s1);		
		toString("s5 = s2", s5 = s2);
		toString("s5 = s3", s5 = s3);
		toString("s5 = s4", s5 = s4);
		toString("s5 = s5", s5 = s5);		
		
		o1 <- new Object;
		o2 <- new Object;
		o3 <- o1;
		o4;
		o5;
		toString("o1 = o1", o1 = o1);		
		toString("o1 = o2", o1 = o2);
		toString("o1 = o3", o1 = o3);
		toString("o1 = o4", o1 = o4);
		toString("o1 = o5", o1 = o5);
		toString("o2 = o1", o2 = o1);		
		toString("o2 = o2", o2 = o2);
		toString("o2 = o3", o2 = o3);
		toString("o2 = o4", o2 = o4);
		toString("o2 = o5", o2 = o5);
		toString("o3 = o1", o3 = o1);		
		toString("o3 = o2", o3 = o2);
		toString("o3 = o3", o3 = o3);
		toString("o3 = o4", o3 = o4);
		toString("o3 = o5", o3 = o5);
		toString("o4 = o1", o4 = o1);		
		toString("o4 = o2", o4 = o2);
		toString("o4 = o3", o4 = o3);
		toString("o4 = o4", o4 = o4);
		toString("o4 = o5", o4 = o5);
		toString("o5 = o1", o5 = o1);		
		toString("o5 = o2", o5 = o2);
		toString("o5 = o3", o5 = o3);
		toString("o5 = o4", o5 = o4);
		toString("o5 = o5", o5 = o5);
		
		0;	
	}};
	
	
	toString(s:String, b:Bool):String {{
		if b then out.out_string(s.concat(" true\n"))
		else out.out_string(s.concat(" false\n")) fi;
		s;
	}};
	
	
};
