module.exports = (sequelize, Sequelize) => {
	const User = sequelize.define('user', {
	  firstname: {
		type: Sequelize.STRING
	  },
	  lastname: {
		type: Sequelize.STRING
	  },
	  age: {
		  type: Sequelize.INTEGER
	  },
	  email:{
	  	  type:Sequelize.STRING
	  },
	  password:{
	  	  type:Sequelize.STRING
	  }
	});
	
	return User;
}