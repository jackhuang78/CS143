import java.io.PrintStream;
import java.util.*;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
	private int semantErrors;
	private PrintStream errorStream;
	
	// PA3
	private Node treeRoot;
	private Map<AbstractSymbol, Node> nodeMap;
	private class_c curClass;

	/** Creates data structures representing basic Cool classes (Object,
	 * IO, Int, Bool, String).  Please note: as is this method does not
	 * do anything useful; you will need to edit it to make if do what
	 * you want.
	 * */
	private void installBasicClasses() {
		AbstractSymbol filename 
			= AbstractTable.stringtable.addString("<basic class>");
		
		// The following demonstrates how to create dummy parse trees to
		// refer to basic Cool classes.  There's no need for method
		// bodies -- these are already built into the runtime system.

		// IMPORTANT: The results of the following expressions are
		// stored in local variables.  You will want to do something
		// with those variables at the end of this method to make this
		// code meaningful.

		// The Object class has no parent class. Its methods are
		//		cool_abort() : Object	aborts the program
		//		type_name() : Str		returns a string representation 
		//								 of class name
		//		copy() : SELF_TYPE	   returns a copy of the object

		class_c Object_class = 
			new class_c(0, 
					   TreeConstants.Object_, 
					   TreeConstants.No_class,
					   new Features(0)
						   .appendElement(new method(0, 
											  TreeConstants.cool_abort, 
											  new Formals(0), 
											  TreeConstants.Object_, 
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.type_name,
											  new Formals(0),
											  TreeConstants.Str,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.copy,
											  new Formals(0),
											  TreeConstants.SELF_TYPE,
											  new no_expr(0))),
					   filename);
		
		// The IO class inherits from Object. Its methods are
		//		out_string(Str) : SELF_TYPE  writes a string to the output
		//		out_int(Int) : SELF_TYPE	  "	an int	"  "	 "
		//		in_string() : Str			reads a string from the input
		//		in_int() : Int				"   an int	 "  "	 "

		class_c IO_class = 
			new class_c(0,
					   TreeConstants.IO,
					   TreeConstants.Object_,
					   new Features(0)
						   .appendElement(new method(0,
											  TreeConstants.out_string,
											  new Formals(0)
												  .appendElement(new formalc(0,
																	 TreeConstants.arg,
																	 TreeConstants.Str)),
											  TreeConstants.SELF_TYPE,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.out_int,
											  new Formals(0)
												  .appendElement(new formalc(0,
																	 TreeConstants.arg,
																	 TreeConstants.Int)),
											  TreeConstants.SELF_TYPE,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.in_string,
											  new Formals(0),
											  TreeConstants.Str,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.in_int,
											  new Formals(0),
											  TreeConstants.Int,
											  new no_expr(0))),
					   filename);

		// The Int class has no methods and only a single attribute, the
		// "val" for the integer.

		class_c Int_class = 
			new class_c(0,
					   TreeConstants.Int,
					   TreeConstants.Object_,
					   new Features(0)
						   .appendElement(new attr(0,
											TreeConstants.val,
											TreeConstants.prim_slot,
											new no_expr(0))),
					   filename);

		// Bool also has only the "val" slot.
		class_c Bool_class = 
			new class_c(0,
					   TreeConstants.Bool,
					   TreeConstants.Object_,
					   new Features(0)
						   .appendElement(new attr(0,
											TreeConstants.val,
											TreeConstants.prim_slot,
											new no_expr(0))),
					   filename);

		// The class Str has a number of slots and operations:
		//	   val							  the length of the string
		//	   str_field						the string itself
		//	   length() : Int				   returns length of the string
		//	   concat(arg: Str) : Str		   performs string concatenation
		//	   substr(arg: Int, arg2: Int): Str substring selection

		class_c Str_class =
			new class_c(0,
					   TreeConstants.Str,
					   TreeConstants.Object_,
					   new Features(0)
						   .appendElement(new attr(0,
											TreeConstants.val,
											TreeConstants.Int,
											new no_expr(0)))
						   .appendElement(new attr(0,
											TreeConstants.str_field,
											TreeConstants.prim_slot,
											new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.length,
											  new Formals(0),
											  TreeConstants.Int,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.concat,
											  new Formals(0)
												  .appendElement(new formalc(0,
																	 TreeConstants.arg, 
																	 TreeConstants.Str)),
											  TreeConstants.Str,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.substr,
											  new Formals(0)
												  .appendElement(new formalc(0,
																	 TreeConstants.arg,
																	 TreeConstants.Int))
												  .appendElement(new formalc(0,
																	 TreeConstants.arg2,
																	 TreeConstants.Int)),
											  TreeConstants.Str,
											  new no_expr(0))),
					   filename);

		/* Do somethind with Object_class, IO_class, Int_class,
		   Bool_class, and Str_class here */
		 
		// PA3
		treeRoot = new Node(Object_class, null);
		//treeRoot = new Node(TreeConstants.prim_slot, null);
		nodeMap = new LinkedHashMap<AbstractSymbol, Node>();
		nodeMap.put(Object_class.getName(), treeRoot);
		//nodeMap.put(Object_class.getName(), new Node(Object_class, treeRoot));
		nodeMap.put(Int_class.getName(), new Node(Int_class, treeRoot));
		nodeMap.put(Bool_class.getName(), new Node(Bool_class, treeRoot));
		nodeMap.put(Str_class.getName(), new Node(Str_class, treeRoot));
		nodeMap.put(IO_class.getName(), new Node(IO_class, treeRoot));
		curClass = null;
		
		if(Flags.semant_debug) {
			System.out.println("\nInitial tree: \n" + this);
			System.out.println("\nInitial map:\n" + nodeMap);
		}
	}

		
	/*
		1. Obtain a list of classes
		2. Check for Main and redefinition of basic classes
		3. Partition the list into two lists:
			- List A: classes that inherit Object, IO, or other user-defined classes
			- List B: classes that inherit undefined classes or {Int, Bool, String}; report each class
		5. Build classes in List A into tree:
			- Using Object as root
			- Using each class in List B as root
		6. Whatever classes left in List A that's not built into a tree, report each as cyclic inheritance
	*/
	
	public ClassTable(Classes cls) {
		semantErrors = 0;
		errorStream = System.err;
		
		
		// PA3
		// initalizatoin
		installBasicClasses();
				
		// add classes into tree
		List<class_c> classList = new ArrayList<class_c>();
		for(int i = 0; i < cls.getLength(); i++)
			classList.add((class_c)cls.getNth(i));
		if(Flags.semant_debug)
			System.out.println("\nclassList: \n" + classList);
		
			
		// 1. check for redefinition
		for(class_c c : classList) {
			
			if(c.getName() == TreeConstants.Object_)
				semantError(c).println("Redefinition of basic class Object.");
				
			else if(c.getName() == TreeConstants.Int)
				semantError(c).println("Redefinition of basic class Int.");
				
			else if(c.getName() == TreeConstants.Bool)
				semantError(c).println("Redefinition of basic class Bool.");
			
			else if(c.getName() == TreeConstants.Str)
				semantError(c).println("Redefinition of basic class String.");
				
			else if(c.getName() == TreeConstants.IO) 
				semantError(c).println("Redefinition of basic class IO.");

			else if(nodeMap.containsKey(c.getName()))
				semantError(c).println("Class " + c.getName() + " was previously defined.");
			
			else
				nodeMap.put(c.getName(), null);
		}
		
		// 2. check for inheriting basic class and inheriting undefined class
		for(int i = classList.size() - 1; i >= 0; i--) {
			class_c c = classList.get(i);
			if(c.getParent() == TreeConstants.Int)
				semantError(c).println("Class " + c.getName() + " cannot inherit class Int.");
			else if(c.getParent() == TreeConstants.Bool)
				semantError(c).println("Class " + c.getName() + " cannot inherit class Bool.");
			else if(c.getParent() == TreeConstants.Str)
				semantError(c).println("Class " + c.getName() + " cannot inherit class String.");
			else if(!nodeMap.containsKey(c.getParent()))
				semantError(c).println("Class " + c.getName() + " inherits from an undefined class " + c.getParent() + ".");
		
		}
		
		// if there has been any error, stop here
		if(errors())
			return;
		
		/*
			At this point, there is...
			- no redefining basic or user-defined class
			- no inheriting from String, Int, or Bool
			- no inheriting from undefined class
			
			We can build the tree and check for cyclic inheritance.		
		*/
		boolean update = true;
		while(update) {
			update = false;
			for(Iterator<class_c> itor = classList.iterator(); itor.hasNext(); ) {
				class_c c = itor.next();
				if(nodeMap.get(c.getParent()) != null) {
					nodeMap.put(c.getName(), new Node(c, nodeMap.get(c.getParent())));
					itor.remove();
					update = true;
				}
			}
		}
		
		//buildTree(treeRoot, classList);
		
		// 3. checck for cyclic inheritance
		for(int i = classList.size() - 1; i >= 0; i--) {
			class_c c = classList.get(i);
			semantError(c).println(
				"Class " + c.getName() + ", or an ancestor of " + 
				c.getName() + ", is involved in an inheritance cycle.");
		}
		
		
		if(errors())
			return;
		
		// 4. check for absence of class Main or method main
		if(!nodeMap.containsKey(TreeConstants.Main))
			semantError().println("Class Main is not defined.");
		// TODO check main method in Main
		//else if(!nodeMap.get(TreeConstants.Main).methods.containsKey(TreeConstants.main_meth))
		//	semantError(nodeMap.get(TreeConstants.Main).value).println("No 'main' method in class Main.");
		
		if(Flags.semant_debug) {
			/*System.out.println("\nFinal class tree: \n" + this);
			System.out.println("\nFinal node map:\n" + nodeMap);
			
			System.out.println(getAttribute(lookup("D"), lookup("b"), true, true));
			System.out.println(getAttribute(lookup("D"), lookup("name"), true, true));
			System.out.println(getAttribute(lookup("D"), lookup("X"), true, true));
			System.out.println(getMethodParams(lookup("D"), lookup("main"), true, true));
			System.out.println(getMethodParams(lookup("X"), lookup("Def"), true, true));			
			
			
			System.out.println("LUB: " + lub(lookup("G"), lookup("C")));
			System.out.println("LUB: " + lub(lookup("I"), lookup("Main")));
			System.out.println("LUB: " + lub(lookup("I"), lookup("C")));
			System.out.println("LUB: " + lub(lookup("F"), lookup("E")));
			
			System.out.println("LE: " + le(lookup("I"), lookup("B")));
			System.out.println("LE: " + le(lookup("I"), lookup("Int")));
			System.out.println("LE: " + le(lookup("B"), lookup("I")));
			System.out.println("LE: " + le(lookup("G"), lookup("A")));
			*/
		}
		
		
		
	} // END ClassTable constructor
	
	
	public void constructScope() {
	// 5. Construct object/method environment for every class
		for(AbstractSymbol clazz : nodeMap.keySet()) {
			if(Flags.semant_debug)
				System.out.println("Constructing object/method env for " + clazz);
			
			Node node = nodeMap.get(clazz);
			AbstractSymbol filename = node.value.getFilename();
			
			Features featList = node.value.getFeatures();
			for(int i = 0; i < featList.getLength(); i++) {
				Feature feat = (Feature)featList.getNth(i);
				
				if(feat instanceof attr) {
					attr a = (attr)feat;
					AbstractSymbol name = a.getName();
					AbstractSymbol type = a.getType();
					if(!hasClass(type)) {
						semantError(filename, a).printf("Class %s of attribute %s is undefined.\n", type, name);
					} 
					if(node.parent.attrMap.containsKey(name)) {
						semantError(filename, a).printf("Attribute %s is an attribute of an inherited class.\n", name);
						
					} else if(node.attrMap.containsKey(name)) {
						semantError(filename, a).printf("Attribute %s is multiply defined in class.\n", name);
						
					} else {
						node.attrMap.put(name, a.getType());
					}
					
				} else {
				
					method m = (method)feat;
					AbstractSymbol name = m.getName();
					Formals formList = m.getFormals();
					Map<AbstractSymbol, AbstractSymbol> params = null;
					if(node.parent != null)
						params = node.parent.methMap.get(name);

					boolean success = false;

					
					// if method is not defined in this class nor in parent class, add it
					if((node.parent == null || !node.parent.methMap.containsKey(name)) && ! node.methMap.containsKey(name)) {
						success = true;

					// error if the same method is defined in the same class					
					} else if(node.methMap.containsKey(name)) {
						semantError(filename, m).printf(
							"Method %s is multiply defined.\n", name);
					
					// error if return type is different
					} else if(params.get(null) != m.getRet()) {
							semantError(filename, m).printf(
								"In redefined method %s, return type %s is different from original return type %s.\n", 
								name, m.getRet(), params.get(null));
								
					// error if number of parameters is different		
					} else if(formList.getLength() != params.size() - 1) {
						semantError(filename, m).printf(
							"Incompatible number of formal parameters in redefined method %s.\n",
							name);
							
					// error if any parameter type is different
					} else {
					
						Iterator<AbstractSymbol> itor = params.keySet().iterator();
						for(int j = 0; j < formList.getLength(); j++) {
							AbstractSymbol key = itor.next();
							formalc form = (formalc)formList.getNth(j);
							if(key == null)
								continue;
							
							if(!hasClass(form.getType())) {
								semantError(filename, m).printf(
									"Class %s of formal parameter %s is undefined.\n",
									form.getType(), name);
							
							} else if(form.getType() != params.get(key)) {
								semantError(filename, m).printf(
									"In redefined method %s, parameter type %s is different from original type %s\n",
									name,
									form.getType(),
									params.get(key));
								break;
							}
						}
						success = true;							
					} // END method check block
					
					if(success) {
						Map<AbstractSymbol, AbstractSymbol> formMap = new LinkedHashMap<AbstractSymbol, AbstractSymbol>();
						for(int j = 0; j < formList.getLength(); j++) {
							formalc form = (formalc)formList.getNth(j);
							if(formMap.containsKey(form.getName())) {
								semantError(filename, form).printf(
									"Formal parameter %s is multiply defined.\n",
									form.getName());
								formMap.put(
									createNewSymbol(form.getName().toString()), 
									form.getType());
								
							} else	{
								formMap.put(form.getName(), form.getType());
							}
						}
						formMap.put(null, m.getRet());
						node.methMap.put(m.getName(), formMap);
					}
					
				} // END method block
			} // END feature loop
			
			// include parent's attributes and methods
			if(node.parent != null) {
				for(AbstractSymbol name : node.parent.attrMap.keySet())
					if(!node.attrMap.containsKey(name))
						node.attrMap.put(name, node.parent.attrMap.get(name));
						
				for(AbstractSymbol name : node.parent.methMap.keySet())
					if(!node.methMap.containsKey(name))
						node.methMap.put(name, node.parent.methMap.get(name));		

			}
			
			// construct scopes
			node.objectEnv = new SymbolTable();
			node.objectEnv.enterScope();
			for(AbstractSymbol name : node.attrMap.keySet())
				node.objectEnv.addId(name, node.attrMap.get(name));
			//node.objectEnv.addId(TreeConstants.self, TreeConstants.SELF_TYPE);
			
			node.methodEnv = new SymbolTable();
			node.methodEnv.enterScope();
			for(AbstractSymbol name : node.methMap.keySet())
				node.methodEnv.addId(name, node.methMap.get(name));
				
			if(Flags.semant_debug) {
				System.out.println("AttrEnv:\n" + node.objectEnv);
				System.out.println("MethEnv:\n" + node.methodEnv);
//				System.out.println("Constructing object/method env for " + clazz);
			}
			
			
			
			
		} // END class loop (constructing scope)				
		
		
		// 6. Check for main method in Main
		if(!nodeMap.get(TreeConstants.Main).methMap.containsKey(TreeConstants.main_meth))
			semantError(nodeMap.get(TreeConstants.Main).value).println("No 'main' method in class Main.");
	}

	private AbstractSymbol createNewSymbol(String name) {
		boolean nameExist = true;
		while(nameExist) {
			nameExist = false;
			name = name + "_";
			Enumeration e = AbstractTable.idtable.getSymbols();
			while(e.hasMoreElements()) {
				if(e.nextElement().toString().equals(name)) {
					nameExist = true;
					break;
				}
			}
		}
	
		return AbstractTable.idtable.addString(name);
	}

	
	private AbstractSymbol lookup(String name) {
		return AbstractTable.idtable.lookup(name);
	}
	
	public String toString() {
		return toString(treeRoot);
	}
	
	public String toString(Node root) {
		return nodeToString(root, 0, new StringBuffer()).toString();
	}
	
	private StringBuffer nodeToString(Node node, int lv, StringBuffer sb) {
		for(int i = 0; i < lv; i++)
			sb.append("\t");
		sb.append("\\______ ");
		
		sb.append("" + node.value.getName() + "\n");
		
		for(Node n : node.children)
			nodeToString(n, lv + 1, sb);
		
		return sb;
	}
	
	private LinkedList<AbstractSymbol> path(AbstractSymbol target) {
		LinkedList<AbstractSymbol> list = new LinkedList<AbstractSymbol>();
		
		Node n = nodeMap.get(target);
		do {
			list.addFirst(n.value.getName());
			n = n.parent;
		} while(n != null);
		
		return list;
	}
	
	
	public AbstractSymbol lub(AbstractSymbol t1, AbstractSymbol t2, class_c clazz) {
		//if(!nodeMap.containsKey(t1) || !nodeMap.containsKey(t2))
		//	return null;
		
		if(t1 == TreeConstants.SELF_TYPE && t2 == TreeConstants.SELF_TYPE)
			return t1;

		else if(t1 == TreeConstants.SELF_TYPE)
			return lub(clazz.getName(), t2, clazz);
		
		else if(t2 == TreeConstants.SELF_TYPE)
			return lub(t1, clazz.getName(), clazz);
		
		else {
			LinkedList<AbstractSymbol> p1 = path(t1);
			LinkedList<AbstractSymbol> p2 = path(t2);		
			AbstractSymbol common = p1.removeFirst();
			while(!p1.isEmpty() && p2.contains(p1.getFirst()))
				common = p1.removeFirst();	

			return common;		
		}	
	}
	
	public AbstractSymbol lub(List<AbstractSymbol> ts, class_c clazz) {
		//System.out.println("LUB: " + ts + " in " + clazz);
	
		if(ts.size() == 0)
			return null;
		else if(ts.size() == 1)
			return ts.get(0);
		else {
			Iterator<AbstractSymbol> itor = ts.iterator();
			AbstractSymbol common = lub(itor.next(), itor.next(), clazz);
			while(itor.hasNext())
				common = lub(common, itor.next(), clazz);
			return common;
		}
		
	}
	
	
	// HACK: actually GE (t1 and t2 switched)
	public boolean le(AbstractSymbol t1, AbstractSymbol t2, class_c clazz) {
	
		//System.out.printf("Is %s less than %s in %s?\n", t1, t2, clazz);
		
		//if(!nodeMap.containsKey(t1) || !nodeMap.containsKey(t2))
		//	return false;
		
		if(t1 == TreeConstants.SELF_TYPE && t2 == TreeConstants.SELF_TYPE)
			return true;
			
		else if(t1 == TreeConstants.SELF_TYPE)
			return le(clazz.getName(), t2, clazz);
			
		else if(t2 == TreeConstants.SELF_TYPE)
			return false;
			
		else			
			return path(t1).contains(t2);
	}
	
	public boolean hasClass(AbstractSymbol clazz) {
		return clazz == TreeConstants.prim_slot || 
				clazz == TreeConstants.SELF_TYPE || 
				nodeMap.containsKey(clazz);
	}
	
	public SymbolTable getMethodTable(AbstractSymbol clazz) {
		return nodeMap.get(clazz).methodEnv;
	}
	
	public SymbolTable getAttributeTable(AbstractSymbol clazz) {
		return nodeMap.get(clazz).objectEnv;
	}
	
	/*
	public AbstractSymbol getAttribute(AbstractSymbol clazz, AbstractSymbol name, boolean self, boolean ancestor) {

		// if CLAZZ is an undefined class, return null
		if(!nodeMap.containsKey(clazz))
			return null;
			
		
		LinkedList<AbstractSymbol> list = path(clazz);
		AbstractSymbol type = null;
		

		// search CLAZZ's list of attributes, if specified
		if(!self)
			list.removeLast();
		else
			type = nodeMap.get(list.removeLast()).attributes.get(name);
			
		// search the closest binding from CLASS's ancestors' lists of attributes, if specified
		if(!ancestor)
			return type;
		else
			while(!list.isEmpty() && type == null)
				type = nodeMap.get(list.removeLast()).attributes.get(name);				
		
		return type;		
	}*/
	
	/*
	public List<Map.Entry<AbstractSymbol, AbstractSymbol>> getMethodParams(AbstractSymbol clazz, AbstractSymbol name, boolean self, boolean ancestor) {
	
		// if CLAZZ is an undefined class, return null
		if(!nodeMap.containsKey(clazz))
			return null;
		
		LinkedList<AbstractSymbol> list = path(clazz);
		List<Map.Entry<AbstractSymbol, AbstractSymbol>> params = null;
		
		if(!self)
			list.removeLast();
		else
			params = nodeMap.get(list.removeLast()).methods.get(name);
			
		if(!ancestor)
			return params;
		else
			while(!list.isEmpty() && params == null)
				params = nodeMap.get(list.removeLast()).methods.get(name);
		
		return params;
		
	}*/
	
	public void setCurrentClass(class_c c) {
		curClass = c;
	}
	
	public class_c getCurrentClass() {
		return curClass;
	}
	
	/** Prints line number and file name of the given class.
	 *
	 * Also increments semantic error count.
	 *
	 * @param c the class
	 * @return a print stream to which the rest of the error message is
	 * to be printed.
	 *
	 * */
	public PrintStream semantError(class_c c) {
		return semantError(c.getFilename(), c);
	}

	/** Prints the file name and the line number of the given tree node.
	 *
	 * Also increments semantic error count.
	 *
	 * @param filename the file name
	 * @param t the tree node
	 * @return a print stream to which the rest of the error message is
	 * to be printed.
	 *
	 * */
	public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
		errorStream.print(filename + ":" + t.getLineNumber() + ": ");
		return semantError();
	}

	/** Increments semantic error count and returns the print stream for
	 * error messages.
	 *
	 * @return a print stream to which the error message is
	 * to be printed.
	 *
	 * */
	public PrintStream semantError() {
		semantErrors++;
		return errorStream;
	}

	/** Returns true if there are any static semantic errors. */
	public boolean errors() {
		return semantErrors != 0;
	}
	
	private static class Node {
		public class_c value;
		public Node parent;
		public List<Node> children;
		public Map<AbstractSymbol, AbstractSymbol> attrMap;
		public Map<AbstractSymbol, Map<AbstractSymbol, AbstractSymbol>> methMap;
		public SymbolTable objectEnv;
		public SymbolTable methodEnv;

		public Node(class_c value, Node parent) {
			this.value = value;
			this.parent = parent;
			children = new ArrayList<Node>();
			if(parent != null)
				parent.children.add(this);
				
			attrMap = new LinkedHashMap<AbstractSymbol, AbstractSymbol>();
			methMap = new LinkedHashMap<AbstractSymbol, Map<AbstractSymbol, AbstractSymbol>>();				
			objectEnv = new SymbolTable();
			methodEnv = new SymbolTable();

				
			// retrieve attribute and method info
			/*methods = new ArrayList<method>();
			attributes = new ArrayList<attr>();
			Features featList = value.getFeatures();
			for(int i = 0; i < featList.getLength(); i++) {
				Feature feat = (Feature)featList.getNth(i);
				
				if(feat instanceof attr)
					attr.add((attr)feat);
				else
					attr.add((method)feat);
				
				/*
					attr at = (attr)feat;
					attributes.put(at.getName(), at.getType());
					
				} else if(feat instanceof method) {
					method meth = (method)feat;
					Formals formList = meth.getFormals();
					List<Map.Entry<AbstractSymbol, AbstractSymbol>> params = new ArrayList<Map.Entry<AbstractSymbol, AbstractSymbol>>();
					
					for(int j = 0; j < formList.getLength(); j++) {
						formalc form = (formalc)formList.getNth(j);
						params.add(new AbstractMap.SimpleEntry<AbstractSymbol, AbstractSymbol>(form.getName(), form.getType()));
					}	
					params.add(new AbstractMap.SimpleEntry<AbstractSymbol, AbstractSymbol>(
						TreeConstants.ret, meth.getRet()));
						
					methods.put(meth.getName(), params);
					
				} else {
					throw new RuntimeException("Feature is neither an attribute or a method.");
				}
			}*/
		}
		
		public String toString() {
			return "<" + value.getName() + " | " + value.getParent() + ">";
		}
	}
	
	/*
	public class AttrInfo {
		public AbstractSymbol attr;
	}
	
	public class MethInfo {
		public AbstractSymbol type;
		public Map<AbstractSymbol, AbstractSymbol> params;
		
		public MethInfo(AbstractSymbol type) {
			this.type = type;
			params = new HashMap<AbstractSymbol, AbstractSymbol>();
		}
	}*/
	
	
}
						  
	
