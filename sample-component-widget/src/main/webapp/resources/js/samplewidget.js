var SampleWidget = SuperWidget.extend({
	
	init: function() {
		//code
	},

	bindings: {
		local: {
			'do-something': ['click_someFunc']
		}
	},

	someFunc: function() {
		var d = new Date();		
	    var curr_date = d.getDate();
	    var curr_month = d.getMonth() + 1;
	    var curr_year = d.getFullYear();
		alert("Hoje é : " + curr_date + "/" + curr_month + "/" + curr_year);
	}
})