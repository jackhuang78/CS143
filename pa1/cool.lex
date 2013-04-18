/*
 *	Stanford University, Spring 2013
 *	CS143, Aiken
 *	Programming Assignment 1
 *	
 *	Jack Huang (yh3483)
 *	Borui Wang
 *  
 */

/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

	// Max size of string constants
	static int MAX_STR_CONST = 1025;

	// For assembling string constants
	StringBuffer string_buf = new StringBuffer();

	// For remembering the nested comment level
	int comment_level;

	private int curr_lineno = 1;
	int get_curr_lineno() {
	return curr_lineno;
	}

	private AbstractSymbol filename;

	void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
	}

	AbstractSymbol curr_filename() {
	return filename;
	}

	
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

	// empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

	switch(yy_lexical_state) {
	case YYINITIAL:
		/* nothing special to do in the initial state */
		break;

	case COMMENT_INLINE:
		/* this is a legal way of ending inline comment */
		break;
	
	case ILLEGAL_STRING:
		/* error already returned, so do nothing */
		break;

	case COMMENT_MULTI:
		yybegin(YYINITIAL);
		return new Symbol(TokenConstants.ERROR, "EOF in comment");	

	case STRING:
		yybegin(YYINITIAL);
		return new Symbol(TokenConstants.ERROR, "EOF in string constant");
		


	}

	return new Symbol(TokenConstants.EOF);

%eofval}

%class CoolLexer
%cup


%state COMMENT_MULTI
%state COMMENT_INLINE
%state STRING
%state ILLEGAL_STRING

INTEGER = [0-9]+
VTRUE = t(R|r)(U|u)(E|e)
VFALSE = f(A|a)(L|l)(S|s)(E|e)
NEWLINE = \n
WHITESPACES = [\b\t\f\r ]+
CLASSNAME = ([A-Z][A-Za-z0-9_]*)|(SELF_TYPE)
OBJECTNAME = [a-z][A-Za-z0-9_]*


%%

<YYINITIAL> "class"		{ return new Symbol(TokenConstants.CLASS);}
<YYINITIAL> "else"		{ return new Symbol(TokenConstants.ELSE);}
<YYINITIAL> "fi"		{ return new Symbol(TokenConstants.FI);}
<YYINITIAL> "if"		{ return new Symbol(TokenConstants.IF);}
<YYINITIAL> "in"		{ return new Symbol(TokenConstants.IN);}
<YYINITIAL> "inherits"	{ return new Symbol(TokenConstants.INHERITS);}
<YYINITIAL> "isvoid"	{ return new Symbol(TokenConstants.ISVOID);}
<YYINITIAL> "let"		{ return new Symbol(TokenConstants.LET);}
<YYINITIAL> "loop"		{ return new Symbol(TokenConstants.LOOP);}
<YYINITIAL> "pool"		{ return new Symbol(TokenConstants.POOL);}
<YYINITIAL> "then"		{ return new Symbol(TokenConstants.THEN);}
<YYINITIAL> "while"		{ return new Symbol(TokenConstants.WHILE);}
<YYINITIAL> "case"		{ return new Symbol(TokenConstants.CASE);}
<YYINITIAL> "esac"		{ return new Symbol(TokenConstants.ESAC);}
<YYINITIAL> "new"		{ return new Symbol(TokenConstants.NEW);}
<YYINITIAL> "of"		{ return new Symbol(TokenConstants.OF);}
<YYINITIAL> "not"		{ return new Symbol(TokenConstants.NOT);}

<YYINITIAL> {VTRUE} { 
	return new Symbol(TokenConstants.BOOL_CONST, new Boolean(true));
}
<YYINITIAL> {VFALSE} { 
	return new Symbol(TokenConstants.BOOL_CONST, new Boolean(false));
}

<YYINITIAL> {NEWLINE} {
	this.curr_lineno += 1;
}
<YYINITIAL> {WHITESPACES} { 
	// ignore white spaces
}

<YYINITIAL> {CLASSNAME} { 
	return new Symbol(TokenConstants.TYPEID, 
		AbstractTable.idtable.addString(yytext()));
}
<YYINITIAL> {OBJECTNAME} { 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
<YYINITIAL> {INTEGER} { 
	return new Symbol(TokenConstants.INT_CONST, 
		AbstractTable.inttable.addString(yytext()));
}


<YYINITIAL> "--" {
	yybegin(COMMENT_INLINE);
}

<YYINITIAL> "(*" {
	yybegin(COMMENT_MULTI);
	comment_level = 1;
}  

<YYINITIAL> "*)" {
	return new Symbol(TokenConstants.ERROR, "Unmatched *)");
} 

<YYINITIAL> "\"" {
	string_buf = new StringBuffer();
	yybegin(STRING);
}



<YYINITIAL> "=>"	{ return new Symbol(TokenConstants.DARROW);}
<YYINITIAL> ":"		{ return new Symbol(TokenConstants.COLON);}
<YYINITIAL> ";"		{ return new Symbol(TokenConstants.SEMI);}
<YYINITIAL> ","		{ return new Symbol(TokenConstants.COMMA);}
<YYINITIAL> "."		{ return new Symbol(TokenConstants.DOT);}
<YYINITIAL> "@"		{ return new Symbol(TokenConstants.AT);}
<YYINITIAL> "~"		{ return new Symbol(TokenConstants.NEG);}
<YYINITIAL> "*"		{ return new Symbol(TokenConstants.MULT);}
<YYINITIAL> "/"		{ return new Symbol(TokenConstants.DIV);}
<YYINITIAL> "+"		{ return new Symbol(TokenConstants.PLUS);}
<YYINITIAL> "-"		{ return new Symbol(TokenConstants.MINUS);}
<YYINITIAL> "<="	{ return new Symbol(TokenConstants.LE);}
<YYINITIAL> "<-"	{ return new Symbol(TokenConstants.ASSIGN);}
<YYINITIAL> "<"		{ return new Symbol(TokenConstants.LT);}
<YYINITIAL> "="		{ return new Symbol(TokenConstants.EQ);}
<YYINITIAL> "("		{ return new Symbol(TokenConstants.LPAREN);}
<YYINITIAL> ")"		{ return new Symbol(TokenConstants.RPAREN);}
<YYINITIAL> "{"		{ return new Symbol(TokenConstants.LBRACE);}
<YYINITIAL> "}"		{ return new Symbol(TokenConstants.RBRACE);}


<COMMENT_MULTI> "(*" {
	// a nested comment in a comment
	comment_level++;
}

<COMMENT_MULTI> "*)" {
	// the closing comment, check for the nested level
	comment_level--;
	if(comment_level == 0)
		yybegin(YYINITIAL);
}
<COMMENT_MULTI> \n  {
	this.curr_lineno += 1;
}
<COMMENT_MULTI> .   {
	// ignore all other characters in multi-line comment
}


<COMMENT_INLINE> \n	{
	yybegin(YYINITIAL);   
	this.curr_lineno += 1;
}
<COMMENT_INLINE> .	{
	// ignroe all other characters in inline comment
}



<STRING> "\""   {
	// the closing quote
	yybegin(YYINITIAL);
	return new Symbol(TokenConstants.STR_CONST,
		AbstractTable.stringtable.addString(string_buf.toString()));
}

<STRING> \\\n  {
	// multi-line string
	this.curr_lineno += 1;
	string_buf.append("\n");
}

<STRING> \0 {
	yybegin(ILLEGAL_STRING);
	return new Symbol(TokenConstants.ERROR, "String contains null character.");
}

<STRING> \n {
	// newline char before closing quote or backslash
	this.curr_lineno += 1;
	yybegin(YYINITIAL);
	return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
}


<STRING> .|\\.	 {

	// match all single character
	// or all escaped characters that need special action

	if(yytext().length() == 1) {
		// all single character
		string_buf.append(yytext());
		
	} else if(yytext().equals("\\b")) {
		string_buf.append("\b");
		
	} else if(yytext().equals("\\t")) {
		string_buf.append("\t");
		
	} else if(yytext().equals("\\n")) {
		string_buf.append("\n");
		
	} else if(yytext().equals("\\f")) {
		string_buf.append("\f");
		
	} else if(yytext().equals("\\0")) {
		string_buf.append("0");
		
	} else {
		// all other escaped character
		string_buf.append(yytext().charAt(1));
	}

	if(string_buf.length() >= MAX_STR_CONST) {
		yybegin(ILLEGAL_STRING);
		return new Symbol(TokenConstants.ERROR, "String constant too long");
	}		
}



<ILLEGAL_STRING> \n {
	// the newline char in the unclosed illegal string
	this.curr_lineno += 1;
	yybegin(YYINITIAL);
}
<ILLEGAL_STRING> "\"" {
	// the closing quote of the illegal string
	yybegin(YYINITIAL);
}

<ILLEGAL_STRING> . {
	// ignore all other characters in illegal string
}



. {
	// matches all other unmatched symbols
	return new Symbol(TokenConstants.ERROR,
		AbstractTable.stringtable.addString(yytext()));

}








