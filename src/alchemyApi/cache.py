from os import path, listdir
import json



class AlchemyApiCache:
    def __init__(self, cacheDirectory):
        self.cacheDirectory = cacheDirectory

    def _getCacheBookDir(self, bookName):
        return path.join(self.cacheDirectory, bookName)

    def _getCacheFilePath(self, filename):
        return path.join(self.cacheDirectory, filename)

    def _loadJsonFile(self, filePath):
        fl = open(filePath, 'r')
        jsonObj = json.loads(bookFile.read())
        fl.close()
        return jsonObj

    def listBooks(self):
        return listdir(self.cacheDirectory)

    def listBookFiles(self, bookName):
        return listdir(self._getCacheBookDir(bookName))

    def loadBookSections(self, bookName):
        sections = {}
        for fileName in self.listBookFiles(bookName):
            filePath = _getCacheFilePath(fileName)
            sections[fileName] = _loadJsonFile(filePath)
        return sections

    '''
    below here is mostly untested
    '''
'''
    _cacheFilenameFormat = '{}{}{:0>5d}.json'

    def _getCacheFileName(self, bookName, segmentType, segmentId):
        return path.join(self.getCacheBookDir(bookName), _cacheFilenameFormat).format(segmentType, segmentId)

    def load(self, bookName, segmentType, segmentId):
        bookFile = open(_getCacheFileName(bookName, segmentType, segmentId), 'r')
        jsonObj = json.loads(bookFile.read())
        bookFile.close()
        return json

    def save(self, data, bookName, segmentType, sectionId):
        f = open(_getCacheFileName(bookName, segmentType, segmentId), 'w')
        f.write(json.dumps(AlchemyApiAnalysis.callEntityExtract(section), indent=2))
        f.close()

    def loadSection(self, bookName, sectionId):
        return load(bookName, 'section', sectionId)

    def saveSection(self, bookName, sectionId):
        return save(bookName, 'section', sectionId)

    def loadWyrdSistersSection(self, sectionId):
        loadSection('wyrd_sisters', sectionId)

    def saveWyrdSistersSection(self, sectionId):
        saveSection('wyrd_sisters', sectionId)
'''
