var win;
var responseinfo=new Array();
var colmnsarray=new Array();
var gridcolmnsarray=new Array();
var displaycolums;
var textArray=new Array();
var columnWidthArray=new Array();
var dataIndexArray=new Array();
var stblInfo='';
var gridDataColumns;
var itemid;



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
			
			//stblInfo=responseinfo[3];
			//createJavascriptDataArray(stblInfo,displaycolums);
			//var actualfieldsArr=stblInfo.split('^');
				

			
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
			
			//stblInfo=responseinfo[3];
			//createJavascriptDataArray(stblInfo,displaycolums);
			//var actualfieldsArr=stblInfo.split('^');
				
				var fdiv=document.getElementById('currenttbltb');
				fdiv.innerHTML=stblInfo;
				//alert(stblInfo);

			
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
			
			//stblInfo=responseinfo[3];
			//createJavascriptDataArray(stblInfo,displaycolums);
			//var actualfieldsArr=stblInfo.split('^');
				

			
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
    fields:['alert_id','alert_name','is_active','alert_description','success_status','alert_date']
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
/*var tstco=[{ text : ' Alert Id ' , width : 80 , sortable : true , dataIndex : 'alert_id' }, { text : ' Alert Name ' , width : 80 , sortable : true , dataIndex : 'alert_name' }, { text : ' Is Active ' , width : 80 , sortable : true , dataIndex : 'is_active' }, { text : ' Alert Description ' , width : 80 , sortable : true , dataIndex : 'alert_description' }, { text : ' Success Status ' , width : 80 , sortable : true , dataIndex : 'success_status' }, { text : ' Alert Date ' , width : 80 , sortable : true , dataIndex : 'alert_date' }, { menuDisabled: true, sortable: false, xtype: 'actioncolumn', width: 50, items: [{ icon : '../shared/icons/fam/delete.gif', tooltip: 'Sell stock', handler: function(grid, rowIndex, colIndex) { var rec = store.getAt(rowIndex); alert('Sell ' + rec.get('alert_name')); } }, { getClass: function(v, meta, rec) { if (rec.get('alert_name') < 0) { this.items[1].tooltip = 'Hold stock'; return 'alert-col'; } else { this.items[1].tooltip = 'Buy stock'; return 'buy-col'; } }, handler: function(grid, rowIndex, colIndex) { var rec = store.getAt(rowIndex); alert((rec.get('alert_id') < 0 ? 'Hold ' : 'Buy ') + rec.get('alert_name')); } }] } ] ;*/
var txtnm=' lablel inofr  ';

  store.load();
    var grid = Ext.create('Ext.grid.Panel', {
						  
        store: store,
        stateful: true,
        collapsible: true,
        multiSelect: true,
        stateId: 'stateGrid',//stblInfo
		columns:stblInfo ,
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
	