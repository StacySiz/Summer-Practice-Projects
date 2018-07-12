const db = require('../config/db.config.js');
const User = db.users;
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
var config = require('../config/env');
const jwtInfo = require('../config/jwtConf');
const express = require('express');
const app = express();
const pg = require('pg');
const connectionString = process.env.DATABASE_URL || 'postgresql://localhost:5432/weather_app';
const client = new pg.Client(connectionString);
client.connect();

exports.create = (req, res) => {
    const hashedPassword = bcrypt.hashSync(req.body.password,8);
	User.create({
	  firstname: req.body.firstname,
	  lastname: req.body.lastname,
	  age: req.body.age,
	  email: req.body.email,
      password: hashedPassword
	}).then(user => {
	    const token = jwt.sign({id: user.id},jwtInfo.JWT_SECRET,{expiresIn:jwtInfo.tokenLife})//exp in 24 hours
		//res.send(user);
       // res.status(200).send({auth:true,token:token})
        res.redirect("/weather");
	});
};

exports.findOne = (req,res) => {
    var tag = req.query.q;
    res.json(tag)
    var search = client.query('select * from users where email Ilike $1', [`%${req.query.q}%`], function(err, result){
            res.json(result);
            console.log(res.json(result));
        }
    );

    // User.findOne({where: {email: req.body.email}},function (err,user) {
    //     console.log(req.body.email);
    //     if (err) return res.status(500).send('Error on server');
    //     if (!user) return res.status(404).send('User not found');
    //     console.log("step 1");
    //    const validPassword = bcrypt.compareSync(req.body.password,user.password);
    //    if (!validPassword) return res.status(401).send({ auth: false, token: null });
    //
    //     const token = jwt.sign({id: user.id},jwtInfo.JWT_SECRET,{expiresIn:jwtInfo.tokenLife});
    //     res.status(200).send({ auth: true, token: token });
    //
    //    // res.render('index',{weather: null, error: null,user:token.firstname});
    //
    // })
        //.then(token =>{
       // res.render('index',{weather: null, error: null,user:token.firstname});
        //res.status(200).send({ auth: true, token: token });
    //})
};
 
exports.findAll = (req, res) => {
	User.findAll().then(user => {
	  res.send(user);
	});
};

exports.findById = (req, res) => {
	User.findById(req.params.userId).then(user => {
		res.send(user);
	})
};
 
exports.update = (req, res) => {
	const id = req.params.userId;
	User.update( { firstname: req.body.firstname, lastname: req.body.lastname, age: req.body.age,email:req.body.email,password: req.body.password },
					 { where: {id: req.params.userId} }
				   ).then(() => {
					 res.status(200).send("updated successfully a user with id = " + id);
				   });
};
 
exports.delete = (req, res) => {
	const id = req.params.userId;
	User.destroy({
	  where: { id: id }
	}).then(() => {
	  res.status(200).send('deleted successfully a user with id = ' + id);
	});
};

