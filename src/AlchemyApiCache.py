from os import path
import json

import config

_cacheLocation = path.join(config.path_dataDirectory, 'alchemy_cache')
_cacheFilenameFormat = '{}{:0>5d}.json'

def _getCacheFileName(bookName, segmentType, segmentId):
    return path.join(_cacheLocation, _cacheFilenameFormat).format(segmentType, segmentId)

def load(bookName, segmentType, segmentId):
    bookFile = open(bookName, 'r')
    jsonObj = json.loads(bookFile.read())
    bookFile.close()
    return json

def save(data, bookName, sectionId):
    f = open(bookName, 'w')
    f.write(json.dumps(AlchemyApiAnalysis.callEntityExtract(section), indent=2))
    f.close()

def loadSection(bookName, sectionId):
    return load(bookName, 'section', sectionId)

def saveSection(bookName, sectionId):
    return save(bookName, 'section', sectionId)

def loadWyrdSistersSection(sectionId):
    loadSection('wyrd_sisters', sectionId)

def saveWyrdSistersSection(sectionId):
    saveSection('wyrd_sisters', sectionId)
