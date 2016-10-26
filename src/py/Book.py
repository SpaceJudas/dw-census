#!/usr/bin/python
import os
import re

class Book:
  # Open the book with the given name
  # sectionDelimeterRe: a regex to use
  def __init__(self, name='Wyrd Sisters', sectionDelimeterRe='\n\n+'):
    self.name = name

  def getBookId(self):
    return self.name.lower().replace(' ', '_')

  #Builds the filename for this book. This method currently assumes that the
  #book will be in txt format, and in the diretory specified
  def getFilePath(self):
    return os.path.join('./data','{}.txt'.format(self.name))

  #returns a file object for the book, based on the getFilePath command
  def open(self):
    return open(self.getFilePath(), 'r')

  def readSections(self):
    return re.split('\n\n+', self.open().read())

  #Loads up the cache from alchemy api
  #def readSectionAnalyses():
