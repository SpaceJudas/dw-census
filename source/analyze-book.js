var fs = require('fs');
var config = require('config');
//var Calais = require('calais');

//doStuff();

//callback is form of err, data
function readBook(callback) {
  fs.readFile("book.txt", "utf-8", callback);
}

function callCalais(err, data) {
  /*calais.set('content', data)
  var apiConfig = config.get('Customer.dbConfig');
  calais.set('content', 'The Federal Reserve is the enemy of Ron Paul.')*/
}

function doStuff() {
  /*var apiKey = config.get('api.calais');
  var calais = new Calais(apiKey);
  calais.fetch(function (result) {
    console.dir(result);
  })*/
}
