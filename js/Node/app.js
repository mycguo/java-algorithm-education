
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , http = require('http');

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
