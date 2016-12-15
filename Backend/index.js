var _ = require('underscore')
var express = require('express')
var bodyParser = require('body-parser')
var request = require('request')
var util = require('util')
var app = express()

app.set('port', (process.env.PORT || 5000))

// Process application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({extended: false}))

// Process application/json
app.use(bodyParser.json())
// Website
app.use(express.static('website'))

// Index route
app.get('/', function (req, res) {
    res.send('Hello, world!')
})

function sendError(res, error) {
    //TODO: send JSON with the returned error message
    console.log('error')
    res.send(error)
}

function parseOverviewPage(res, body) {
    var response = {}
    response.breakfast = parseMealPeriod(body, 0)
    response.lunch = parseMealPeriod(body, 1)
    response.dinner = parseMealPeriod(body, 2)
    res.send(response)
}

function parseMealPeriod(body, mealNumber) {
    var result = []

    return result
}

/* Parameters:
    Date (optional)
*/
app.get('/overview/', function (req, res) {
    //TODO: Check for date in req
    var url = 'http://menu.ha.ucla.edu/foodpro/default.asp?date=%d%%2F%d%%2F%d'
    var date = new Date()
    var month = date.getMonth() + 1 //getMonth returns 0 based month
    var day = date.getDate()
    var year = date.getFullYear()
    var finalUrl = util.format(url, month, day, year)
    console.log(finalUrl)
    request(finalUrl, function(error, response, body) {
        if (error) {
            sendError(res, error)
        } else {
            parseOverviewPage(res, body)
        }
    })
})

/* Parameters:
    Date
*/
app.get('/hours/', function (req, res) {
    res.send('TODO')
})

/* Parameters:
    Date
*/
app.get('/swipes/', function (req, res) {
    res.send('TODO')
})

// Spin up the server
app.listen(app.get('port'), function() {
    console.log('running on port', app.get('port'))
})




