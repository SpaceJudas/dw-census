#!/usr/bin/python
'''
Requirements:
 - pip install --upgrade watson-developer-cloud (http://www.ibm.com/watson/developercloud/alchemy-language/api/v1/?python#introduction)

'''
import Main
import config

import json
from watson_developer_cloud import AlchemyLanguageV1

alchemy_language = AlchemyLanguageV1(api_key=config.alchemyApiKey)

def extractQuotes(text):  
  print(json.dumps(
    alchemy_language.combined(
      url='https://www.ibm.com/us-en/',
      extract='entities,keywords',
      sentiment=1,
      max_items=1),
    indent=2))
