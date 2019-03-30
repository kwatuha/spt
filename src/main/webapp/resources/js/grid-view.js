
function employeeContacts() {
    var toolBars = [{
                text: 'Upload Contacts',
                iconCls: 'user-add',
                id: 'contactUpload',
                handler: function () {

                    // uploadFile('Upload Contacts', 'contactUpload');
                    createUploadForm('center-info-v', 'contactUpload','Upload Contacts');
                }
            }, '-',
    {
        text: 'Delete',
        tooltip: '',
        iconCls: 'remove',
        handler: function () {
            removeQueedItemsOnconfirmation('contacts', 'Are you sure you want to delete all contacts?');
        }
    }, '-'];
    createDisplayView( 'contacts','center-info-v','Employee Contact',toolBars);
   
}


function getEmployeesWithoutEmail() {
    var toolBars = [{
        text: 'Re-send',
        tooltip: '',
        iconCls: 'email',
        handler: function () {
            confirmToSendEMail('no_contacts', 'Are you sure you want to email to missed contacts?');
        }
    }, '-',
    {
        text: 'Delete',
        tooltip: '',
        iconCls: 'remove',
        handler: function () {

            removeQueedItemsOnconfirmation('no_contacts', 'Are you sure you want to delete list of employees without contacts?');
        }
    }, '-'];
    createDisplayView( 'no_contacts','center-info-v','Employees Without Email Addresses',toolBars);
   
}


function emailQueue() {
 
    var toolBars = [{
        text: 'Send Queued Data',
        tooltip: '',
        iconCls: 'email',
        handler: function () {
            if(window.navigator.onLine===true){
                confirmToSendEMail('encrypted', 'Are you sure you want to send all queued messages?');
            }else{
                showloginerror('Internet Connection Error', 'Please connect to an internet network');
            }

        }
    }, '-',
    {
        text: 'Delete',
        tooltip: '',
        iconCls: 'remove',
        handler: function () {

            removeQueedItemsOnconfirmation('encrypted', 'Are you sure you want to delete all queued messages?');
        }
    }, '-'];
createDisplayView( 'queue','center-info-v','Email Queue',toolBars);

}



function createDisplayView( searchitem,display,title,toolBars){
var viewdiv=document.getElementById(display);
viewdiv.innerHTML='';
Ext.onReady(function() {
Ext.QuickTips.init();
var closebtn= Ext.get('close-btn');
	Ext.define('GridViewDataModel', {
    extend: 'Ext.data.Model',
	fields:[
            { name: 'id', type: 'integer' },
            { name: 'firstName', type: 'string' },
            { name: 'middleName', type: 'string' },
            { name: 'lastName', type: 'string' },
            { name: 'pfNumber', type: 'string' },
            { name: 'idNumber', type: 'string' },
            { name: 'emailAddress', type: 'string' },
            { name: 'kraPinNumber', type: 'string' }
        ]
	});
	var store = Ext.create('Ext.data.Store', {
    model: 'GridViewDataModel',
	
    proxy: {
        type: 'ajax',
         url: 'contacts?listType=' + searchitem,
        reader: {
             type: 'json',
                totalProperty: 'total',
                root: 'data',
                successProperty: 'success',
                idProperty: 'id',
        }
    }
});
  store.load({pageSize:50});
       var gridWin = Ext.create('Ext.window.Window', {
            id: 'estsearchforms',
            width:900,
            height: 480,
            title: false,
            autoScroll: true,
            border: false,
            renderTo:'center-info-v',
            layout: 'fit',
            closable:true,
            maximizable:true,
            animCollapse:false,
            constrainHeader:true,
            resizable:true,
            title:title,
            items: [
                    {
                        border: false,
                        xtype: 'grid',
                        // columnLines: true,
                        tbar:toolBars,
                        store: store,
                        		bbar:{height: 20},
            dockedItems: [{
                    xtype: 'pagingtoolbar',
                    store: store,
                    dock: 'bottom',
                    displayInfo: true
                }],
             viewConfig: { stripeRows: true},
            columns: [
            new Ext.grid.RowNumberer({ width: 50, sortable: true }),
            { header: 'First Name', width: 120, sortable: true, id: 'firstName', dataIndex: 'firstName' },
            { header: 'Middle Name', width: 120, sortable: true, id: 'middleName', dataIndex: 'middleName' },
            { header: 'Last Name', width: 120, sortable: true, id: 'lastName', dataIndex: 'lastName' },
            { header: 'PF Number', width: 100, sortable: true, id: 'pfNumber', dataIndex: 'pfNumber' },
            { header: 'KRA Pin', width: 100, sortable: true, id: 'kraPinNumber', dataIndex: 'kraPinNumber' },
            { header: 'Email Address', width: 250, sortable: true, id: 'emailAddress', dataIndex: 'emailAddress' },
            { header: 'Edit', width: 50, sortable: true, id: 'id', dataIndex: 'id',
            icon   : '../../resources/ext/shared/icons/fam/editcontactrow.gif',
            tooltip: 'Sell stock',
            handler: function(grid, rowIndex, colIndex) {
                var rec = store.getAt(rowIndex);
                alert('sssssssssssssss ===='+rec);
            } }
        
        ],
        }
    ]
            

        });

       gridWin.show();

});
}

