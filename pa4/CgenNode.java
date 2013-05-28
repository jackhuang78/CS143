/*
00..........                                                                  0000000000000000000000000Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.util.Vector;
import java.util.Enumeration;
import java.util.*;

class CgenNode extends class_ {
	/** The parent of this node in the inheritance tree */
	private CgenNode parent;

	/** The children of this node in the inheritance tree */
	private Vector children;

	/** Indicates a basic class */
	final static int Basic = 0;

	/** Indicates a class that came from a Cool program */
	final static int NotBasic = 1;
	
	/** Does this node correspond to a basic class? */
	private int basic_status;
	
	private StringSymbol nameStrSym;
	private int tag;
	
	private int methOff;
	private int attrOff;
	private Map<AbstractSymbol, Integer> methOffsets;
	private Map<AbstractSymbol, Integer> attrOffsets;
	
	private Map<AbstractSymbol, AbstractSymbol> attrTypes;
	
	

	/** Constructs a new CgenNode to represent class "c".
	 * @param c the class
	 * @param basic_status is this class basic or not
	 * @param table the class table
	 * */
	CgenNode(Class_ c, int basic_status, CgenClassTable table) {
		super(0, c.getName(), c.getParent(), c.getFeatures(), c.getFilename());
		this.parent = null;
		this.children = new Vector();
		this.basic_status = basic_status;
		this.nameStrSym = (StringSymbol)AbstractTable.stringtable.addString(name.getString());
		this.tag = -1;
		
		/*this.methMap = new LinkedHashMap<AbstractSymbol, List<AbstractSymbol>>();
		for(Enumeration e = features.getElements(); e.hasMoreElements();) {
			Feature feat = (Feature)e.nextElement();
			if(feat instanceof method) {
				method meth = (method)feat;
				List<AbstractSymbol> formList = new LinkedList<AbstractSymbol>();
				methMap.put(meth.name, formList);
			}
		}*/
	}
	


	void addChild(CgenNode child) {
		children.addElement(child);
	}

	/** Gets the children of this class
	 * @return the children
	 * */
	Enumeration getChildren() {
		return children.elements(); 
	}

	/** Sets the parent of this class.
	 * @param parent the parent
	 * */
	void setParentNd(CgenNode parent) {
		if (this.parent != null) {
			Utilities.fatalError("parent already set in CgenNode.setParent()");
		}
		if (parent == null) {
			Utilities.fatalError("null parent in CgenNode.setParent()");
		}
		this.parent = parent;
	}	
		

	/** Gets the parent of this class
	 * @return the parent
	 * */
	CgenNode getParentNd() {
		return parent; 
	}

	/** Returns true is this is a basic class.
	 * @return true or false
	 * */
	boolean basic() { 
		return basic_status == Basic; 
	}
	
	// PA4
	public String toString() {
		return String.format(
			"<[%d]class %s inherits %s>", 
			tag, getName(), getParent());
	}
	
	void setTag(int tag) {
		this.tag = tag;
	}
	
	int getTag() {
		return tag;
	}
	
	StringSymbol getNameStrSym() {
		return nameStrSym;
	}

	void buildFeatures() {
		attrTypes = new LinkedHashMap<AbstractSymbol, AbstractSymbol>();
		methOffsets = new LinkedHashMap<AbstractSymbol, Integer>();
		attrOffsets = new LinkedHashMap<AbstractSymbol, Integer>();
		methOff = 0;
		attrOff = 12;
		
		if(getName() != TreeConstants.Object_) {
			methOffsets.putAll(parent.methOffsets);
			attrOffsets.putAll(parent.attrOffsets);
			attrTypes.putAll(parent.attrTypes);
			methOff = parent.methOff;
			attrOff = parent.attrOff;
		}
		
		for(Enumeration e = features.getElements(); e.hasMoreElements();) {
			Feature feat = (Feature)e.nextElement();
			if(feat instanceof method) {
				methOffsets.put( ((method)feat).name, methOff );
				methOff += 4;
			} else {
				attrTypes.put( ((attr)feat).name, ((attr)feat).type_decl );
				attrOffsets.put( ((attr)feat).name, attrOff );
				attrOff += 4;
				
			}
		}
		
		for(Enumeration e = getChildren(); e.hasMoreElements();) {
			((CgenNode)e.nextElement()).buildFeatures();
		}
	}	
	
	void codeDispTab(PrintStream s) {	
		if(Flags.cgen_debug) {
			System.out.println("codeDispTab " + name + " " + methOffsets.size());		
		}
		
		for(AbstractSymbol method : methOffsets.keySet()) {
			s.print(CgenSupport.WORD);
			CgenSupport.emitMethodRef(name, method, s);
			s.println();
		}		
	}
	
	void codeProtObj(PrintStream s) {
		if(Flags.cgen_debug) {
			System.out.println("codeProtObj " + name + " " + attrOffsets.size());		
		}
		
				
		
		// class tag
		s.println(CgenSupport.WORD + getTag());				
		
		// size
		s.println(CgenSupport.WORD + (attrOff/4));	
		
		// disp table
		s.print(CgenSupport.WORD);	
		CgenSupport.emitDispTableRef(name, s);	
		s.println();					
		
		// attributes
		for(AbstractSymbol attr : attrTypes.keySet()) {
			
			AbstractSymbol type = attrTypes.get(attr);
			
			if(Flags.cgen_debug)
				System.out.println("\tattr: " + attr + ":" + type);
			
			s.print(CgenSupport.WORD);
						
			if(type == TreeConstants.Int)
				((IntSymbol)AbstractTable.inttable.addInt(0)).codeRef(s);
			else if(type == TreeConstants.Bool)
				s.print(CgenSupport.BOOLCONST_PREFIX + "0");
			else if(type == TreeConstants.Str)
				((StringSymbol)AbstractTable.stringtable.addString("")).codeRef(s);
			else
				s.print("0");
			s.println();
		}	
	}
}
	

	
