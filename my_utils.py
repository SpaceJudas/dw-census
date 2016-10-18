#!/usr/bin/python

# Cleans an array of lines from a file. This trims whitespace and removes empty lines.
def cleanTextArray(arr):
  moddedLines = filter(lambda str: len(str) > 0,
                  map(lambda str: str.strip(), arr))
  return list(moddedLines)
