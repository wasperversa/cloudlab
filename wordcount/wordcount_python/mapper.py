#!/usr/bin/env python3
import sys

# input comes from standard input (stdin)
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()
    # split the line into words
    words = line.split()
    # output tuples (word, 1)
    for word in words:
        print(f'{word}\t1')

