Ext.onReady(function(){

Ext.QuickTips.init();
//'http://localhost/formgen/sview/grid/jdata.php',
var proxy=new Ext.data.HttpProxy({url:'http://localhost/formgen/sview/grid/jdata.php'});
var reader=new Ext.data.JsonReader({
root: 'results',
totalProperty: 'total',
id: 'employee_id'
},[
	{name: 'employee_id'},
	{name: 'employee_name'},
	{name: 'employee_number'},
	{name: 'phone_number'}        
]);

var store=new Ext.data.Store({
	proxy:proxy,
	reader:reader
});
	
var grid = new Ext.grid.GridPanel({
ds: store,
viewConfig: { forceFit:true },
columns: [
{header: "employee_id", width: 20, sortable: true, dataIndex: 'id'},
{header: "employee_name", width: 20, sortable: true, dataIndex: 'date'},
{header: "employee_number", width: 50, sortable: true, dataIndex: 'title'},
{header: "phone_number", width: 10, sortable: true, dataIndex: 'hidden'}
],     
        width:600,
        height:300,
        frame:false,
        title:'News List',
        renderTo: document.getElementById('alertdata')
    }); 
store.load();
});