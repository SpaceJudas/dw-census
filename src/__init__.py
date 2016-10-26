import os

_REPO_ROOT=os.path.join(os.path.abspath(os.path.dirname(__file__)),'..')
_SRC_ROOT = os.path.abspath(os.path.dirname(__file__))

#http://stackoverflow.com/questions/4519127/setuptools-package-data-folder-location
def get_data(path):
    return os.path.join(_REPO_ROOT, 'data', path)
