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
		treeRoot = new Node(Object_class);
		nodeMap = new HashMap<AbstractSymbol, Node>();
		nodeMap.put(Int_class.getName(), add(treeRoot, Int_class));
		nodeMap.put(Bool_class.getName(), add(treeRoot, Bool_class));
		nodeMap.put(Str_class.getName(), add(treeRoot, Str_class));
		nodeMap.put(IO_class.getName(), add(treeRoot, IO_class));
	}
		


	public ClassTable(Classes cls) {
		semantErrors = 0;
		errorStream = System.err;
		
		/* fill this in */
		
		// initalizatoin
		installBasicClasses();
		if(Flags.semant_debug) {
			System.out.println(this);
			System.out.println(nodeMap);
		}
		
		// add classes into tree
		for(int i = 0; i < cls.getLength(); i++) {
			class_c c = (class_c)cls.getNth(i);
			if(nodeMap.containsKey(c.getName()))
				semantError(c).println("Class " + c.getName() + " was previously defined.");
			else
				nodeMap.put(c.getName(), add(treeRoot, c));

		}
		
		if(Flags.semant_debug) {
			System.out.println(this);
			System.out.println(nodeMap);
		}
			
	}
	
	private Node add(Node n, class_c c) {
	
		// if the parent of c is this in n, add c as n's child
		if(n.value.getName() == c.getParent()) {
			Node child = new Node(c);
			n.children.add(child);
			return child;
			
		} else {
		
			// else, if we can successfully add c to any of n's child
			for(Node child : n.children) {
				Node newNode = add(child, c);
				if(newNode != null)
					return newNode;
			}
		
			// else, add fail and return null
			return null;
		}
	}
	

	
	public String toString() {
		return nodeToString(treeRoot, 0, new StringBuffer()).toString();
	}
	
	private StringBuffer nodeToString(Node node, int lv, StringBuffer sb) {
		for(int i = 0; i < lv; i++)
			sb.append("\t");
		sb.append("|------ ");
		
		sb.append(node.value.getName() + "\n");
		
		for(Node n : node.children)
			nodeToString(n, lv + 1, sb);
		
		return sb;
	}
	
	private LinkedList<AbstractSymbol> path(Node node, AbstractSymbol target, LinkedList<AbstractSymbol> list) {
		list.addLast(node.value.getName());
		if(node.value.getName() == target) {
			return list;
			
		} else {
			for(Node child : node.children) {
				if(path(child, target, list) != null)
					return list;				
			} 
			
			list.removeLast();
			return null;			
		}
	}
	
	
	public AbstractSymbol lub(AbstractSymbol t1, AbstractSymbol t2) {
		if(!nodeMap.containsKey(t1) || !nodeMap.containsKey(t2))
			return null;
			
		LinkedList<AbstractSymbol> p1 = path(treeRoot, t1, new LinkedList<AbstractSymbol>());
		LinkedList<AbstractSymbol> p2 = path(treeRoot, t2, new LinkedList<AbstractSymbol>());		
		
		AbstractSymbol common = p1.removeFirst();
		while(!p1.isEmpty() && p2.contains(p1.getFirst()))
			common = p1.removeFirst();
			
			
		return common;
	}
	
	public boolean le(AbstractSymbol t1, AbstractSymbol t2) {
		// TODO
		return true;
	}
	
	public List<AbstractSymbol> getFunctionParams(AbstractSymbol c, AbstractSymbol f) {
		// TODO
		return null;
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
		public List<Node> children;
		public Node(class_c value) {
			this.value = value;
			children = new ArrayList<Node>();
		}
		public String toString() {
			return "<" + value.getName() + " | " + value.getParent() + ">";
		}
	}
}
						  
	
