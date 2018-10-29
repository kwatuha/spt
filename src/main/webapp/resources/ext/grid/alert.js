Ext.define('AdminAlert', {
    extend: 'Ext.data.Model',
    fields: [
				{name: 'alert_id'},
				{name: 'alert_name'},
				{name: 'is_active'},
				{name: 'success_status'},
				{name: 'alert_description'},
				{name: 'alert_date',type: 'date'}
			],
    idProperty: 'alert_id'
});
//end of funtions
Ext.onReady(function() {
					 
	var winAlert;
    Ext.QuickTips.init();
    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
	var closebtn= Ext.get('close-btn');
    var myalertData =alertArrstr;
	var  buttonaltert = Ext.get('buttongalertgrid');
	/**
     * Custom function used for column renderer
     * @param {Object} val
     */
	

    buttonaltert.on('click', function(){
//removed functions

    // create the data store
    var Alertstore = Ext.create('Ext.data.ArrayStore', {
        model: 'AdminAlert',
        data: myalertData
    });

    // create the Grid
    var grid = Ext.create('Ext.grid.Panel', {
        store: Alertstore,
        stateful: true,
        collapsible: true,
        multiSelect: true,
        stateId: 'storestateGrid',
		
		
        columns: [
            {
                text     : '#',
                
				width    : 20,
                sortable : true,
                dataIndex: 'alert_id'
            },
            {
                text     : 'Name',
                //width    : 75,
				flex     : 1,
                sortable : true,
                //renderer : alert_name,
                dataIndex: 'alert_name'
            },
            {
                text     : 'A active',
                width    : 75,
                sortable : true,
                //renderer : is_active,
                dataIndex: 'is_active'
            },
            {
                text     : 'success status',
                width    : 75,
                sortable : true,
                //renderer : success_status,
                dataIndex: 'success_status'
            }
			,
		
            {
                text     : 'alert_description',
                width    : 85,
                sortable : true,
                //renderer : alert_description,
                dataIndex: 'alert_description'
            },
			
			
			{
                text     : 'Alert Date',
                width    : 85,
                sortable : true,
                //renderer : alert_date,
				renderer : Ext.util.Format.dateRenderer('m/d/Y'),
                dataIndex: 'alert_date'
            },
			
            {
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 50,
                items: [{
                    icon   : '../shared/icons/fam/delete.gif',  // Use a URL in the icon config
                    tooltip: 'Sell stock',
                    handler: function(grid, rowIndex, colIndex) {
                        var rec = Alertstore.getAt(rowIndex);
                        alert("Sell " + rec.get('alert_name'));
                    }
                }, {
                    getClass: function(v, meta, rec) {          // Or return a class from a function
                        if (rec.get('alert_name') < 0) {
                            this.items[1].tooltip = 'Hold stock';
                            return 'alert-col';
                        } else {
                            this.items[1].tooltip = 'Buy stock';
                            return 'buy-col';
                        }
                    },
                    handler: function(grid, rowIndex, colIndex) {
                        var rec = Alertstore.getAt(rowIndex);
                        alert((rec.get('alert_id') < 0 ? "Hold " : "Buy ") + rec.get('alert_name'));
                    }
                }]
            }
        ],
        height: 350,
        width: 900,
        title: 'Alert Datetails Data',
        renderTo: 'alertdata',
        viewConfig: {
            stripeRows: true,
            enableTextSelection: true
        }
    });
	//behaviour
	  buttonaltert.dom.disabled = true;
        if (winAlert.isVisible()) {
            winAlert.hide(this, function() {
                button.dom.disabled = false;
            });
        } else {
            winAlert.show(this, function() {
                button.dom.disabled = false;
            });
        }
	
	 });//end click event
	  });
	  function closeDiv(div,enbtn){
	  var mydiv=document.getElementById(div);
	   var btn=document.getElementById(enbtn);
	  mydiv.innerHTML='';
	   btn.disabled=false;
	  }
	/////////////////////////////////end alert