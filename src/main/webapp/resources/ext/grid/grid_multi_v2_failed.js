
var win;
var store;
var alertArr=new Array();
alertArr['title']='Alert Ubuntu';
alertArr['alertdesc']='wewe ndiwe mungu ';
alertArr['success']='success status'
var columnsArray=new Array();
var mymodel='AdminAlert';
var colmsdefin;
//var colmsdefin=['alert_id', 'alert_name', 'is_active','success_status', 'alert_description','alert_date'];
var datasourceurl='http://localhost/formgen/home/buidgrid.php';

var gridtitled;
var gridDataColumns=[
					 { 
					 text : ' Alert Id ' , 
					 width : 80 , 
					 sortable : true , 
					 dataIndex : 'alert_id' 
					 }, 
					 { text : ' Alert Name ' , 
					 width : 80 ,
					 sortable : true , 
					 dataIndex : 'alert_name' 
					 }, 
					 { text : ' Is Active ' , 
					 width : 80 , 
					 sortable : true , 
					 dataIndex : 'is_active' }, 
					 { text : ' Alert Description ' , 
					 width : 80 , 
					 sortable : true , 
					 dataIndex : 'alert_description' 
					 }, 
					 { text : ' Success Status ' , 
					 width : 80 , 
					 sortable : true ,
					 dataIndex : 'success_status' },
					 { text : ' Alert Date ' , 
					 width : 80 , 
					 sortable : true , 
					 //renderer : Ext.util.Format.dateRenderer('m/d/Y'),
					 dataIndex : 'alert_date' }, 
					 { menuDisabled: true,
					 sortable: false, 
					 xtype: 'actioncolumn',
					 width: 50, 
					 items: [{ icon : '../shared/icons/fam/delete.gif',
					tooltip: 'Sell stock', 
					handler: function(grid, rowIndex, colIndex) 
					{ var rec = store.getAt(rowIndex); 
					alert("Sell " + rec.get('alert_name')); 
					} 
					},
					{ getClass: function(v, meta, rec) { 
					if (rec.get('alert_name') < 0) { 
					this.items[1].tooltip = 'Hold stock'; return 'alert-col'; } 
					else { this.items[1].tooltip = 'Buy stock'; return 'buy-col'; } }, 
					handler: function(grid, rowIndex, colIndex) 
					{ var rec = store.getAt(rowIndex);
					alert((rec.get('alert_id') < 0 ? "Hold " : "Buy ") + rec.get('alert_name')); } }] }];
/*var sgridDataColumns=[
            {
                text     : '#',
                
				width    : 20,
                sortable : true,
                dataIndex :  'alert_id'
            },
            {
                text     : 'Name',
                //width    : 75,
				flex     : 1,
                sortable : true,
                //renderer : alert_name,
                dataIndex :  'alert_name'
            },
            {
                text     : 'A active',
                width    : 75,
                sortable : true,
                //renderer : is_active,
                dataIndex :  'is_active'
            },
            {
                text     : alertArr['success'],
                width    : 75,
                sortable : true,
                //renderer : success_status,
                dataIndex :  'success_status'
            }
			,
		
            {
                text     : alertArr['alertdesc'],
                width    : 85,
                sortable : true,
                //renderer : alert_description,
                dataIndex :  'alert_description'
            },
			
			
			{
                text     : 'Alert Date',
                width    : 85,
                sortable : true,
                //renderer : alert_date,
				renderer : Ext.util.Format.dateRenderer('m/d/Y'),
                dataIndex :  'alert_date'
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
        ];*/
//****************************************************************************************************************
//showTableGrid(colmsdefin,gridDataColumns,datasourceurl,alertArr,win,store);
showTableGrid('admin_alert');
function showTableGrid(tablename){
	
	
	

	
	
	
Ext.onReady(function() {
	var closebtn= Ext.get('close-btn');
	var  buttonaltert = Ext.get('buttongalertgrid');
	
	Ext.Ajax.request({
    url: 'http://localhost/formgen/home/buidgriddesign.php?t='+tablename,
    params: {
        id: 1
    },
    success: function(response){
          var text = response.responseText;
        // process server response here
	      var mdiv=document.getElementById('putitherea');
		
		//echo $gridtitle.'||'.$columnModel.'||'.$gridcolumns;
		  var responseinfo=new Array();
			 responseinfo=text.split('||');
			colmsdefin=responseinfo[1];
			gridDataColumns=responseinfo[2];
			gridtitled=responseinfo[0];
			mdiv.innerHTML=responseinfo[2];
			alert(colmsdefin);
    }
});
    /*Ext.QuickTips.init();
	var closebtn= Ext.get('close-btn');
	
	Ext.define(mymodel, {
    extend: 'Ext.data.Model',
    fields: colmsdefin
});*/
var myalertData;


		
buttonaltert.on('click', function(){

//////////////DYNamic Grid assignment
   
	
//////
 Ext.QuickTips.init();
	Ext.define(mymodel, {
    extend: 'Ext.data.Model',
    fields: colmsdefin
});
	
	///
/////////end of dynamic grid assignment

store = Ext.create('Ext.data.Store', {
    model: mymodel,//'AdminAlert',
    proxy: {
        type: 'ajax',
        url : datasourceurl,
        reader: {
            type: 'json'
        }
    }
});
  store.load();
//removed functions

  
//var data =Store.getRange(store).json;
    // create the Grid
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        stateful: true,
        collapsible: true,
        multiSelect: true,
        stateId: 'stateGrid',
		
		
        columns: gridDataColumns,
        height: 350,
        width: 600,
        title: gridtitled,//alertArr['title'],//'Alert Datetails Data',
        renderTo: 'alertdata',
        viewConfig: {
            stripeRows: true,
            enableTextSelection: true
        }
    });
	
	
	 });//end click event
	  });

}
//////////////////////////////};
	  function closeDiv(div,enbtn){
	  var mydiv=document.getElementById(div);
	   var btn=document.getElementById(enbtn);
	  mydiv.innerHTML='';
	   btn.disabled=false;
	  }
	