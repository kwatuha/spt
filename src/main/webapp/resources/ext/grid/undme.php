/*gridDataColumns,*[
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
                text     : 'alert  description',
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
                        var rec = store.getAt(rowIndex);
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
                        var rec = store.getAt(rowIndex);
                        alert((rec.get('alert_id') < 0 ? "Hold " : "Buy ") + rec.get('alert_name'));
                    }
                }]
            }
        ],*/