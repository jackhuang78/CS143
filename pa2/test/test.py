#!/usr/bin/python

import os, sys, re

print '\n>>> CS143 PA2 Testing Script <<<'
for arg in sys.argv[1:]:

	# initialization
	print 'COOL file: ' + arg
	os.system('rm -f parser.out myparser.out parser_noline.out myparser_noline.out')
	os.system('rm -f parser.err myparser.err parser_noline.err myparser.noline.err')
	
	# run our implementation and the reference 
	print 'running reference...' 
	os.system('/usr/class/cs143/bin/.coolref/lexer ' + arg + ' | /usr/class/cs143/bin/.coolref/parser > parser.out 2> parser.err')
	print 'running our implementation...'
	os.system('../lexer ' + arg + ' | ../parser > myparser.out 2> myparser.err')
	
	# remove line numbers from parser.out and myparser.out
	with open('parser.out', 'r') as fin, open('parser_noline.out', 'w') as fout: 
		for line in fin:
			fout.write(re.sub('\s*#[0-9]+\n', '', line))	
	with open('myparser.out', 'r') as fin, open('myparser_noline.out', 'w') as fout: 
		for line in fin:
			fout.write(re.sub('\s*#[0-9]+\n', '', line))
			
	# remove line numbers from parser.err and myparser.err
	with open('myparser.err', 'r') as fin, open('myparser_noline.err', 'w') as fout: 
		for line in fin:
			fout.write(re.sub('parse error at or near', 'syntax error at or near', line))	
			
	with open('parser.err', 'r') as fin, open('parser_noline.err', 'w') as fout: 
		for line in fin:
			fout.write(line)
		
	# compare results (out and err)
	res1 = os.system('diff parser_noline.out myparser_noline.out')
	res2 = os.system('diff parser_noline.err myparser_noline.err')
	if res1 != 0:
		print 'FAIL out'
	else:
		print 'PASS out'	
	if res2 != 0:
		print 'FAIL err'
	else:
		print 'PASS err'



