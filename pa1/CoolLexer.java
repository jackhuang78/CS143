/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */
	// Max size of string constants
	static int MAX_STR_CONST = 1025;
	// For assembling string constants
	StringBuffer string_buf = new StringBuffer();
	// For remembering the comment type
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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
	// empty for now
	}

	private boolean yy_eof_done = false;
	private final int COMMENT_INLINE = 2;
	private final int STRING = 3;
	private final int YYINITIAL = 0;
	private final int COMMENT_MULTI = 1;
	private final int ILLEGAL_STRING = 4;
	private final int yy_state_dtrans[] = {
		0,
		64,
		68,
		71,
		73
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"51,48:7,49:2,24,48,49,25,48:18,49,48,34,48:5,30,33,31,44,39,32,40,43,27:10," +
"37,38,45,35,36,48,41,21,26:3,20,26:6,22,26:5,17,23,26,18,26:5,48,50,48:2,28" +
",48,3,29,1,14,5,6,29,9,7,29:2,2,29,8,13,15,29,10,4,11,19,12,16,29:3,46,48,4" +
"7,42,48,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,116,
"0,1,2,3,1,4,5,1,6,7,8,1:2,9,1:8,10,1:2,11:2,12,11,1:6,11:15,1:14,13,14,15,1" +
"6,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,4" +
"1,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,11,59,60,61,62,63")[0];

	private int yy_nxt[][] = unpackFromString(64,52,
"1,2,89,110:2,111,65,69,90,110:2,112,110,72,110,113,114,3:2,110,3:4,4,5,3,6," +
"7,110,8,9,10,11,12,13,7,14,15,16,17,18,19,20,21,22,23,24,7,5,7:2,-1:53,110," +
"115,91,110:20,-1:2,110:4,-1:23,3:23,-1:2,3:4,-1:47,5,-1:23,5,-1:29,6,-1:55," +
"29,-1:53,30,-1:50,31,-1:55,32,-1:47,33,-1:2,34,-1:17,110:23,-1:2,110:4,-1:2" +
"3,110:8,104,110:14,-1:2,110:4,-1:22,1,50:23,51,-1,50:4,66,70,50:20,-1,110:2" +
",95,110:3,25,110:13,95,110:2,-1:2,110:4,-1:53,52,-1:21,56:23,60,-1,56:26,1," +
"54:23,55,-1,54:26,-1,110:3,96,110,26,110,27,110:15,-1:2,110:4,-1:55,53,-1:1" +
"8,1,56:23,57,-1,56:8,58,56:15,67,59,-1,110:5,28,110:17,-1:2,110:4,-1:22,1,6" +
"1:23,62,-1,61:8,63,61:17,-1,110:10,35,110:12,-1:2,110:4,-1:23,110:15,36,110" +
":7,-1:2,110:4,-1:23,110:10,37,110:12,-1:2,110:4,-1:23,110:4,38,110:18,-1:2," +
"110:4,-1:23,110:14,39,110:8,-1:2,110:4,-1:23,110:4,40,110:18,-1:2,110:4,-1:" +
"23,41,110:22,-1:2,110:4,-1:23,110:7,42,110:15,-1:2,110:4,-1:23,110:4,43,110" +
":14,43,110:3,-1:2,110:4,-1:23,110,44,110:21,-1:2,110:4,-1:23,110:3,45,110:1" +
"9,-1:2,110:4,-1:23,110:4,46,110:14,46,110:3,-1:2,110:4,-1:23,110:4,47,110:1" +
"8,-1:2,110:4,-1:23,110:13,48,110:9,-1:2,110:4,-1:23,110:3,49,110:19,-1:2,11" +
"0:4,-1:23,110:4,74,110:7,92,110:10,-1:2,110:4,-1:23,110:4,75,110:7,76,110:1" +
"0,-1:2,110:4,-1:23,110:3,77,110:19,-1:2,110:4,-1:23,110:12,78,110:10,-1:2,1" +
"10:4,-1:23,110:3,79,110:19,-1:2,110:4,-1:23,110:2,80,110:20,-1:2,110:4,-1:2" +
"3,110,102,110:19,102,110,-1:2,110:4,-1:23,110:11,103,110:11,-1:2,110:4,-1:2" +
"3,110:4,81,110:18,-1:2,110:4,-1:23,110:17,82:2,110:4,-1:2,110:4,-1:23,110:1" +
"2,83,110:10,-1:2,110:4,-1:23,110:6,105,110:16,-1:2,110:4,-1:23,110:3,84,110" +
":19,-1:2,110:4,-1:23,110:3,85,110:18,85,-1:2,110:4,-1:23,110:12,106,110:10," +
"-1:2,110:4,-1:23,110:4,107,110:18,-1:2,110:4,-1:23,110,86,110:21,-1:2,110:4" +
",-1:23,110:6,87,110:16,-1:2,110:4,-1:23,110:9,108,110:13,-1:2,110:4,-1:23,1" +
"10:6,109,110:16,-1:2,110:4,-1:23,110:10,88,110:12,-1:2,110:4,-1:23,110,93,1" +
"10,94,110:19,-1:2,110:4,-1:23,110:8,97,98,110:6,98,110:6,-1:2,110:4,-1:23,1" +
"10:12,99,110:10,-1:2,110:4,-1:23,110:8,100,110:14,-1:2,110:4,-1:23,110:2,10" +
"1,110:20,-1:2,110:4,-1:22");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
	case COMMENT_MULTI:
		yybegin(YYINITIAL);
		return new Symbol(TokenConstants.ERROR, "EOF in comment");	
	case STRING:
		yybegin(YYINITIAL);
		return new Symbol(TokenConstants.ERROR, "EOF in string constant");
	}
	return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -3:
						break;
					case 3:
						{ 
	return new Symbol(TokenConstants.TYPEID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -4:
						break;
					case 4:
						{
	this.curr_lineno += 1;
}
					case -5:
						break;
					case 5:
						{ 
}
					case -6:
						break;
					case 6:
						{ 
	return new Symbol(TokenConstants.INT_CONST, 
		AbstractTable.inttable.addString(yytext()));
}
					case -7:
						break;
					case 7:
						{
		/*  This rule should be the very last
			in your lexical specification and
			will match match everything not
			matched by other lexical rules. */
		return new Symbol(TokenConstants.ERROR,
			AbstractTable.stringtable.addString(yytext()));
				//System.err.println("LEXER BUG - UNMATCHEDs: " + yytext()); 
}
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.LPAREN);}
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.MULT);}
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.MINUS);}
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.RPAREN);}
					case -12:
						break;
					case 12:
						{
	string_buf = new StringBuffer();
	yybegin(STRING);
}
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.EQ);}
					case -14:
						break;
					case 14:
						{ return new Symbol(TokenConstants.COLON);}
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.SEMI);}
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.COMMA);}
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.DOT);}
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.AT);}
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.NEG);}
					case -20:
						break;
					case 20:
						{ return new Symbol(TokenConstants.DIV);}
					case -21:
						break;
					case 21:
						{ return new Symbol(TokenConstants.PLUS);}
					case -22:
						break;
					case 22:
						{ return new Symbol(TokenConstants.LT);}
					case -23:
						break;
					case 23:
						{ return new Symbol(TokenConstants.LBRACE);}
					case -24:
						break;
					case 24:
						{ return new Symbol(TokenConstants.RBRACE);}
					case -25:
						break;
					case 25:
						{ return new Symbol(TokenConstants.FI);}
					case -26:
						break;
					case 26:
						{ return new Symbol(TokenConstants.IF);}
					case -27:
						break;
					case 27:
						{ return new Symbol(TokenConstants.IN);}
					case -28:
						break;
					case 28:
						{ return new Symbol(TokenConstants.OF);}
					case -29:
						break;
					case 29:
						{
	yybegin(COMMENT_MULTI);
	comment_level = 1;
}
					case -30:
						break;
					case 30:
						{
	return new Symbol(TokenConstants.ERROR, "Unmatched *)");
}
					case -31:
						break;
					case 31:
						{
	yybegin(COMMENT_INLINE);
}
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.DARROW);}
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.ASSIGN);}
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.LE);}
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.LET);}
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.NEW);}
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.NOT);}
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.CASE);}
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.LOOP);}
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.ELSE);}
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.ESAC);}
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.THEN);}
					case -43:
						break;
					case 43:
						{ 
	return new Symbol(TokenConstants.BOOL_CONST, new Boolean(true));
}
					case -44:
						break;
					case 44:
						{ return new Symbol(TokenConstants.POOL);}
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.CLASS);}
					case -46:
						break;
					case 46:
						{ 
	return new Symbol(TokenConstants.BOOL_CONST, new Boolean(false));
}
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.WHILE);}
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.ISVOID);}
					case -49:
						break;
					case 49:
						{ return new Symbol(TokenConstants.INHERITS);}
					case -50:
						break;
					case 50:
						{
}
					case -51:
						break;
					case 51:
						{
	this.curr_lineno += 1;
}
					case -52:
						break;
					case 52:
						{
	comment_level++;
}
					case -53:
						break;
					case 53:
						{
	comment_level--;
	if(comment_level == 0)
		yybegin(YYINITIAL);
}
					case -54:
						break;
					case 54:
						{
}
					case -55:
						break;
					case 55:
						{
	yybegin(YYINITIAL);   
	this.curr_lineno += 1;
}
					case -56:
						break;
					case 56:
						{
	if(yytext().equals("\\b")) 
		string_buf.append("\b");
	else if(yytext().equals("\\t"))
		string_buf.append("\t");
	else if(yytext().equals("\\n"))
		string_buf.append("\n");
	else if(yytext().equals("\\f"))
		string_buf.append("\f");
	else if(yytext().equals("\\0"))
		string_buf.append("0");
	else if(yytext().length() > 1)
		string_buf.append(yytext().charAt(1));
	else
		string_buf.append(yytext());
	if(string_buf.length() >= MAX_STR_CONST) {
		yybegin(ILLEGAL_STRING);
		return new Symbol(TokenConstants.ERROR, "String constant too long");
	}		
}
					case -57:
						break;
					case 57:
						{
	this.curr_lineno += 1;
	yybegin(YYINITIAL);
	return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
}
					case -58:
						break;
					case 58:
						{
	yybegin(YYINITIAL);
	return new Symbol(TokenConstants.STR_CONST,
		AbstractTable.stringtable.addString(string_buf.toString()));
}
					case -59:
						break;
					case 59:
						{
	yybegin(ILLEGAL_STRING);
	return new Symbol(TokenConstants.ERROR, "String contains null character");
}
					case -60:
						break;
					case 60:
						{
	this.curr_lineno += 1;
	string_buf.append("\n");
}
					case -61:
						break;
					case 61:
						{
}
					case -62:
						break;
					case 62:
						{
	this.curr_lineno += 1;
	yybegin(YYINITIAL);
}
					case -63:
						break;
					case 63:
						{
	yybegin(YYINITIAL);
}
					case -64:
						break;
					case 65:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -65:
						break;
					case 66:
						{
}
					case -66:
						break;
					case 67:
						{
	if(yytext().equals("\\b")) 
		string_buf.append("\b");
	else if(yytext().equals("\\t"))
		string_buf.append("\t");
	else if(yytext().equals("\\n"))
		string_buf.append("\n");
	else if(yytext().equals("\\f"))
		string_buf.append("\f");
	else if(yytext().equals("\\0"))
		string_buf.append("0");
	else if(yytext().length() > 1)
		string_buf.append(yytext().charAt(1));
	else
		string_buf.append(yytext());
	if(string_buf.length() >= MAX_STR_CONST) {
		yybegin(ILLEGAL_STRING);
		return new Symbol(TokenConstants.ERROR, "String constant too long");
	}		
}
					case -67:
						break;
					case 69:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -68:
						break;
					case 70:
						{
}
					case -69:
						break;
					case 72:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -70:
						break;
					case 74:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -71:
						break;
					case 75:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -72:
						break;
					case 76:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -73:
						break;
					case 77:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -74:
						break;
					case 78:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -75:
						break;
					case 79:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -76:
						break;
					case 80:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -77:
						break;
					case 81:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -78:
						break;
					case 82:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -79:
						break;
					case 83:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -80:
						break;
					case 84:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -81:
						break;
					case 85:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -82:
						break;
					case 86:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -83:
						break;
					case 87:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -84:
						break;
					case 88:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -85:
						break;
					case 89:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -86:
						break;
					case 90:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -87:
						break;
					case 91:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -88:
						break;
					case 92:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -89:
						break;
					case 93:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -90:
						break;
					case 94:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -91:
						break;
					case 95:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -92:
						break;
					case 96:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -93:
						break;
					case 97:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -94:
						break;
					case 98:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -95:
						break;
					case 99:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -96:
						break;
					case 100:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -97:
						break;
					case 101:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -98:
						break;
					case 102:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -99:
						break;
					case 103:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -100:
						break;
					case 104:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -101:
						break;
					case 105:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -102:
						break;
					case 106:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -103:
						break;
					case 107:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -104:
						break;
					case 108:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -105:
						break;
					case 109:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -106:
						break;
					case 110:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -107:
						break;
					case 111:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -108:
						break;
					case 112:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -109:
						break;
					case 113:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -110:
						break;
					case 114:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -111:
						break;
					case 115:
						{ 
	return new Symbol(TokenConstants.OBJECTID, 
		AbstractTable.idtable.addString(yytext()));
}
					case -112:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
