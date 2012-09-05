
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , http = require('http');

var querystring = require('querystring');

var app = express();

app.configure(function(){
  app.set('port', process.env.PORT || 3000);
  app.set('views', __dirname + '/views');
  app.set('view engine', 'ejs');
  app.use(express.favicon());
  app.use(express.logger('dev'));
  app.use(express.bodyParser());
  app.use(express.methodOverride());

  app.use(express.static(__dirname + '/public'));
  //put it below static
  app.use(app.router);
	  
	//Register ejs as .html. If we did
	//not call this, we would need to
	//name our views foo.ejs instead
	//of foo.html. The __express method
	//is simply a function that engines
	//use to hook into the Express view
	//system by default, so if we want
	//to change "foo.ejs" to "foo.html"
	//we simply pass _any_ function, in this
	//case `ejs.__express`.
	
	app.engine('.html', require('ejs').__express);
  
});

app.configure('development', function(){
  app.use(express.errorHandler());
});

app.get('/', function(req, res) {
	
	var body = '<html>' + '<head>' + '<meta http-equiv="Content-Type" '
	+ 'content="text/html; charset=UTF-8" />' + '</head>' + '<body>'
	+ '<form action="/upload" enctype="multipart/form-data" '
	+ 'method="post">' + '<input type="file" name="upload"><br />'
	+ '<input type="submit" value="Upload file" />' + '</form>'
	+ '</body>' + '</html>';

	response.writeHead(200, {
	"Content-Type" : "text/html"
	});
	response.write(body);
	response.end();
	
});

app.post('/form', function(req, res) {

			console.log("[200] " + req.method + " to " + req.url);

		
				res.writeHead(200, "OK", {'Content-Type': 'text/html'});
				res.write('<html><head><title>Post data</title></head><body>');
				res.write('<style>th, td {text-align:left; padding:5px; color:black}\n');
				res.write('th {background-color:grey; color:white; min-width:10em}\n');
				res.write('td {background-color:lightgrey}\n');
				res.write('caption {font-weight:bold}</style>');
				res.write('<table border="1"><caption>Form Data</caption>');
				res.write('<tr><th>Name</th><th>Value</th>');
				console.log(req.body);
				var dBody = querystring.parse(req.body);
				console.log('dbody' + dBody);
				for (var prop in dBody) {
				res.write("<tr><td>" + prop + "</td><td>" + dBody[prop] + "</td></tr>");
			}
			res.write('</table></body></html>');
			res.end();
			



	
});

app.get("/form", function(req,res) {
	console.log("[405] " + req.method + " to " + req.url);
	res.writeHead(405, "Method not supported", {'Content-Type': 'text/html'});
	res.end('<html><head><title>405 - Method not supported</title></head><body>' +
	'<h1>Method not supported.</h1></body></html>');	
});

//Dummy users
var users = [
    { name: 'tobi', email: 'tobi@learnboost.com' }
  , { name: 'loki', email: 'loki@learnboost.com' }
  , { name: 'jane', email: 'jane@learnboost.com' }
];

app.get('/index', function(req, res){
  res.render('index.html', { users: users });
});

http.createServer(app).listen(app.get('port'), function(){
  console.log("Express server listening on port " + app.get('port'));
});
