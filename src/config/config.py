#!/usr/bin/python
from os import path

import confidentialConfig

alchemyUrl = 'https://gateway-a.watsonplatform.net/calls'
alchemyApiKey = confidentialConfig.alchemyApiKey

#Gets the path of this directory, combines it with .. to get the path relative to root, then gets teh real
path_projectRoot = path.realpath(path.join(path.dirname(path.realpath(__file__)), '../..'))
path_dataDirectory = path.realpath(path.join(path_projectRoot, 'data'))
#for relative path, check os.path.relpath(subdir2, subdir1)
