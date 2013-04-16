#!/usr/bin/python

import os, sys

print "\n>>> CS143 PA1 Testing Script <<<"


for arg in sys.argv[1:]:
    print "COOL file: " + arg
    os.system("rm -f lexer.out mylexer.out")
    os.system("/usr/class/cs143/bin/coolc -k " + arg + " &> /dev/null")
    os.system("../lexer " + arg + " > mylexer.out")
    res = os.system("diff lexer.out mylexer.out")
    if res == 0:
        print "PASS"
    else:
        print "FAIL"



