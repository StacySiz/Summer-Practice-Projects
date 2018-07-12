var express = require('express');
var app = express();
var bodyParser = require('body-parser');
const request = require('request');
const apiKey = 'a43d2d5006ccc3c8aaee754a5fef4de5';

app.set('view engine','ejs');
app.use(express.static('public'));
app.use(bodyParser.urlencoded({extended : true}));
app.use(bodyParser.json())

const db = require('./app/config/db.config.js');

db.sequelize.sync({force: false}).then(() => {
    console.log('Drop and Resync with { force: true }');
});

// force: true will drop the table if it already exists
require('./app/route/weatherRoute.js')(app);
require('./app/route/userRoute.js')(app);

var server = app.listen(8081, function () {
 
  var host = server.address().address
  var port = server.address().port

    module.exports = app;


    console.log("App listening at http://%s:%s", host, port)
});