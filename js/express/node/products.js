var mongoose = require('mongoose'), Schema = mongoose.Schema;

var ProductSchema = new Schema({
	name: String,
	description: String,
	price: String
	
});

//mongoose.connect('mongodb://localhost/mydatabase');
mongoose.model('Product', ProductSchema);

