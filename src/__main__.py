
#DIRECTORIES
from os import path
_REPO_ROOT=path.abspath(path.join(path.dirname(__file__), '..'))
_DATA_ROOT=path.join(_REPO_ROOT, 'data')
_ALCHEMYAPI_CACHE_ROOT=path.join(_DATA_ROOT, 'alchemy_cache')

#load cache
from alchemyApi.cache import AlchemyApiCache
alch_cache=AlchemyApiCache(_ALCHEMYAPI_CACHE_ROOT)

print(alch_cache.listBooks())
