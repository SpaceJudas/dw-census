
#DIRECTORIES
from os import path
_REPO_ROOT=path.abspath(path.join(path.dirname(__file__), '..'))
_DATA_ROOT=path.join(_REPO_ROOT, 'data')
_ALCHEMYAPI_CACHE_ROOT=path.join(_DATA_ROOT, 'alchemy_cache')
#_ALCHEMYAPI_CACHE_ROOT='/Users/spencerewall/Developer/discworld-census/data/alchemy_cache'

#load cache
from alchemyApi.cache import AlchemyApiCache
alch_cache=AlchemyApiCache(_ALCHEMYAPI_CACHE_ROOT)
#print(_ALCHEMYAPI_CACHE_ROOT) => /Users/spencerewall/Developer/discworld-census/data/alchemy_cache


def alchemyCache_buildPeopleQuoteDict(bookName):
    peopleQuotes = {}
    #entities = set()
    for item in alch_cache.loadBookSections(bookName).items():
        secName = item[0]
        sectionEntities = item[1]['entities']
        peopleEntities = list(filter(lambda e: e['type']=='Person', sectionEntities))
        #peopleNames = map(lambda e: e['text'], peopleEntities)

        for person in peopleEntities:
            personName = person['text']
            if not personName in peopleQuotes: #add an empty list to the dict
                peopleQuotes[personName] = []

            if 'quotations' in person: #because some people just don't say anything
                peopleQuotes[personName].extend(person['quotations'])
        #entities.update(peopleNames)

    print(peopleQuotes)


alchemyCache_buildPeopleQuoteDict('wyrd_sisters')
