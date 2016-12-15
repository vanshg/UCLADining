var _ = require('underscore')
var express = require('express')
var bodyParser = require('body-parser')
var request = require('request')
var util = require('util')
var cheerio = require('cheerio')
var app = express()

let hoursUrl = 'https://secure5.ha.ucla.edu/restauranthours/dining-hall-hours-by-day.cfm?serviceDate=%d%%2F%d%%2F%d'
let overviewUrl = 'http://menu.ha.ucla.edu/foodpro/default.asp?date=%d%%2F%d%%2F%d'

app.set('port', (process.env.PORT || 5000))
app.use(bodyParser.urlencoded({extended: false}))
app.use(bodyParser.json())
app.use(express.static('website'))

/* Parameters:
    Date (optional)
    MM-DD-YYYY
*/
app.get('/overview', function (req, res) {
    var date = getDate(req, res)
    var month = date.getMonth() + 1 //getMonth returns 0 based month
    var day = date.getDate()
    var year = date.getFullYear()
    var url = util.format(overviewUrl, month, day, year)
    request(url, function(error, response, body) {
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
app.get('/hours', function (req, res) {
    var date = getDate(req, res)
    var month = date.getMonth() + 1 //getMonth returns 0 based month
    var day = date.getDate()
    var year = date.getFullYear()
    var url = util.format(hoursUrl, month, day, year)
    request(url, function(error, response, body) {
        if (error) {
            sendError(res, error)
        } else {
            parseHours(res, body)
        }
    })
})

/* Parameters:
    Date
*/
app.get('/swipes', function (req, res) {
    res.send('TODO')
})

// Spin up the server
app.listen(app.get('port'), function() {
    console.log('running on port', app.get('port'))
})

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

function parseHours(res, body) {
    var response = {}
    var $ = cheerio.load(body)
    $('.articleBody table tr').each(function(index, element) {
        if (index < 3) return
        var obj = {}
        var name = ''
        $(this).find('td').each(function(index, element) {
            var text = $(this).text().replace(/\r\n\t/g, '').trim()
            if (index == 0) {
                name = text
            } else {
                if (index == 1) {
                    var key = 'breakfast'
                } else if (index == 2) {
                    var key = 'lunch'
                } else if (index == 3) {
                    var key = 'dinner'
                } else if (index == 4) {
                    var key = 'late_night'
                }
                // TODO: Sometimes, they put full words into the hours, this gets rid of the spacing in those words. But we need this right now to get rid of whacky spacing in the returned String
                obj[key] = text.replace(/ /g, '').trim()
            }
        })
        response[name] = obj
    })
    res.send(response)
}

function sendError(res, error) {
    //TODO: send JSON with the returned error message
    console.log('error')
    res.send(error)
}

function getDate(req, res) {
    var dateText = req.query['date']
    if (dateText) {
        return new Date(dateText)
        // TODO: Catch invalid dateText format and send appropriate error message on incorrect format
    }
    return new Date()
}

// TODO: Have a job that runs every hour that refreshes all the menus
// TODO: Store about a week's worth of menu info


