#!/usr/bin/python
'''
Requirements:
 - pip install --upgrade watson-developer-cloud (http://www.ibm.com/watson/developercloud/alchemy-language/api/v1/?python#introduction)

 example:
 import AlchemyApiClient
 AlchemyApiClient.callEntityExtract("wooooooo im a string")
'''
import config

import json
from watson_developer_cloud import AlchemyLanguageV1
import requests

alchemy_language = AlchemyLanguageV1(api_key=config.alchemyApiKey)


def callCombined(text):
  print(json.dumps(
      alchemy_language.combined(
        text=text,
        extract='entities,keywords',
        sentiment=1,
        max_items=1),
      indent=2))

'''
4000 calls/day
106 segments
4088 lines cleaned
487086 bytes
limited to 50km
'''
# AlchemyApiAnalysis.callEntityExtract


def callEntityExtract(text):
  return alchemy_language.entities(text=text, linked_data=True, coreference=True, quotations=True, sentiment=True, emotion=True, max_items=50)
# def getQuotations(text):
#  alchemy_language.


def extractUrl():
  return alchemy_language.entities(url="http://www-03.ibm.com/press/us/en/pressrelease/49384.wss", linked_data=True, coreference=True,quotations=True, max_items=50)
