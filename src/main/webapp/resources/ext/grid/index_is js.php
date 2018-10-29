<script>
var PresidentsDataStore;         // this will be our datastore
var PresidentsColumnModel;       // this will be our columnmodel
var PresidentListingEditorGrid;
var PresidentListingWindow;
 
Ext.onReady(function(){
  Ext.QuickTips.init();
  //float
  PresidentsDataStore = new Ext.data.Store({
      id: 'PresidentsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: 'retrievedata.php',      // File to connect to
                method: 'POST'
            }),
            baseParams:{task: "LISTING"}, // this parameter asks for listing
      reader: new Ext.data.JsonReader({   
                  // we tell the datastore where to get his data from
        root: 'results',
        totalProperty: 'total',
        id: 'id'
      },[ 
        {name: 'IDemployee', type: 'int', mapping: 'employee_id'},
        {name: 'EmployeeName', type: 'string', mapping: 'employee_name'},
        {name: 'Address', type: 'string', mapping: 'address'},
        {name: 'PhoneNumber', type: 'string', mapping: 'phone_number'},
        {name: 'Town', type: 'string', mapping: 'town'},
        {name: 'DateOfBirth', type: 'date', mapping: 'DOB'},
        {name: 'pinNumber', type: 'date', mapping: 'pin_number'},
        {name: 'nssf_number', type: 'string', mapping: 'nssf_number'}
      ]),
      sortInfo:{field: 'IDemployee', direction: "ASC"}
    });


///////
PresidentsColumnModel = new Ext.grid.ColumnModel(
    [{
        header: '#',
        readOnly: true,
        dataIndex: 'IDemployee', // this is where the mapped name is important!
        width: 50,
        hidden: false
      },{
        header: 'Employee Name',
        dataIndex: 'EmployeeName',
        width: 150,
        editor: new Ext.form.TextField({  // rules about editing
            allowBlank: false,
            maxLength: 50,
            maskRe: /([a-zA-Z0-9\s]+)$/   // alphanumeric + spaces allowed
          })
      },
	  /////
	  
	  {name: 'EmployeeName', type: 'string', mapping: 'employee_name'},
        {name: 'Address', type: 'string', mapping: 'address'},
        {name: 'PhoneNumber', type: 'string', mapping: 'phone_number'},
        {name: 'Town', type: 'string', mapping: 'town'},
        {name: 'DateOfBirth', type: 'date', mapping: 'DOB'},
        {name: 'effective_dt', type: 'date', mapping: 'effective_dt'},
        {name: 'nssf_number', type: 'string', mapping: 'nssf_number'}
	  ///
	  {
        header: 'Personal Address',
        dataIndex: 'Address',
        width: 150,
        editor: new Ext.form.TextField({
          allowBlank: false,
          maxLength: 20,
          maskRe: /([a-zA-Z0-9\s]+)$/
          })
      },{
        header: 'Phone Number',
        readOnly: true,
        dataIndex: 'PhoneNumber',
        width: 50,
        hidden: true                      // we don't necessarily want to see this...
      },{
        header: 'Home Town',
        dataIndex: 'Town',
        width: 150,
        readOnly: true
      },
	  ,{
        header: 'Date Of Birth',
        dataIndex: 'DateOfBirth',
        width: 150,
        readOnly: true
      },
	  {
        header: 'Nssf number',
        dataIndex: 'nssf_number',
        width: 150,
        readOnly: true
      },
	  {
        header: 'Transaction date',
        dataIndex: 'effective_dt',
        width: 150,
        readOnly: true
      }/*{
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
          }*/)
      }]
    );
    PresidentsColumnModel.defaultSortable= true;
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
 
});
</script>