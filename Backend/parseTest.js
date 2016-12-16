var tabletojson = require('tabletojson');
var fs = require('fs');
var util = require('util');

var res = 
{
	"Breakfast" : 
	[
		{
			"name" : "Bruin Plate",
			"menu" : []
		},
		{
			"name" : "De Neve Dining",
			"menu" : []
		}
	],
	"Lunch" : 
	[
		{
			"name" : "Covel Dining",
			"menu" : []
		},
		{
			"name" : "Bruin Plate",
			"menu" : []
		},
		{
			"name" : "De Neve Dining",
			"menu" : []
		},
		{
			"name" : "FEAST at Rieber",
			"menu" : []
		}
	],
	"Dinner" : 
	[
		{
			"name" : "Covel Dining",
			"menu" : []
		},
		{
			"name" : "Bruin Plate",
			"menu" : []
		},
		{
			"name" : "De Neve Dining",
			"menu" : []
		},
		{
			"name" : "FEAST at Rieber",
			"menu" : []
		}
	]
};

var html = fs.readFileSync("test.html");
var tablesAsJson = tabletojson.convert(html);

//console.log(tablesAsJson);

for (var i = 1; i <= 5; i++)
{
	var mealname;
	var nameMap;

	if (i == 1)
	{
		mealname = "Breakfast";
		nameMap = 
		{
			"Bruin Plate" : 0,
			"De Neve Dining" : 1
		}
	}
	else 
	{
		if (i == 2 || i == 3)
			mealname = "Lunch";
		else if (i == 4 || i == 5)
			mealname = "Dinner";
		nameMap = 
		{
			"Covel Dining" : 0,
			"Bruin Plate" : 1,
			"De Neve Dining" : 2,
			"FEAST at Rieber" : 3
		}
	}

	var table = tablesAsJson[i];
	var offset = 0;

	var name1;
	var name2;

	if (i == 3 || i == 5)
	{
		offset = 1;
		name1 = table[0]['0'];
		name2 = table[0]['1'];
	}
	else
	{
		name1 = table[1]['0'];
		name2 = table[1]['1'];
	}
	for (var j = (3-offset); j < table.length; j++)
	{
		var obj = {};
		var obj1 = 
		{
			"section_name" : "",
			"items" : []
		};

		var obj2 = 
		{
			"section_name" : "",
			"items" : []
		};

		var section1 = table[j]['0'];
		var arr1 = section1.replace(/\t/g, '').split('\n');
		obj1.section_name = arr1[0];
		arr1.shift();
		obj1.items = arr1;
		res[mealname][nameMap[name1]].menu.push(obj1);
		
		var section2 = table[j]['1'];
		var arr2 = section2.replace(/\t/g, '').split('\n');
		obj2.section_name = arr2[0];
		arr2.shift();
		obj2.items = arr2;
		res[mealname][nameMap[name2]].menu.push(obj2);
	}
}

console.log(util.inspect(res, false, null));