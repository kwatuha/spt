
var PresidentsDataStore;         // this will be our datastore
var PresidentsColumnModel;       // this will be our columnmodel
var PresidentListingEditorGrid;
var PresidentListingWindow;

Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*'
]);
 
Ext.onReady(function(){
  Ext.QuickTips.init();
  //float
  PresidentsDataStore = new Ext.create('Ext.data.Store'({
    id: 'IDemployee',
    proxy: {
    type: 'json',
    url: 'http://localhost/formgen/sview/grid/retrievedata.php',
    extraParams: {
      total: 50000
    },
      baseParams:{task: "LISTING"}, // this parameter asks for listing
        reader: new Ext.data.JsonReader({   
        root: 'results',
        totalProperty: 'total',
		id: 'id'
      },[ 
        {name: 'IDemployee', type: 'int', mapping: 'employee_id'},
        {name: 'EmployeeName', type: 'string', mapping: 'employee_name'},
        {name: 'EmailAddress', type: 'string', mapping: 'email_address'},
        {name: 'PhoneNumber', type: 'string', mapping: 'phone_number'},
        {name: 'Town', type: 'string', mapping: 'town'},
        {name: 'DateOfBirth', type: 'date', mapping: 'DOB'},
        {name: 'EffectiveDate', type: 'date', mapping: 'effective_dt'},
        {name: 'NssfNumber', type: 'string', mapping: 'nssf_number'}
      ]),
      sortInfo:{field: 'IDemployee', direction: "ASC"}
	});
    });


///////
var PresidentsColumnModel = Ext.create('Ext.grid.Panel', {
        store: PresidentsDataStore,
		stateful: true,
        collapsible: true,
        multiSelect: true,
        stateId: 'stateGrid',
		
    columns:[{
        text: '#',
        readOnly: true,
        dataIndex: 'IDemployee', // this is where the mapped name is important!
        width: 50,
        hidden: false
      },{
        text: 'Employee Name',
        dataIndex: 'EmployeeName',
        width: 150,
        editor: new Ext.form.TextField({  // rules about editing
            //allowBlank: false,
            maxLength: 500
            //maskRe: /([a-zA-Z0-9\s]+)$/   // alphanumeric + spaces allowed
          })
      },
	  {
        text: 'Email address',
        dataIndex: 'EmailAddress',
        width: 150,
        editor: new Ext.form.TextField({
          //allowBlank: false,
          maxLength: 50
         // maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        text: 'Phone Number',
        readOnly: true,
        dataIndex: 'PhoneNumber',
        width: 50,
        hidden: true                      // we don't necessarily want to see this...
      },{
        text: 'Home Town',
        dataIndex: 'Town',
        width: 150,
        readOnly: true
      },
	  {
        text: 'Date Of Birth',
        dataIndex: 'DateOfBirth',
        width: 150,
        readOnly: true
      },
	  {
        text: 'Nssf number',
        dataIndex: 'NssfNumber',
        width: 150,
        readOnly: true
      },
	  {
        text: 'Transaction date',
        dataIndex: 'EffectiveDate',
        width: 150,
        readOnly: true
      }],
	  height: 350,
        width: 600,
        title: 'Array Grid',
        renderTo: 'personaldata'/*,
        viewConfig: {
            stripeRows: true,
            enableTextSelection: true
			}*/
    });
});

	
	/*{
        header: "Date Of Birth",
        dataIndex: 'DateOfBirth',
        width: 150,
        renderer: function(v){ return '$ ' + v; },   
                           // we tell Ext how to display the number
        editor: new Ext.form.NumberField({
          allowBlank: false,
          decimalSeparator : ',',
          allowDecimals: true,
          allowNegative: false,
          blankText: '0',
          maxLength: 11
          })
      }*/
   /* PresidentsColumnModel.defaultSortable= true;
//Displaying the Grid
///////////
    PresidentListingEditorGrid =  new Ext.grid.EditorGridPanel({
      id: 'PresidentListingEditorGrid',
      store: PresidentsDataStore,     // the datastore is defined here
      cm: PresidentsColumnModel,      // the columnmodel is defined here
      enableColLock:false,
      clicksToEdit:1,
      selModel: new Ext.grid.RowSelectionModel({singleSelect:false})
    });
 
  PresidentListingWindow = new Ext.Window({
      id: 'PresidentListingWindow',
      title: 'The Registered Employees',
      closable:true,
      width:700,
      height:350,
      plain:true,
      layout: 'fit',
      items: PresidentListingEditorGrid  // We'll just put the grid in for now...
    });
 
  PresidentsDataStore.load();      // Load the data
  PresidentListingWindow.show();   // Display our window
 
*/
