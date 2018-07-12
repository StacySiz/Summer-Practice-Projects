const pg = require('pg');
const connectionString = process.env.DATABASE_URL || 'postgresql://localhost:5432/weather_app';
const client = new pg.Client(connectionString);
client.connect();
module.exports = function(app) {

    const users = require('../controller/userController.js');

    app.get('/',function(req,res){
        res.redirect('/users');
    })
    // Create a new user
    app.post('/registration',users.create);

    app.get('/registration', function (req, res) {
        res.render('registration')
    });

    app.get('/signIn',function (req,res) {
        res.render('signIn');
    });

    // app.post('/signIn',users.findOne);

    router.post('signIn', (req, res, next) => {
        const results = [];
        // Get a Postgres client from the connection pool
        pg.connect(connectionString, (err, client, done) => {
            // Handle connection errors
            if(err) {
                done();
                console.log(err);
                return res.status(500).json({success: false, data: err});
            }
            // SQL Query > Select Data
            const query = client.query('select * from users where email Ilike $1\', [`%${req.query.q}%`]');
            // Stream results back one row at a time
            query.on('row', (row) => {
                results.push(row);
            });
            // After all data is returned, close connection and return results
            query.on('end', () => {
                done();
                return res.json(results);
            });
        });
    });

    // Retrieve all Customer
    app.get('/users', users.findAll);
 
    // Retrieve a single Customer by Id
    app.get('/users/:userId', users.findById);
 
    // Update a Customer with Id
    app.put('/users/:userId', users.update);
 
    // Delete a Customer with Id
    app.delete('/users/:userId', users.delete);
}