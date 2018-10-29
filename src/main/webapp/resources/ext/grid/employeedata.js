Ext.onReady(function(){
	  Ext.define('UsgsList', {
	    extend: 'Ext.data.Model',
	    fields: [
	       {name: 'employee_id',      type: 'int'},
	       {name: 'employee_name',    type: 'string'},
	       {name: 'employee_number',  type: 'string'},
	       {name: 'phone_number',     type: 'string'},
	       {name: 'effective_dt',    type: 'date'},
	       {name: 'nssf_number',      type: 'string'},
	       {name: 'email_address',    type: 'string'}
	    ],
	    idProperty: 'employee_id'
	});
	 Ext.Ajax.request({
    url: 'http://localhost/formgen/sview/grid/jdata.php',
    params: {
        id: 1
    },
    success: function(response){
        var text = response.responseText;
        // process server response here
		alert(text);
		var mdiv=document.getElementById('putitherea');
		mdiv.innerHTML=text;
    }
});
	var store = Ext.create('Ext.data.Store', {
	    id: 'store',
	    model: 'UsgsList',
	    proxy: {
	       type: 'aja',
	       url: 'http://localhost/formgen/sview/grid/jdata.php',
	    extraParams: {
 q: ' select employee_id, employee_name, employee_number,phone_number, effective_dt, nssf_number, email_address from pim_employee',
	       format: 'json'
	   },
	   reader: {
	      root: 'query.results.item',
	   }
	 }
	});
	 
	function renderTitle(value, p, record) {
	   return Ext.String.format('<a href="{1}" target="_blank">{0}</a>',
	   value,
	   record.data.link
	   );
	}
	 
	var grid = Ext.create('Ext.grid.Panel', {
	   width: 700,
	   height: 500,
   title: 'USGS - M2.5+',
	   store: store,
	   loadMask: true,
	   disableSelection: true,
	   invalidateScrollerOnRefresh: false,
	   viewConfig: {
	     trackOver: false
	   },
	   // grid columns
	   columns:[{
	      xtype: 'rownumberer',
	      width: 50,
	      sortable: false
	   },{
	      id: 'employee_name',
	      text: "Title employee_name",
	      dataIndex: 'employee_name',
	      flex: 1,
	      renderer: renderTitle,
	      sortable: false
	   },{
	      id: 'effective_dt',
	      text: "Published Date effective_dt",
	      dataIndex: 'effective_dt',
	      width: 130,
	      renderer: Ext.util.Format.dateRenderer('n/j/Y g:i A'),
	      sortable: true
	   }],
	   renderTo: Ext.getBody()
	});
	 
	// trigger the data store load
	store.load();
	});
