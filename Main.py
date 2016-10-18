#!/usr/bin/python

'''
This is all mine!

Requirements:
 - https://github.com/watson-developer-cloud/python-sdk

'''

# Open the book with the given name


def openBook(bookName):
  return open('./data/{}.txt'.format(bookName), 'r')

# Cleans an array of lines from a file. This trims whitespace and removes
# empty lines.


def cleanLines(lines):
  moddedLines = filter(lambda str: len(
      str) > 0, map(lambda str: str.strip(), lines))
  return list(moddedLines)

book = openBook('Wyrd Sisters')
lines = cleanLines(book.readlines())
