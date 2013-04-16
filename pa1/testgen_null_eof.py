#!/usr/bin/python

import os, sys

os.system('rm -f null.cl eof_string.cl eof_inline_comment.cl eof_comment.cl')

# test for null char
nul = chr(0);
with open('null.cl', 'w') as f:
    f.write('B1 identi%cfier E1\n' % nul)
    f.write('B2(*multiline % ccomment*)E2\n' % nul)
    f.write('B3 12345%c67890 E3\n' % nul)
    f.write('B4\"string with null %c char\"E4\n' % nul)
    f.write('B5\"unterminated string with null %c char E5\n' % nul)
    f.write('B6--inline comment with null % char E6\n' % nul)
    f.write('B7(*nested comment with (* null %c char *) *)E7\n' % nul)


with open ('eof_string.cl', 'w') as f:
    f.write('B1"String ends in eof')

with open('eof_inline', 'w') as f:
    f.write('B1--inline comment ends in eof')

with open('eof_comment.cl', 'w') as f:
    f.write('B1(*comment ends in eof')





