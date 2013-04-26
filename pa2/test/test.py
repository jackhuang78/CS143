#!/usr/bin/python

import os, sys

print "\n>>> CS143 PA2 Testing Script <<<"
for arg in sys.argv[1:]:
	print "COOL file: " + arg
	os.system("rm -f parser.out myparser.out")
	os.system("/usr/class/cs143/bin/coolc -k " + arg + " &> /dev/null")
	os.system("../lexer " + arg + " | ../parser > myparser.out")
	res = os.system("diff parser.out myparser.out")
	if res == 0:
		print "PASS"
	else:
		print "FAIL"



