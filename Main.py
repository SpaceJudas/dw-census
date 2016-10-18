#!/usr/bin/python
'''
me at the console

b = Book()
b.open()
'''

'''
This is all mine!

Requirements:
 - https://github.com/watson-developer-cloud/python-sdk

'''
import config

import os


class Book:
  # Open the book with the given name
  def __init__(self, name='Wyrd Sisters', fileExtension='txt'):
    self._filename = os.path.join('./data','{}.{}'.format(name, fileExtension))

  #returns a file object
  def open(self):
    return open(self._filename, 'r')

### Utils
# Cleans an array of lines from a file. This trims whitespace and removes empty lines.
def cleanTextArray(arr):
  moddedLines = filter(lambda str: len(str) > 0,
                  map(lambda str: str.strip(), lines))
  return list(moddedLines)


### main stuff
#from Main import Book
#b = Book()
#bookText = b.open().read()

#import AlchemyApiAnalysis
#print(AlchemyApiAnalysis.callEntityExtract(bookText))
