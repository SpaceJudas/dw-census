#!/usr/bin/python

class Book:
  # Open the book with the given name
  def __init__(self, name='Wyrd Sisters', fileExtension='txt'):
    self._filename = os.path.join('./data','{}.{}'.format(name, fileExtension))

  #returns a file object
  def open(self):
    return open(self._filename, 'r')

  def readSections(self):
    return re.split('\n\n+', self.open().read())
