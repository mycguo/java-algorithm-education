

var mongoose = require('mongoose'), Schema = mongoose.Schema;
var UserSchema = new Schema({
	login: String,
	password: String,
	role: String
	
});

//mongoose.connect('mongodb://localhost/mydatabase');
mongoose.model('User', UserSchema);

var User = mongoose.model("User");
