#!/usr/bin/python

import os, sys, re

print '\n>>> CS143 PA3 Testing Script <<<'

debug=''
ref=False
built=False
num=0

files=[]
outs=[]
errs=[]

def dotest(arg):
	global num, debug, ref, built

	if not re.match('.*\.cl$', arg):
		return

	# Input file
	print '\n---\n# Input file #%d: %s' % (num, arg)
	num += 1
	files.append(arg)
	
	if ref:
		# Run reference only and output to console...
		print '# Run reference only and output to console...'
		os.system('/usr/class/cs143/bin/.coolref/lexer ' + arg + 
			' | /usr/class/cs143/bin/.coolref/parser'
			' | /usr/class/cs143/bin/.coolref/semant')
		return
		
	# Remove previous output files...
	print '# Remove previous output files...'
	os.system('rm -f lexer.out parser.out semant.out semant.err mysemant.out mysemant.err')



	# Running reference...
	print '# Running reference...' 
	os.system('/usr/class/cs143/bin/.coolref/lexer ' + arg + ' 2> lexer.err'
		' | /usr/class/cs143/bin/.coolref/parser 2> parser.err'
		' | /usr/class/cs143/bin/.coolref/semant > semant.out 2> semant.err')
	
	# Check for lexer/parser error
	if os.path.getsize('lexer.err') > 0 or os.path.getsize('parser.err') > 0:
		print 'Lexer/parser ERROR!'
		outs.append('Lexer/parser ERROR')
		errs.append('')
		return
		
	
	# Running our implementation...
	print '# Running our implementation...'
	os.system('./lexer ' + arg +
		' | ./parser '
		' | ./semant %s > mysemant.out 2> mysemant.err' % debug)
	


	# Compare outputs...
	print '# Compare output...'
	if os.system('diff semant.out mysemant.out') == 0:
		outs.append('p')
		print 'PASS'
	else:
		outs.append('FAIL')
		print 'FAIL'
		
	# Compare errors...
	print '# Compare errors...'
	if os.system('diff semant.err mysemant.err') == 0:
		errs.append('p')
		print 'PASS'
	else:
		errs.append('FAIL')
		print 'FAIL'
	

for arg in sys.argv[1:]:
	
	# Check if debug mode
	if arg == '-s':
		print '# DEBUG on'
		debug = '-s';
		continue
		
	# Check if ref_only mode
	elif arg == '-r':
		print '# REF_ONLY on'
		ref = True
		continue
	
	# Check if needs to compile	
	elif not ref and not built:
		print '# Building semant...'
		if os.system('gmake semant') != 0:
			print 'Build error! Aborting...'
			exit(-1);
		else:
			built = True
	
	# Run tests
	if not os.path.isdir(arg):
		dotest(arg);
	else:
		for f in sorted(os.listdir(arg)):
			dotest(arg + '/' + f);
	
# Display test summary
print '\n# Summary:\n%-20s\tstdout\tstderr' % 'File'
for f, o, e in zip(files, outs, errs):
	print '%-20s\t%s\t%s' % (f, o, e)
	

