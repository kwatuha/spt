var win;
var responseinfo=new Array();
var colmnsarray=new Array();
var gridcolmnsarray=new Array();
var displaycolums;
var textArray=new Array();
var columnWidthArray=new Array();
var dataIndexArray=new Array();
var stblInfo;
var gridDataColumns;
var itemid;
function createJavascriptDataArray(stblInfo,displaycolums){
/*var actualfieldsArr=stblInfo.split('^');
		for(i=0;i<displaycolums;i++){
		var indcolumArr=actualfieldsArr[i].split('|');
		textArray[i]=indcolumArr[0];
		columnWidthArray[i]=indcolumArr[1];
		dataIndexArray[i]=indcolumArr[2];
		
		}
*/		
		
 
}
	
///////////////////////	
function loadGridData(tablename){
/*Ext.Ajax.request({
    url: 'http://localhost/formgen/home/buidgriddesign.php?t='+tablename,
    params: {
        id: 1
    },
    success: function(response){
          var text = response.responseText;
            responseinfo=text.split('||');
			colmsdefin=responseinfo[1];
			//alert(responseinfo[1]);
			//['alert_id','alert_name','is_active','alert_description','success_status','alert_date'];//;
			
			colmnsarray=colmsdefin.split(',');
			gridtitled=responseinfo[0];
			displaycolums=responseinfo[2];
			
			stblInfo=responseinfo[3];
			//createJavascriptDataArray(stblInfo,displaycolums);
			var actualfieldsArr=stblInfo.split('^');
				for(i=0;i<displaycolums;i++){
				var indcolumArr=actualfieldsArr[i].split('|');
				textArray[i]=indcolumArr[0];
				columnWidthArray[i]=indcolumArr[1];
				dataIndexArray[i]=indcolumArr[2];
				
				}

			
    }
});*/
}


/*select dynamic grid functionality 
*
*
**************************************************************************************/



//end of dynamic grid

function loadArrayInfo(){
	tablename='admin_alert';
Ext.Ajax.request({
    url: 'http://localhost/formgen/home/buidgriddesign.php?t='+tablename,
    params: {
        id: 1
    },
    success: function(response){
          var text = response.responseText;
            responseinfo=text.split('||');
			colmsdefin=responseinfo[1];
			//alert(responseinfo[1]);
			//['alert_id','alert_name','is_active','alert_description','success_status','alert_date'];//;
			
			colmnsarray=colmsdefin.split(',');
			gridtitled=responseinfo[0];
			displaycolums=responseinfo[2];
			
			stblInfo=responseinfo[3];
			//createJavascriptDataArray(stblInfo,displaycolums);
			var actualfieldsArr=stblInfo.split('^');
				for(i=0;i<displaycolums;i++){
				var indcolumArr=actualfieldsArr[i].split('|');
				textArray[i]=indcolumArr[0];
				columnWidthArray[i]=indcolumArr[1];
				dataIndexArray[i]=indcolumArr[2];
				
				
				}

			
    }
});

return dataIndexArray;

	}
	
	function loadtextArrayArrayInfo(){
	tablename='admin_alert';
Ext.Ajax.request({
    url: 'http://localhost/formgen/home/buidgriddesign.php?t='+tablename,
    params: {
        id: 1
    },
    success: function(response){
          var text = response.responseText;
            responseinfo=text.split('||');
			colmsdefin=responseinfo[1];
			//alert(responseinfo[1]);
			//['alert_id','alert_name','is_active','alert_description','success_status','alert_date'];//;
			
			colmnsarray=colmsdefin.split(',');
			gridtitled=responseinfo[0];
			displaycolums=responseinfo[2];
			
			stblInfo=responseinfo[3];
			//createJavascriptDataArray(stblInfo,displaycolums);
			var actualfieldsArr=stblInfo.split('^');
				for(i=0;i<displaycolums;i++){
				var indcolumArr=actualfieldsArr[i].split('|');
				textArray[i]=indcolumArr[0];
				columnWidthArray[i]=indcolumArr[1];
				dataIndexArray[i]=indcolumArr[2];
				
				
				}

			
    }
});

return textArray;

	}
function loadcolumnWidthArrayInfo(){
tablename='admin_alert';
Ext.Ajax.request({
    url: 'http://localhost/formgen/home/buidgriddesign.php?t='+tablename,
    params: {
        id: 1
    },
    success: function(response){
          var text = response.responseText;
            responseinfo=text.split('||');
			colmsdefin=responseinfo[1];
			//alert(responseinfo[1]);
			//['alert_id','alert_name','is_active','alert_description','success_status','alert_date'];//;
			
			colmnsarray=colmsdefin.split(',');
			gridtitled=responseinfo[0];
			displaycolums=responseinfo[2];
			
			stblInfo=responseinfo[3];
			//createJavascriptDataArray(stblInfo,displaycolums);
			var actualfieldsArr=stblInfo.split('^');
				for(i=0;i<displaycolums;i++){
				var indcolumArr=actualfieldsArr[i].split('|');
				textArray[i]=indcolumArr[0];
				columnWidthArray[i]=indcolumArr[1];
				dataIndexArray[i]=indcolumArr[2];
				
				
				}

			
    }
});

return columnWidthArray;

	}
	
function showgriddetails() {
	

///////////////////////////////
Ext.QuickTips.init();
var closebtn= Ext.get('close-btn');
	var  buttonaltert = Ext.get('buttongalertgrid');	
var myalertData;
var tablename='admin_alert';
	Ext.define('AdminAlert', {
    extend: 'Ext.data.Model',
    fields:colmnsarray//['alert_id','alert_name','is_active','alert_description','success_status','alert_date']
});
//
var store = Ext.create('Ext.data.Store', {
    model: 'AdminAlert',
    proxy: {
        type: 'ajax',
        url : 'http://localhost/formgen/home/buidgrid.php',
        reader: {
            type: 'json'
        }
    }
});
//alert(textArray.length);
//alert(dataIndexArray[1]);//='Kwatuha in';
var txtnm=' lablel inofr  ';

  store.load();
    var grid = Ext.create('Ext.grid.Panel', {
						  
        store: store,
        stateful: true,
        collapsible: true,
        multiSelect: true,
        stateId: 'stateGrid',
		columns:[{
				 
				 text     : txtnm,
                
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
	
	
	//}// });//end click event
	 } //});
	  function closeDiv(div,enbtn){
	  var mydiv=document.getElementById(div);
	   var btn=document.getElementById(enbtn);
	  mydiv.innerHTML='';
	   btn.disabled=false;
	  }
	