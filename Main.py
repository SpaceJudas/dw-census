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
import re
import json

import AlchemyApiAnalysis

from Book import Book
import my_utils

#pulls analsis by book section from Alchemy, then writes it to json files. THIS IS REALLY SLOW
def resetSectionAnalysisCache():
  wyrdSis = Book()
  sections = wyrdSis.readSections()
  i=1;
  for section in sections:
    filename='./output/section{:0>4d}.json'.format(i)
    f = open(filename, 'w')
    f.write(json.dumps(AlchemyApiAnalysis.callEntityExtract(section),indent=2))
    i+=1


def getFileNameList():
  wyrdSis = Book()
  sections = wyrdSis.readSections()
  filenames = list(map(lambda i: './output/section{:0>4d}.json'.format(i),
                  range(len(sections))))
  print(filenames)

getFileNameList()
#results = AlchemyApiAnalysis.extractUrl()
#print(results)

#linesRaw = Book().open().readlines()
#linesClean = cleanTextArray(linesRaw)
