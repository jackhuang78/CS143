#!/usr/bin/python

import os, sys, re

print '\n>>> CS143 PA2 Testing Script <<<'
for arg in sys.argv[1:]:

	# initialization
	print 'COOL file: ' + arg
	os.system('rm -f parser.out myparser.out parser_noline.out myparser_noline.out')
	os.system('rm -f parser.err myparser.err')
	
	# run our implementation and the reference 
	print 'running reference...' 
	os.system('/usr/class/cs143/bin/coolc -k ' + arg + ' 2> parser.err')
	print 'running our implementation...'
	os.system('../lexer ' + arg + ' | ../parser > myparser.out 2> myparser.err')
	
	# remove line numbers from parser.out and myparser.out
	with open('parser.out', 'r') as fin, open('parser_noline.out', 'w') as fout: 
		for line in fin:
			fout.write(re.sub('\s*#[0-9]+\n', '', line))	
	with open('myparser.out', 'r') as fin, open('myparser_noline.out', 'w') as fout: 
		for line in fin:
			fout.write(re.sub('\s*#[0-9]+\n', '', line))

	size = os.path.getsize('parser.out')
		
	# compare results (out and err)
	res = os.system('diff parser_noline.out myparser_noline.out')
	if res != 0:
		print 'FAIL'
	elif size == 0:
		print 'Empty parser output; check parser.err and myparser.err' 
	else:
		print 'PASS'	



