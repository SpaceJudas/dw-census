var fs = require('fs');
var config = require('config');
var Calais = require('calais-entity-extractor').Calais;
var split = require('split');

//getBookSize();
//doStuff();
//doSplitForCalais("hey bob");
readBook(function(err, text) {
  getNonWordCounts(text);
  //forEachChunk(text, config.get('api.calais.max_size'), )
});

//callback is form of err, data
function readBook(callback) {
  fs.readFile("book.txt", "utf-8", callback);
}

//callback is function(element)
function forEachChunk(text, maxSize, callback) {
  var strSize = Buffer.byteLength(text, 'utf-8');
  var chunkCount = (strSize/maxSize) + 1; //add 1 for safety

  text.length()
}

function getNonWordCounts(text) {
  var re = /\W/g;
  var freq = {};
  var match;

  do {
      match = re.exec(text);
      if (match) {
          //console.log(match[0], match.index);

          var char = match[0];
          var index = match.index;
          if (!freq[char])
            freq[char] = [];
          freq[char].push(index);
      }
  } while (match);

  console.log(freq)
  Object.keys(freq).forEach(function(element) {
    
  });
}

function getBookSize() {
  fs.stat('book.txt', function(error, stat) {
    console.log(stat.size, 'bytes')
  })
  fs.readFile('book.txt', 'utf-8', function(error, text) {
    console.log(Buffer.byteLength(text, 'utf-8'));
  })
}

function doSplitForCalais(text) {
  fs.stat("book.txt", function(error, stat) {
    console.dir(stat);
    var file_size = stat.size;
    var max_size = config.get('api.calais.max_size');
  })


}




function doStuff() {
  readBook(function(err, text) {
    var calais = new Calais(config.get('api.calais'), {'cleanResult': false})
    calais.set('content', text)
    calais.extractFromText(function(result, err) {
      if (err) {
        console.log('ahsit' + err);
        return;
      }

      //So far so good
      var util = require('util');

      //The results have two fields: 'entities' and 'tags'
      //'entities' contains a list of the detected entities, and gives basic info & confidence
      console.log('Entities: ' + util.inspect(result.entities, false, null));

      //'tags' are a list of string tags (the "socialTags" from Calais).
      console.log('\nTags: ' + util.inspect(result.tags, false, null));
    })

  })



}
