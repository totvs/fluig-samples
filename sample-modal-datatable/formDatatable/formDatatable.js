function init(){
	$('#btMyModal').click( function() {
		var myModal = FLUIGC.modal({
		    title: 'DataTable',
		    content: '<h1>Usuários</h1>'+'<div id="target"></div>',
		    id: 'fluig-modal',
		    size: 'large',
		    actions: [{
		        'label': 'Save',
		        'bind': 'data-open-modal',
		    },{
		        'label': 'Close',
		        'autoClose': true
		    }]
		
		}, function(err, data) {
		    if(err) {
		        // do error handling
		    } else {
		        // do something with data
		    	var that = this;
		    	var datasetReturned = DatasetFactory.getDataset("colleague", null, null, null);
	    	    if (datasetReturned != null && datasetReturned.values != null && datasetReturned.values.length > 0) {
	    	        var records = datasetReturned.values;
	    	        that.mydata = [];
	    	        for ( var index in records) {
	    	            var record = records[index];
	    	            that.mydata.push({
	    	                id: record.userTenantId,
	    	                name: record.colleagueName,
	    	                email: record.mail
	    	            });
	    	        }
	    	    }
		    	    
	    	    that.myTable = FLUIGC.datatable('#target', {
	    	        dataRequest: that.mydata,
	    	        renderContent: ['id', 'name', 'email'],
	    	        header: [{
	    	            'title': 'Code',
	    	            'dataorder': 'name',
	    	            'size': 'col-md-4'
	    	        }, {
	    	            'title': 'Name',
	    	            'standard': true,
	    	            'size': 'col-md-4'
	    	        }, {
	    	            'title': 'EMAIL',
	    	            'size': 'col-md-4',
	    	            'dataorder': 'ASC'
	    	        }],
	    	        search: {
	    	            enabled: false,
	    	        },
	    	        scroll: {
	    	            target: ".target",
	    	            enabled: true
	    	        },
	    	        actions: {
	    	            enabled: false,
	    	        },
	    	        navButtons: {
	    	            enabled: false,
	    	        },
	    	        draggable: {
	    	            enabled: false
	    	        },
	    	    }, function(err, data) {
	    	        if (err) {
	    	            FLUIGC.toast({
	    	                message: err,
	    	                type: 'danger'
	    	            });
	    	        }
	    	    });
	    	    
	    	    that.myTable.on('fluig.datatable.loadcomplete', function() {
	    	        if (!that.tableData) {
	    	            that.tableData = that.myTable.getData();
	    	        }
	    	    });
		    }
		});

	});
}

init();



