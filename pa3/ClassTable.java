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
		nodeMap = new HashMap<AbstractSymbol, Node>();
		nodeMap.put(Object_class.getName(), treeRoot);
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
		else if(!nodeMap.get(TreeConstants.Main).methods.containsKey(TreeConstants.main_meth))
			semantError(nodeMap.get(TreeConstants.Main).value).println("No 'main' method in class Main.");
		
		if(Flags.semant_debug) {
			System.out.println("\nFinal class tree: \n" + this);
			System.out.println("\nFinal node map:\n" + nodeMap);
			/*
			System.out.println(getAttribute(lookup("D"), lookup("b")));
			System.out.println(getAttribute(lookup("D"), lookup("name")));
			System.out.println(getAttribute(lookup("D"), lookup("X")));
			System.out.println(getFunctionParams(lookup("D"), lookup("main")));
			System.out.println(getFunctionParams(lookup("X"), lookup("Def")));			
			
			
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
		
		// 5. Construct method/attr symbol tables for every class
		
		
			
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
		
		sb.append("" + node.value.getName() + node.methods + node.attributes + "\n");
		
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
	
	
	public AbstractSymbol lub(AbstractSymbol t1, AbstractSymbol t2) {
		if(!nodeMap.containsKey(t1) || !nodeMap.containsKey(t2))
			return null;
		
		if(t1 == TreeConstants.SELF_TYPE && t2 == TreeConstants.SELF_TYPE)
			return t1;

		else if(t1 == TreeConstants.SELF_TYPE)
			return lub(curClass.getName(), t2);
		
		else if(t2 == TreeConstants.SELF_TYPE)
			return lub(t1, curClass.getName());
		
		else {
			LinkedList<AbstractSymbol> p1 = path(t1);
			LinkedList<AbstractSymbol> p2 = path(t2);		
			AbstractSymbol common = p1.removeFirst();
			while(!p1.isEmpty() && p2.contains(p1.getFirst()))
				common = p1.removeFirst();	

			return common;		
		}	
	}
	
	public boolean le(AbstractSymbol t1, AbstractSymbol t2) {
		if(!nodeMap.containsKey(t1) || !nodeMap.containsKey(t2))
			return false;
		
		if(t1 == TreeConstants.SELF_TYPE && t2 == TreeConstants.SELF_TYPE)
			return true;
			
		else if(t1 == TreeConstants.SELF_TYPE)
			return le(curClass.getName(), t2);
			
		else if(t2 == TreeConstants.SELF_TYPE)
			return false;
			
		else			
			return path(t1).contains(t2);
	}
	
	public AbstractSymbol getAttribute(AbstractSymbol c, AbstractSymbol v) {
		if(!nodeMap.containsKey(c))
			return null;
			
		LinkedList<AbstractSymbol> list = path(c);
		AbstractSymbol type = null;
		do {
			//System.out.println(list);
			type = nodeMap.get(list.removeLast()).attributes.get(v);				
		} while(!list.isEmpty() && type == null);
		
		return type;		
	}
	
	public List<AbstractSymbol> getFunctionParams(AbstractSymbol c, AbstractSymbol f) {
		if(!nodeMap.containsKey(c))
			return null;
		
		LinkedList<AbstractSymbol> list = path(c);
		List<AbstractSymbol> types = null;

		do {
			types = nodeMap.get(list.removeLast()).methods.get(f);
		} while(!list.isEmpty() && types == null);
		
		return types;
		
	}
	
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
		public SymbolTable attributeTable;
		public SymbolTable methodTable;
		
		public Map<AbstractSymbol, AbstractSymbol> attributes;
		public Map<AbstractSymbol, List<AbstractSymbol>> methods;
		public Node(class_c value, Node parent) {
			this.value = value;
			this.parent = parent;
			children = new ArrayList<Node>();
			if(parent != null)
				parent.children.add(this);
				
			// retrieve attribute and method info
			attributes = new HashMap<AbstractSymbol, AbstractSymbol>();
			methods = new HashMap<AbstractSymbol, List<AbstractSymbol>>();
			Features featList = value.getFeatures();
			for(int i = 0; i < featList.getLength(); i++) {
				Feature feat = (Feature)featList.getNth(i);
				
				if(feat instanceof attr) {
					attr at = (attr)feat;
					attributes.put(at.getName(), at.getType());
					
				} else if(feat instanceof method) {
					method meth = (method)feat;
					Formals formList = meth.getFormals();
					List<AbstractSymbol> types = new ArrayList<AbstractSymbol>();
					for(int j = 0; j < formList.getLength(); j++)
						types.add(((formalc)formList.getNth(j)).getType());
					types.add(meth.getRet());
					methods.put(meth.getName(), types);
				} else {
					
				}
			}
		}
		
		public String toString() {
			return "<" + value.getName() + " | " + value.getParent() + ">";
		}
	}
}
						  
	
