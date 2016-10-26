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
import json

import pandas

import AlchemyApiClient

from Book import Book
import my_utils

cacheFilenameFormat = './output/section{:0>4d}.json'

#pulls analsis by book section from Alchemy, then writes it to json files. THIS IS REALLY SLOW
def resetSectionAnalysisCache():
  wyrdSis = Book()
  sections = wyrdSis.readSections()
  i=1;
  for section in sections:
    filename=cacheFilenameFormat.format(i)
    f = open(filename, 'w')
    f.write(json.dumps(AlchemyApiAnalysis.callEntityExtract(section),indent=2))
    i+=1

def getFileNameList():
  wyrdSis = Book()
  sections = wyrdSis.readSections()
  filenames = list(map(lambda i: cacheFilenameFormat.format(i+1),
                  range(len(sections))))
  return filenames

def build(jsonData):
  return None

def buildEntityDataFrame(analysisResponse):
  rows = { 'name':[], 'type':[] }
  for entity in analysisResponse['entities']:
    rows.append({'name':entity['text'],'type':entity['type']})
  return pandas.DataFrame(rows)
'''
def buildEntityWithSentimentDataFrame(analysisResponse):
  entityDataFrame = buildEntityDataFrame(analysisResponse)
  rows = { 'name':[], 'type':[] }
  for entity in analysisResponse['entities']:
    entityRow = {'name':entity['text'],'type':entity['type']}
    if (entity['type'] == 'Person'):
      entityRow['disgust'] = entity['emotions']['disgust']
      entityRow['joy'] = entity['emotions']['joy']
      entityRow['sadness'] = entity['emotions']['sadness']
      entityRow['anger'] = entity['emotions']['anger']
      entityRow['fear'] = entity['emotions']['fear']

    rows.append(entityRow)
  return pandas.DataFrame(rows)


class Quote:
  text
  entityName
  entityType
  sentimentScore
  sentimentTyp

'''
for filename in getFileNameList():
  analysisResponse = json.loads(open(filename).read())
  for(entity in analysisResponse['entities']):
    pandasList = []
    entityName = entity['text']
    entityType = entity['type']
    entityQuotes = entity['quotations']
    for quote in entityQuotes



#results = AlchemyApiAnalysis.extractUrl()
#print(results)

#linesRaw = Book().open().readlines()
#linesClean = cleanTextArray(linesRaw)
