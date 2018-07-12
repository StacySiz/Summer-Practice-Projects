const db = require('../config/db.config.js');
const User = db.users;
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
var config = require('../config/env');
const jwtInfo = require('../config/jwtConf');
const express = require('express');
const app = express();


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
    User.findOne({where: {email:req.body.email}}).then(user => {
        if (!user) return res.status(404).send('User not found');
        const validPassword = bcrypt.compareSync(req.body.password,user.password);
        if (!validPassword) return res.status(401).send({ auth: false, token: null });
        const token = jwt.sign({id: user.id},jwtInfo.JWT_SECRET,{expiresIn:jwtInfo.tokenLife});
        console.log(user.email);
        //res.status(200).send({ auth: true, token: token });
        res.redirect('/weather');
    })

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

