var express = require('express');
var app = express();
var bodyParser = require('body-parser');
const request = require('request');
const apiKey = 'a43d2d5006ccc3c8aaee754a5fef4de5';
const users = require('../controller/userController');

module.exports = function(app) {

    app.set('view engine', 'ejs');
    app.use(express.static('public'));
    app.use(bodyParser.urlencoded({extended: true}));

    app.use(bodyParser.json())

    app.post('/weather', function (req, res) {
        let city = req.body.city;
        let url = `http://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=${apiKey}`;
        request(url, function (err, response, body) {
            if (err) {
                res.render('index', {weather: null, error: 'Something went wrong , please try again1',user:null});
            } else {
                let weather = JSON.parse(body)
                if (weather.main == undefined) {
                    res.render('index', {weather: null, error: 'Something went wrong , please try again2',user:null});
                } else {
                    let weatherResult = `There is ${weather.main.temp} degrees in ${weather.name}!`;
                    //let weatherResult2 =  ${weather.main.temp} degrees in ${weather.name}!`;
                    console.log(weatherResult);
                    res.send(weather);
                    // if (!user) {
                    //     res.render('index', {weather: weather, error: null, user: user});
                    // }
                    // else {
                        res.render('index', {weather: weather, error: null, user: null});
                    //}
                }
            }
        });
    });


    app.get('/weather', function (req, res) {
        res.render('index', {weather: null, error: null,user:null})
    });
}
