#!/usr/bin/python

import os, sys, re

print '\n>>> CS143 PA2 Testing Script <<<'
for arg in sys.argv[1:]:

	# initialization
	print 'COOL file: ' + arg
	os.system('rm -f parser.out myparser.out parser_noline.out myparser_noline.out')
	
	# run our implementation and the reference 
	os.system('/usr/class/cs143/bin/coolc -k ' + arg + ' &> /dev/null')
	os.system('../lexer ' + arg + ' | ../parser > myparser.out')
	
	# remove line numbers from parser.out and myparser.out
	with open('parser.out', 'r') as fin, open('parser_noline.out', 'w') as fout: 
		for line in fin:
			fout.write(re.sub('\s*#[0-9]+\n', '', line))	
	with open('myparser.out', 'r') as fin, open('myparser_noline.out', 'w') as fout: 
		for line in fin:
			fout.write(re.sub('\s*#[0-9]+\n', '', line))
		
	# compare results
	res = os.system("diff parser_noline.out myparser_noline.out")
	if res == 0:
		print "PASS"
	else:
		print "FAIL"



