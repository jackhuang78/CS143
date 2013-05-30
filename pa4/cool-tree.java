// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////

import java.util.*;
import java.io.PrintStream;

/** class for variables **/
abstract class Variable {
    public abstract void emitLoad(PrintStream s);
    public abstract void emitStore(PrintStream s);
}

/** class for formal variables **/
class FieldVar extends Variable{
	private int offset;
    public FieldVar(int offset) {
        this.offset = offset;
    }
    public void emitLoad(PrintStream s) {
        CgenSupport.emitLoad(CgenSupport.ACC, -offset, CgenSupport.FP, s);
    }
    public void emitStore(PrintStream s) {
        CgenSupport.emitStore(CgenSupport.ACC, -offset, CgenSupport.FP, s);
    }
}

/** class for class variables **/
class ClassVar extends Variable{
	private int offset;
    public ClassVar(int offset) {
        this.offset = CgenSupport.DEFAULT_OBJFIELDS+offset;
    }
    public void emitLoad(PrintStream s) {
        CgenSupport.emitLoad(CgenSupport.ACC, offset, CgenSupport.SELF, s);
    }
    public void emitStore(PrintStream s) {
        CgenSupport.emitStore(CgenSupport.ACC, offset, CgenSupport.SELF, s);
    }
}

/** Defines simple phylum Program */
abstract class Program extends TreeNode {
	protected Program(int lineNumber) {
		super(lineNumber);
	}
	public abstract void dump_with_types(PrintStream out, int n);
	public abstract void semant();
	public abstract void cgen(PrintStream s);

}

/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
	protected Class_(int lineNumber) {
		super(lineNumber);
	}
	public abstract void dump_with_types(PrintStream out, int n);
	public abstract AbstractSymbol getName();
	public abstract AbstractSymbol getParent();
	public abstract AbstractSymbol getFilename();
	public abstract Features getFeatures();

}


/** Defines list phylum Classes
	<p>
	See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
	public final static Class elementClass = Class_.class;
	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}
	protected Classes(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}
	/** Creates an empty "Classes" list */
	public Classes(int lineNumber) {
		super(lineNumber);
	}
	/** Appends "Class_" element to this list */
	public Classes appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}
	public TreeNode copy() {
		return new Classes(lineNumber, copyElements());
	}
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
	protected Feature(int lineNumber) {
		super(lineNumber);
	}
	public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Features
	<p>
	See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
	public final static Class elementClass = Feature.class;
	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}
	protected Features(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}
	/** Creates an empty "Features" list */
	public Features(int lineNumber) {
		super(lineNumber);
	}
	/** Appends "Feature" element to this list */
	public Features appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}
	public TreeNode copy() {
		return new Features(lineNumber, copyElements());
	}
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
	protected Formal(int lineNumber) {
		super(lineNumber);
	}
	public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Formals
	<p>
	See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
	public final static Class elementClass = Formal.class;
	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}
	protected Formals(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}
	/** Creates an empty "Formals" list */
	public Formals(int lineNumber) {
		super(lineNumber);
	}
	/** Appends "Formal" element to this list */
	public Formals appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}
	public TreeNode copy() {
		return new Formals(lineNumber, copyElements());
	}
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
	protected Expression(int lineNumber) {
		super(lineNumber);
	}
	private AbstractSymbol type = null;								 
	public AbstractSymbol get_type() { return type; }		   
	public Expression set_type(AbstractSymbol s) { type = s; return this; } 
	public abstract void dump_with_types(PrintStream out, int n);
	public void dump_type(PrintStream out, int n) {
		if (type != null)
			{ out.println(Utilities.pad(n) + ": " + type.getString()); }
		else
			{ out.println(Utilities.pad(n) + ": _no_type"); }
	}
	public abstract void code(PrintStream s);

}


/** Defines list phylum Expressions
	<p>
	See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
	public final static Class elementClass = Expression.class;
	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}
	protected Expressions(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}
	/** Creates an empty "Expressions" list */
	public Expressions(int lineNumber) {
		super(lineNumber);
	}
	/** Appends "Expression" element to this list */
	public Expressions appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}
	public TreeNode copy() {
		return new Expressions(lineNumber, copyElements());
	}
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
	protected Case(int lineNumber) {
		super(lineNumber);
	}
	public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
	<p>
	See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
	public final static Class elementClass = Case.class;
	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}
	protected Cases(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}
	/** Creates an empty "Cases" list */
	public Cases(int lineNumber) {
		super(lineNumber);
	}
	/** Appends "Case" element to this list */
	public Cases appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}
	public TreeNode copy() {
		return new Cases(lineNumber, copyElements());
	}
}


/** Defines AST constructor 'program'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class program extends Program {
	public Classes classes;
	/** Creates "program" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for classes
	  */
	public program(int lineNumber, Classes a1) {
		super(lineNumber);
		classes = a1;
	}
	public TreeNode copy() {
		return new program(lineNumber, (Classes)classes.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "program\n");
		classes.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_program");
		for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
			((Class_)e.nextElement()).dump_with_types(out, n + 1);
		}
	}
	/** This method is the entry point to the semantic checker.  You will
		need to complete it in programming assignment 4.
		<p>
		Your checker should do the following two things:
		<ol>
		<li>Check that the program is semantically correct
		<li>Decorate the abstract syntax tree with type information
		by setting the type field in each Expression node.
		(see tree.h)
		</ol>
		<p>
		You are free to first do (1) and make sure you catch all semantic
			errors. Part (2) can be done in a second stage when you want
		to test the complete compiler.
	*/
	public void semant() {
		/* ClassTable constructor may do some semantic analysis */
		ClassTable classTable = new ClassTable(classes);
		
		/* some semantic analysis code may go here */
		//System.out.println("Semant!");

		if (classTable.errors()) {
			System.err.println("Compilation halted due to static semantic errors.");
			System.exit(1);
		}
	}
	/** This method is the entry point to the code generator.  All of the work
	  * of the code generator takes place within CgenClassTable constructor.
	  * @param s the output stream 
	  * @see CgenClassTable
	  * */
	public void cgen(PrintStream s) 
	{
		// spim wants comments to start with '#'
		s.print("# start of generated code\n");

		CgenClassTable codegen_classtable = new CgenClassTable(classes, s);

		s.print("\n# end of generated code\n");
	}

}


/** Defines AST constructor 'class_'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_ extends Class_ {
	public AbstractSymbol name;
	public AbstractSymbol parent;
	public Features features;
	public AbstractSymbol filename;
	/** Creates "class_" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for name
	  * @param a1 initial value for parent
	  * @param a2 initial value for features
	  * @param a3 initial value for filename
	  */
	public class_(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
		super(lineNumber);
		name = a1;
		parent = a2;
		features = a3;
		filename = a4;
	}
	public TreeNode copy() {
		return new class_(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "class_\n");
		dump_AbstractSymbol(out, n+2, name);
		dump_AbstractSymbol(out, n+2, parent);
		features.dump(out, n+2);
		dump_AbstractSymbol(out, n+2, filename);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_class");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, parent);
		out.print(Utilities.pad(n + 2) + "\"");
		Utilities.printEscapedString(out, filename.getString());
		out.println("\"\n" + Utilities.pad(n + 2) + "(");
		for (Enumeration e = features.getElements(); e.hasMoreElements();) {
			((Feature)e.nextElement()).dump_with_types(out, n + 2);
		}
		out.println(Utilities.pad(n + 2) + ")");
	}
	public AbstractSymbol getName()	 { return name; }
	public AbstractSymbol getParent()   { return parent; }
	public AbstractSymbol getFilename() { return filename; }
	public Features getFeatures()	   { return features; }
}


/** Defines AST constructor 'method'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
	public AbstractSymbol name;
	public Formals formals;
	public AbstractSymbol return_type;
	public Expression expr;
	/** Creates "method" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for name
	  * @param a1 initial value for formals
	  * @param a2 initial value for return_type
	  * @param a3 initial value for expr
	  */
	public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
		super(lineNumber);
		name = a1;
		formals = a2;
		return_type = a3;
		expr = a4;
	}
	public TreeNode copy() {
		return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "method\n");
		dump_AbstractSymbol(out, n+2, name);
		formals.dump(out, n+2);
		dump_AbstractSymbol(out, n+2, return_type);
		expr.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_method");
		dump_AbstractSymbol(out, n + 2, name);
		for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
			((Formal)e.nextElement()).dump_with_types(out, n + 2);
		}
		dump_AbstractSymbol(out, n + 2, return_type);
		expr.dump_with_types(out, n + 2);
	}

	
    public void code(PrintStream s) {
        CgenClassTable.ct.enterScope();
        for (int i = 0; i < formals.getLength(); ++i) {
            int offset = 2 + formals.getLength() - i;
            CgenClassTable.ct.addId(((formal)formals.getNth(i)).getName(), new FieldVar(-offset));
        }
        CgenSupport.emitStartMethod(s);
        CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, s);
        CgenClassTable.ct.fpOffset = 1;
        expr.code(s);
        CgenSupport.emitEndMethod(formals.getLength(), s);
        CgenClassTable.ct.exitScope();
    }
}


/** Defines AST constructor 'attr'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
	public AbstractSymbol name;
	public AbstractSymbol type_decl;
	public Expression init;
	/** Creates "attr" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for name
	  * @param a1 initial value for type_decl
	  * @param a2 initial value for init
	  */
	public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
		super(lineNumber);
		name = a1;
		type_decl = a2;
		init = a3;
	}
	public TreeNode copy() {
		return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "attr\n");
		dump_AbstractSymbol(out, n+2, name);
		dump_AbstractSymbol(out, n+2, type_decl);
		init.dump(out, n+2);
	}
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_attr");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, type_decl);
		init.dump_with_types(out, n + 2);
	}
}


/** Defines AST constructor 'formal'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formal extends Formal {
	public AbstractSymbol name;
	public AbstractSymbol type_decl;
	/** Creates "formal" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for name
	  * @param a1 initial value for type_decl
	  */
	public AbstractSymbol getType() {
        return type_decl;
    }

    public AbstractSymbol getName() {
        return name;
    }

	public formal(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
		super(lineNumber);
		name = a1;
		type_decl = a2;
	}
	public TreeNode copy() {
		return new formal(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "formal\n");
		dump_AbstractSymbol(out, n+2, name);
		dump_AbstractSymbol(out, n+2, type_decl);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_formal");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, type_decl);
	}

}


/** Defines AST constructor 'branch'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
	public AbstractSymbol name;
	public AbstractSymbol type_decl;
	public Expression expr;
	/** Creates "branch" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for name
	  * @param a1 initial value for type_decl
	  * @param a2 initial value for expr
	  */
	public AbstractSymbol getType() {
		return type_decl;
	}
	public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
		super(lineNumber);
		name = a1;
		type_decl = a2;
		expr = a3;
	}
	public TreeNode copy() {
		return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "branch\n");
		dump_AbstractSymbol(out, n+2, name);
		dump_AbstractSymbol(out, n+2, type_decl);
		expr.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_branch");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, type_decl);
		expr.dump_with_types(out, n + 2);
	}

    public void code(int labelContinue, PrintStream s) {
	  s.println("# start of branch for " + name + ":" + type_decl);
	  // store typcase expression's tag into T1 (object reference)
      CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.ACC, s);
      // create label for next branch
      int labelNextBranch = CgenSupport.genNextLabel();
      CgenSupport.emitBlti(CgenSupport.T1, CgenClassTable.ct.getMinTag(type_decl), labelNextBranch, s);
      CgenSupport.emitBgti(CgenSupport.T1, CgenClassTable.ct.getMaxTag(type_decl), labelNextBranch, s); 
      // push typcase expression object reference on the stack
      CgenSupport.emitPush(CgenSupport.ACC, s);
      CgenClassTable.ct.enterScope();

      // add id to appropriate offset
      CgenClassTable.ct.addId(name, new FieldVar(CgenClassTable.ct.fpOffset));
      CgenClassTable.ct.fpOffset = CgenClassTable.ct.fpOffset + 1;

      expr.code(s);
      CgenClassTable.ct.fpOffset = CgenClassTable.ct.fpOffset - 1;
      CgenClassTable.ct.exitScope();
      // pop typcase expression object
      CgenSupport.emitPop(CgenSupport.T1,s);
      // jump out of branch
      CgenSupport.emitBranch(labelContinue, s);
      // print next branch label
      CgenSupport.emitLabelDef(labelNextBranch, s);
      s.println("# end of branch for " + name + ":" + type_decl);
    }
}


/** Defines AST constructor 'assign'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
	public AbstractSymbol name;
	public Expression expr;
	/** Creates "assign" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for name
	  * @param a1 initial value for expr
	  */
	public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
		super(lineNumber);
		name = a1;
		expr = a2;
	}
	public TreeNode copy() {
		return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "assign\n");
		dump_AbstractSymbol(out, n+2, name);
		expr.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_assign");
		dump_AbstractSymbol(out, n + 2, name);
		expr.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	
	public void code(PrintStream s) {
		s.println("# start of assign to " + name);
		// code expr
        expr.code(s);
        // emit assign 
        ((Variable)CgenClassTable.ct.lookup(name)).emitStore(s);
        s.println("# end of assign to " + name);
	}


}


/** Defines AST constructor 'static_dispatch'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
	public Expression expr;
	public AbstractSymbol type_name;
	public AbstractSymbol name;
	public Expressions actual;
	/** Creates "static_dispatch" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for expr
	  * @param a1 initial value for type_name
	  * @param a2 initial value for name
	  * @param a3 initial value for actual
	  */
	public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
		super(lineNumber);
		expr = a1;
		type_name = a2;
		name = a3;
		actual = a4;
	}
	public TreeNode copy() {
		return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "static_dispatch\n");
		expr.dump(out, n+2);
		dump_AbstractSymbol(out, n+2, type_name);
		dump_AbstractSymbol(out, n+2, name);
		actual.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_static_dispatch");
		expr.dump_with_types(out, n + 2);
		dump_AbstractSymbol(out, n + 2, type_name);
		dump_AbstractSymbol(out, n + 2, name);
		out.println(Utilities.pad(n + 2) + "(");
		for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
			((Expression)e.nextElement()).dump_with_types(out, n + 2);
		}
		out.println(Utilities.pad(n + 2) + ")");
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	
	public void code(PrintStream s) {
		s.println("# static_dispatch " + type_name + "." + name + "()");
		// code actuals
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression ne = (Expression)e.nextElement();
            // code the actual params as temporary and store result in $a0
            ne.code(s);
            // store the temporary on stack
            CgenSupport.emitPush(CgenSupport.ACC, s);
        }
        expr.code(s);
        CgenSupport.emitDispAbort(lineNumber, CgenClassTable.ct.getFilename(type_name), s);
        // load dispatch table's address into T1
        CgenSupport.emitLoadAddress(CgenSupport.T1, type_name + CgenSupport.DISPTAB_SUFFIX, s);
        // find the offset of the method in the dispatch table, load method address into t1
        CgenSupport.emitLoad(CgenSupport.T1, CgenClassTable.ct.getMethodOffset(type_name, name), CgenSupport.T1, s);
        // let's jump!
        CgenSupport.emitJalr(CgenSupport.T1, s);
        s.println("# end of static_dispatch " + type_name + "." + name + "()");
	}
}


/** Defines AST constructor 'dispatch'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
	public Expression expr;
	public AbstractSymbol name;
	public Expressions actual;
	/** Creates "dispatch" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for expr
	  * @param a1 initial value for name
	  * @param a2 initial value for actual
	  */
	public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
		super(lineNumber);
		expr = a1;
		name = a2;
		actual = a3;
	}
	public TreeNode copy() {
		return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "dispatch\n");
		expr.dump(out, n+2);
		dump_AbstractSymbol(out, n+2, name);
		actual.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_dispatch");
		expr.dump_with_types(out, n + 2);
		dump_AbstractSymbol(out, n + 2, name);
		out.println(Utilities.pad(n + 2) + "(");
		for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
			((Expression)e.nextElement()).dump_with_types(out, n + 2);
		}
		out.println(Utilities.pad(n + 2) + ")");
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		s.println("# dispatch " + name + "()");
        // code actuals
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression ne = (Expression)e.nextElement();
            // code the actual params as temporary and store result in $a0
            ne.code(s);
            // push temporary onto stack
            CgenSupport.emitPush(CgenSupport.ACC, s);
        }
        expr.code(s);
        
        // get the expression type, and convert to current class if it is SELF_TYPE
        AbstractSymbol exprType = expr.get_type();
        if(exprType == TreeConstants.SELF_TYPE)
        	exprType = (AbstractSymbol)CgenClassTable.ct.lookup(exprType);
        	
        CgenSupport.emitDispAbort(lineNumber, CgenClassTable.ct.getFilename(exprType),s);
        // load dispatch table's address into T1
        CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DISPTABLE_OFFSET, CgenSupport.ACC, s);
        // find the offset of the method in the dispatch table, load method address into t1
        CgenSupport.emitLoad(CgenSupport.T1, CgenClassTable.ct.getMethodOffset(exprType, name), CgenSupport.T1, s);
        // let's jump!
        CgenSupport.emitJalr(CgenSupport.T1, s);
        s.println("# end of dispatch " + name + "()");
	}


}


/** Defines AST constructor 'cond'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
	public Expression pred;
	public Expression then_exp;
	public Expression else_exp;
	/** Creates "cond" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for pred
	  * @param a1 initial value for then_exp
	  * @param a2 initial value for else_exp
	  */
	public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
		super(lineNumber);
		pred = a1;
		then_exp = a2;
		else_exp = a3;
	}
	public TreeNode copy() {
		return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "cond\n");
		pred.dump(out, n+2);
		then_exp.dump(out, n+2);
		else_exp.dump(out, n+2);
	}
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_cond");
		pred.dump_with_types(out, n + 2);
		then_exp.dump_with_types(out, n + 2);
		else_exp.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		s.println("# start cond");
		// first code predicate
        pred.code(s);
        // store result into T1
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.ACC, s);
        // generate new label
        int falseLable = CgenSupport.genNextLabel();
        // branch to false label if needed
        CgenSupport.emitBeqz(CgenSupport.T1, falseLable, s);
        // coding true branch
        then_exp.code(s);
        // generate new label
        int labelContinue = CgenSupport.genNextLabel();
        // jumpover to continue label to avoid false branch
        CgenSupport.emitBranch(labelContinue, s);
        // coding false label
        CgenSupport.emitLabelDef(falseLable, s);
        // coding false branch
        else_exp.code(s);
        // coding continue label
        CgenSupport.emitLabelDef(labelContinue, s);
        s.println("# end cond");
	}
}


/** Defines AST constructor 'loop'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
	public Expression pred;
	public Expression body;
	/** Creates "loop" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for pred
	  * @param a1 initial value for body
	  */
	public loop(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		pred = a1;
		body = a2;
	}
	public TreeNode copy() {
		return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "loop\n");
		pred.dump(out, n+2);
		body.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_loop");
		pred.dump_with_types(out, n + 2);
		body.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
        s.println("# start loop");
        // generate label for loop
        int labelLoop = CgenSupport.genNextLabel();
        // emit label definition for loop
        CgenSupport.emitLabelDef(labelLoop, s);
        // code predicate for loop
        pred.code(s);
        // fetch predicate result as int in T1
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.ACC, s);
        // create label for continue
        int labelContinue = CgenSupport.genNextLabel();
        // emit beqz to test if we want to jump out of loop
        CgenSupport.emitBeqz(CgenSupport.T1, labelContinue, s);
        // code body of loop
        body.code(s);
        // return to the head of loop
        CgenSupport.emitBranch(labelLoop, s);
        // emit label for continue
        CgenSupport.emitLabelDef(labelContinue, s);
        // clear ACC
        CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.ZERO, s);
        s.println("# end loop");
	}


}


/** Defines AST constructor 'typcase'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
	public Expression expr;
	public Cases cases;
	/** Creates "typcase" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for expr
	  * @param a1 initial value for cases
	  */
	public typcase(int lineNumber, Expression a1, Cases a2) {
		super(lineNumber);
		expr = a1;
		cases = a2;
	}
	public TreeNode copy() {
		return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "typcase\n");
		expr.dump(out, n+2);
		cases.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_typcase");
		expr.dump_with_types(out, n + 2);
		for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
			((Case)e.nextElement()).dump_with_types(out, n + 2);
		}
		dump_type(out, n);
	}

	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		s.println("# start case");
		// create a list of branches
        List<branch> branches = new ArrayList<branch>();
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            branches.add((branch)e.nextElement());
        }
        // sort branches so that we can generate effective branching mechanism
        Collections.sort(branches, new Comparator<branch>(){
        	// using sort implementation
            public int compare(branch first, branch second) {
            	// Return a positive value if object1 is larger than object2. put the more specific type first
                return CgenClassTable.ct.getDepth(second.getType()) - CgenClassTable.ct.getDepth(first.getType());
            }
        });
		// evaluate expr
        expr.code(s); // case void of will cause case to abort CASE_ABORT2
        // do not abort if case type is not void (0 in accu)
        int labelNotVoid = CgenSupport.genNextLabel();
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, labelNotVoid, s);
        // jump to case abort
        // store file name into ACC as also done in the reference
        StringSymbol filename = (StringSymbol)AbstractTable.stringtable.lookup(CgenClassTable.ct.getFilename(null));
        CgenSupport.emitLoadAddress(CgenSupport.ACC, TRCONST_PREFIX + sym.getIndex() , s);
        // load line number into t1
        CgenSupport.emitLoadImm(CgenSupport.T1, lineNumber, s);
        CgenSupport.emitJal(CgenSupport.CASE_ABORT2, s);
        // print label with no jump
        CgenSupport.emitLabelDef(labelNotVoid, s);
        // generate continue label
        int labelContinue = CgenSupport.genNextLabel();
        // code all branches, passing in the label for continue execution
        for(branch b: branches) {
            b.code(labelContinue, s);
        }
        // jump to case abort because there is no match, missing branch
        CgenSupport.emitJal(CgenSupport.CASE_ABORT, s);
        // generate label to continue
        CgenSupport.emitLabelDef(labelContinue, s);
        s.println("# end case");
	}
}


/** Defines AST constructor 'block'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
	public Expressions body;
	/** Creates "block" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for body
	  */
	public block(int lineNumber, Expressions a1) {
		super(lineNumber);
		body = a1;
	}
	public TreeNode copy() {
		return new block(lineNumber, (Expressions)body.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "block\n");
		body.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_block");
		for (Enumeration e = body.getElements(); e.hasMoreElements();) {
			((Expression)e.nextElement()).dump_with_types(out, n + 2);
		}
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		for (Enumeration e = body.getElements(); e.hasMoreElements();) {
			// code sequence
            ((Expression)e.nextElement()).code(s);
        }
	}
}


/** Defines AST constructor 'let'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
	public AbstractSymbol identifier;
	public AbstractSymbol type_decl;
	public Expression init;
	public Expression body;
	/** Creates "let" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for identifier
	  * @param a1 initial value for type_decl
	  * @param a2 initial value for init
	  * @param a3 initial value for body
	  */
	public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
		super(lineNumber);
		identifier = a1;
		type_decl = a2;
		init = a3;
		body = a4;
	}
	public TreeNode copy() {
		return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "let\n");
		dump_AbstractSymbol(out, n+2, identifier);
		dump_AbstractSymbol(out, n+2, type_decl);
		init.dump(out, n+2);
		body.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_let");
		dump_AbstractSymbol(out, n + 2, identifier);
		dump_AbstractSymbol(out, n + 2, type_decl);
		init.dump_with_types(out, n + 2);
		body.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		s.println("# start of let for " + identifier);
        if (init.get_type() == null) {
        	// load default value into ACC if init's type is null
            if (type_decl == TreeConstants.Bool) {
            	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);
            } else if (type_decl == TreeConstants.Int) {
            	CgenSupport.emitLoadInt(CgenSupport.ACC, (IntSymbol)AbstractTable.inttable.lookup(0), s);
            } else if (type_decl == TreeConstants.Str) {
            	CgenSupport.emitLoadString(CgenSupport.ACC, (StringSymbol)AbstractTable.stringtable.lookup(""), s);
            } else {
                CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.ZERO, s);
            }
        } else {
            init.code(s);
        }
        // save ACC value as temporary variable on stack
        CgenSupport.emitPush(CgenSupport.ACC, s);
        CgenClassTable.ct.enterScope();
        CgenClassTable.ct.addId(identifier, new FieldVar(CgenClassTable.ct.fpOffset));
        CgenClassTable.ct.fpOffset = CgenClassTable.ct.fpOffset + 1;
        body.code(s);
        CgenClassTable.ct.fpOffset = CgenClassTable.ct.fpOffset - 1;
        CgenClassTable.ct.exitScope();
        // pop previous ACC value out of stack
        CgenSupport.emitPop(CgenSupport.T1,s);
        s.println("# end of let for " + identifier);
	}
}


/** Defines AST constructor 'plus'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
	public Expression e1;
	public Expression e2;
	/** Creates "plus" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  * @param a1 initial value for e2
	  */
	public plus(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	public TreeNode copy() {
		return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "plus\n");
		e1.dump(out, n+2);
		e2.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_plus");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		CgenSupport.emitArith(e1, e2, CgenSupport.ADD, s);
	}


}


/** Defines AST constructor 'sub'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
	public Expression e1;
	public Expression e2;
	/** Creates "sub" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  * @param a1 initial value for e2
	  */
	public sub(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	public TreeNode copy() {
		return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "sub\n");
		e1.dump(out, n+2);
		e2.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_sub");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
        CgenSupport.emitArith(e1, e2, CgenSupport.SUB, s);
	}


}


/** Defines AST constructor 'mul'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
	public Expression e1;
	public Expression e2;
	/** Creates "mul" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  * @param a1 initial value for e2
	  */
	public mul(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	public TreeNode copy() {
		return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "mul\n");
		e1.dump(out, n+2);
		e2.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_mul");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		CgenSupport.emitArith(e1, e2, CgenSupport.MUL, s);
	}


}


/** Defines AST constructor 'divide'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
	public Expression e1;
	public Expression e2;
	/** Creates "divide" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  * @param a1 initial value for e2
	  */
	public divide(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	public TreeNode copy() {
		return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "divide\n");
		e1.dump(out, n+2);
		e2.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_divide");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
        CgenSupport.emitArith(e1, e2, CgenSupport.DIV, s);
	}


}


/** Defines AST constructor 'neg'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
	public Expression e1;
	/** Creates "neg" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  */
	public neg(int lineNumber, Expression a1) {
		super(lineNumber);
		e1 = a1;
	}
	public TreeNode copy() {
		return new neg(lineNumber, (Expression)e1.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "neg\n");
		e1.dump(out, n+2);
	}
	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_neg");
		e1.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
        // code expression
        e1.code(s);
        // copy result into accu
        CgenSupport.emitJal("Object.copy", s);
        // fetch result value into T1
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.ACC, s);
        // emit neg code
        CgenSupport.emitNeg(CgenSupport.T1, CgenSupport.T1, s);
        // store result into accu
        CgenSupport.emitStoreInt(CgenSupport.T1, CgenSupport.ACC, s);
	}
}


/** Defines AST constructor 'lt'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
	public Expression e1;
	public Expression e2;
	/** Creates "lt" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  * @param a1 initial value for e2
	  */
	public lt(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	public TreeNode copy() {
		return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "lt\n");
		e1.dump(out, n+2);
		e2.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_lt");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
        CgenSupport.emitCpr(e1, e2, CgenSupport.BLT, s);
	}
}

/** Defines AST constructor 'eq'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
	public Expression e1;
	public Expression e2;
	/** Creates "eq" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  * @param a1 initial value for e2
	  */
	public eq(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	public TreeNode copy() {
		return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "eq\n");
		e1.dump(out, n+2);
		e2.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_eq");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		s.println("# start of eq");
        e1.code(s);
        CgenSupport.emitPush(CgenSupport.ACC, s);
        e2.code(s);
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, s);
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true),s);
        int labelEnd = CgenSupport.genNextLabel();
        CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.T2, labelEnd, s);
        CgenSupport.emitLoadBool(CgenSupport.A1, new BoolConst(false),s);
        CgenSupport.emitJal("equality_test", s);
        CgenSupport.emitLabelDef(labelEnd, s);
        s.println("# end of eq");
	}


}


/** Defines AST constructor 'leq'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
	public Expression e1;
	public Expression e2;
	/** Creates "leq" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  * @param a1 initial value for e2
	  */
	public leq(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	public TreeNode copy() {
		return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "leq\n");
		e1.dump(out, n+2);
		e2.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_leq");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		CgenSupport.emitCpr(e1, e2, CgenSupport.BLEQ, s);
	}
}


/** Defines AST constructor 'comp'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
	public Expression e1;
	/** Creates "comp" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  */
	public comp(int lineNumber, Expression a1) {
		super(lineNumber);
		e1 = a1;
	}
	public TreeNode copy() {
		return new comp(lineNumber, (Expression)e1.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "comp\n");
		e1.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_comp");
		e1.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s){
		// code e1
        e1.code(s);
        // fetch int value into T1
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.ACC, s);
        // load true into ACC
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true),s);
        // generate new label
        int labelContinue = CgenSupport.genNextLabel();
        // Beqz to continue label if needed, note that ACC has bool true
        CgenSupport.emitBeqz(CgenSupport.T1, labelContinue, s);
        // otherwise load false into ACC, this effectively reverse bool value
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false),s);
        // emit continue label definition
        CgenSupport.emitLabelDef(labelContinue, s);
	}
}


/** Defines AST constructor 'int_const'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
	public AbstractSymbol token;
	/** Creates "int_const" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for token
	  */
	public int_const(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		token = a1;
	}
	public TreeNode copy() {
		return new int_const(lineNumber, copy_AbstractSymbol(token));
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "int_const\n");
		dump_AbstractSymbol(out, n+2, token);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_int");
		dump_AbstractSymbol(out, n + 2, token);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method method is provided
	  * to you as an example of code generation.
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		CgenSupport.emitLoadInt(CgenSupport.ACC,
								(IntSymbol)AbstractTable.inttable.lookup(token.getString()), s);
	}

}


/** Defines AST constructor 'bool_const'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
	public Boolean val;
	/** Creates "bool_const" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for val
	  */
	public bool_const(int lineNumber, Boolean a1) {
		super(lineNumber);
		val = a1;
	}
	public TreeNode copy() {
		return new bool_const(lineNumber, copy_Boolean(val));
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "bool_const\n");
		dump_Boolean(out, n+2, val);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_bool");
		dump_Boolean(out, n + 2, val);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method method is provided
	  * to you as an example of code generation.
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(val), s);
	}

}


/** Defines AST constructor 'string_const'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
	public AbstractSymbol token;
	/** Creates "string_const" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for token
	  */
	public string_const(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		token = a1;
	}
	public TreeNode copy() {
		return new string_const(lineNumber, copy_AbstractSymbol(token));
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "string_const\n");
		dump_AbstractSymbol(out, n+2, token);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_string");
		out.print(Utilities.pad(n + 2) + "\"");
		Utilities.printEscapedString(out, token.getString());
		out.println("\"");
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method method is provided
	  * to you as an example of code generation.
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		CgenSupport.emitLoadString(CgenSupport.ACC,
								   (StringSymbol)AbstractTable.stringtable.lookup(token.getString()), s);
	}

}


/** Defines AST constructor 'new_'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
	public AbstractSymbol type_name;
	/** Creates "new_" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for type_name
	  */
	public new_(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		type_name = a1;
	}
	public TreeNode copy() {
		return new new_(lineNumber, copy_AbstractSymbol(type_name));
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "new_\n");
		dump_AbstractSymbol(out, n+2, type_name);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_new");
		dump_AbstractSymbol(out, n + 2, type_name);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		s.println("# start of 'new " + type_name + "'");
        if (type_name != TreeConstants.SELF_TYPE) {
        	//find prototype's addres and load to ACC
            CgenSupport.emitLoadAddress(CgenSupport.ACC, type_name.toString() + CgenSupport.PROTOBJ_SUFFIX, s);
            // copy protoype
            CgenSupport.emitJal("Object.copy", s);
            // jump to init method
            CgenSupport.emitJal(type_name.toString() + CgenSupport.CLASSINIT_SUFFIX, s);
        }else{
        	// load address of class object tab into T1
        	CgenSupport.emitLoadAddress(CgenSupport.T1, CgenSupport.CLASSOBJTAB, s);
        	// load tag number of SELF on T2
            CgenSupport.emitLoad(CgenSupport.T2, 0, CgenSupport.SELF, s);
            // shift tag number to get the address of SELF object
            CgenSupport.emitSll(CgenSupport.T2, CgenSupport.T2, 3, s);
            // find the address of SELF object
            CgenSupport.emitAddu(CgenSupport.T1, CgenSupport.T1, CgenSupport.T2, s);
            // load object's adress into ACC
            CgenSupport.emitLoad(CgenSupport.ACC, 0, CgenSupport.T1, s);
            // push content the address of SELF object (address os self object) on to stack, as temporary
            CgenSupport.emitPush(CgenSupport.T1, s);
            // copy SELF object
            CgenSupport.emitJal("Object.copy", s);
            // pop content of stack (address self object) back back to T1
            CgenSupport.emitPop(CgenSupport.T1, s);
            // load
            CgenSupport.emitLoad(CgenSupport.T1, 1, CgenSupport.T1, s);
            CgenSupport.emitJalr(CgenSupport.T1, s);
        }
        s.println("# end of 'new " + type_name + "'");
	}
}


/** Defines AST constructor 'isvoid'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
	public Expression e1;
	/** Creates "isvoid" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for e1
	  */
	public isvoid(int lineNumber, Expression a1) {
		super(lineNumber);
		e1 = a1;
	}
	public TreeNode copy() {
		return new isvoid(lineNumber, (Expression)e1.copy());
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "isvoid\n");
		e1.dump(out, n+2);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_isvoid");
		e1.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		s.println("# start of isvoid");
		// emit code
        e1.code(s);
        // generate label for void
        int labelIsVoid = CgenSupport.genNextLabel();
        // emit branch if ACC is zero, void
        CgenSupport.emitBeqz(CgenSupport.ACC, labelIsVoid, s);
        // if ACC is not void, load false
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);
        // generate lable for continue
        int labelContinue = CgenSupport.genNextLabel();
        // branch to continue
        CgenSupport.emitBranch(labelContinue, s);
        // is void
        CgenSupport.emitLabelDef(labelIsVoid, s);
        // load true
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true),s);
        // emit continue label
        CgenSupport.emitLabelDef(labelContinue, s);
        s.println("# end of isvoid");
	}
}


/** Defines AST constructor 'no_expr'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
	/** Creates "no_expr" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  */
	public no_expr(int lineNumber) {
		super(lineNumber);
	}
	public TreeNode copy() {
		return new no_expr(lineNumber);
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "no_expr\n");
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_no_expr");
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		// do not need to code
	}


}


/** Defines AST constructor 'object'.
	<p>
	See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
	public AbstractSymbol name;
	/** Creates "object" AST node. 
	  *
	  * @param lineNumber the line in the source file from which this node came.
	  * @param a0 initial value for name
	  */
	public object(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		name = a1;
	}
	public TreeNode copy() {
		return new object(lineNumber, copy_AbstractSymbol(name));
	}
	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "object\n");
		dump_AbstractSymbol(out, n+2, name);
	}

	
	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_object");
		dump_AbstractSymbol(out, n + 2, name);
		dump_type(out, n);
	}
	/** Generates code for this expression.  This method is to be completed 
	  * in programming assignment 5.  (You may add or remove parameters as
	  * you wish.)
	  * @param s the output stream 
	  * */
	public void code(PrintStream s) {
		if (name == TreeConstants.self) {
            CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
        } else {
            ((Variable)CgenClassTable.ct.lookup(name)).emitLoad(s);
        }
	}
}


