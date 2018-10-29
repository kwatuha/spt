Ext.define('Thread', {
  extend: 'Ext.data.Model',
 
  idProperty: 'threadid',
  fields: [
    'employee_name', 'email_address', 'phone_number', 'town', 'nssf_number', 'pin_number', 'DOB', 'national_ID',
    {name: 'effective_dt', type: 'date', dateFormat: 'timestamp'}
  ]
});

//////


/////
//
var store = Ext.create('Ext.data.Store', {
  model: 'Thread',
  pageSize: 200,
  autoLoad: true,
 
  remoteSort: true,
  sorters: {
    property: 'effective_dt',
    direction: 'DESC'
  },
 
  proxy: {
    type: 'scripttag',
    url: 'http://localhost/formgen/sview/grid/retrievedata.php',//'http://www.sencha.com/forum/remote_topics/index.php',
    extraParams: {
      total: 50000
    },
    reader: {
      type: 'json',
      root: 'results',
      totalProperty: 'total'
    },
    simpleSortMode: true
  }
});
///
Ext.create('Ext.grid.GridPanel', {
  width: 700,
  height: 500,
  renderTo: Ext.getBody(),
  store: store,
 
  verticalScroller: {
    xtype: 'paginggridscroller'
  },
 
  columns: [
    {
      xtype: 'rownumberer',
      width: 40,
      sortable: false
    },
    {
      text: "Employee Name",
      dataIndex: 'employee_name',
      flex: 1
    },
    {
      text: "Email address",
      dataIndex: 'email_address',
      align: 'center',
      width: 70
    },
    {
      text: "Transaction Date",
      dataIndex: 'effective_dt',
      width: 130,
      renderer: Ext.util.Format.dateRenderer('n/j/Y g:i A')
    }
  ],
  height: 350,
        width: 600,
        title: 'Personal Information',
        renderTo: 'personaldata'
});

