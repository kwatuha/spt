// function createViewPort(){
//     Ext.onReady(function(){
//         Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
//
//         var viewport = new Ext.Viewport({
//             layout: 'border',
//             items: [
//                 // create instance immediately
//                 new Ext.BoxComponent({
//                     region: 'north',
//                     height: 32, // give north and south regions a height
//                     autoEl: {
//                         tag: 'div',
//                         html:'Secure Payslip Transmission'
//                     }
//                 }), {
//                     // lazily created panel (xtype:'panel' is default)
//                     region: 'south',
//                     contentEl: 'south',
//                     split: true,
//                     height: 100,
//                     minSize: 100,
//                     maxSize: 200,
//                     collapsible: false,
//                     title: 'South',
//                     margins: '0 0 0 0'
//                 }, {
//                     region: 'east',
//                     title: 'East Side',
//                     collapsible: false,
//                     split: true,
//                     width: 225, // give east and west regions a width
//                     minSize: 175,
//                     maxSize: 400,
//                     margins: '0 5 0 0',
//                     layout: 'fit', // specify layout manager for items
//                     items:            // this TabPanel is wrapped by another Panel so the title will be applied
//                         new Ext.TabPanel({
//                             border: false, // already wrapped so don't add another border
//                             activeTab: 1, // second tab initially active
//                             tabPosition: 'bottom',
//                             items: [{
//                                 html: '<p>A TabPanel component can be a region.</p>',
//                                 title: 'A Tab',
//                                 autoScroll: true
//                             }, new Ext.grid.PropertyGrid({
//                                 title: 'Property Grid',
//                                 closable: true,
//                                 source: {
//                                     "(name)": "Properties Grid",
//                                     "grouping": false,
//                                     "autoFitColumns": true,
//                                     "productionQuality": false,
//                                     "created": new Date(Date.parse('10/15/2006')),
//                                     "tested": false,
//                                     "version": 0.01,
//                                     "borderWidth": 1
//                                 }
//                             })]
//                         })
//                 }, {
//                     region: 'west',
//                     id: 'west-panel', // see Ext.getCmp() below
//                     title: 'West',
//                     split: true,
//                     width: 200,
//                     minSize: 175,
//                     maxSize: 400,
//                     collapsible: false,
//                     margins: '0 0 0 5',
//                     layout: {
//                         type: 'accordion',
//                         animate: true
//                     },
//                     items: [{
//                         contentEl: 'west',
//                         title: 'Navigation',
//                         border: false,
//                         iconCls: 'nav' // see the HEAD section for style used
//                     }, {
//                         title: 'Settings',
//                         html: '<p>Some settings in here.</p>',
//                         border: false,
//                         iconCls: 'settings'
//                     }]
//                 },
//                 // in this instance the TabPanel is not wrapped by another panel
//                 // since no title is needed, this Panel is added directly
//                 // as a Container
//                 new Ext.TabPanel({
//                     region: 'center', // a center region is ALWAYS required for border layout
//                     deferredRender: false,
//                     activeTab: 0,     // first tab initially active
//                     items: [{
//                         contentEl: 'queue-grid',
//                         title: 'View Out Going Mails',
//                         closable: false,
//                         autoScroll: true
//                     }, {
//                         contentEl: 'contacts-grid',
//                         title: 'View Contacts',
//                         autoScroll: true
//                     }]
//                 })]
//         });
//         // get a reference to the HTML element with id "hideit" and add a click listener to it
//         // Ext.get("hideit").on('click', function(){
//         //     // get a reference to the Panel that was created with id = 'west-panel'
//         //     var w = Ext.getCmp('west-panel');
//         //     // expand or collapse that Panel based on its collapsed property state
//         //     w.collapsed ? w.expand() : w.collapse();
//         // });
//     });
// }
//
//
// function composeEMail(){
//     Ext.onReady(function() {
//         var form = new Ext.form.FormPanel({
//             baseCls: 'x-plain',
//             labelWidth: 55,
//             url:'contacts',
//             defaultType: 'textfield',
//
//             items: [{
//                 fieldLabel: 'Send To',
//                 name: 'to',
//                 anchor:'100%'  // anchor width by percentage
//             },{
//                 fieldLabel: 'Subject',
//                 name: 'subject',
//                 anchor: '100%'  // anchor width by percentage
//             }, {
//                 xtype: 'textarea',
//                 hideLabel: true,
//                 name: 'msg',
//                 anchor: '100% -53'  // anchor width by percentage and height by raw adjustment
//             }
//
//
//             ]
//         });
//
//         var window = new Ext.Window({
//             title: 'Resize Me',
//             width: 500,
//             height:300,
//             minWidth: 300,
//             minHeight: 200,
//             layout: 'fit',
//             plain:true,
//             bodyStyle:'padding:5px;',
//             buttonAlign:'center',
//             items: form,
//
//             buttons: [{
//                 text: 'Send',
//                 handler: function() {
//                     alert('ssssssssssssssssss')
//                 }
//             },{
//                 text: 'Cancel'
//             }]
//         });
//
//         window.show();
//     });
// }
//
// function createFileUploader(){
//
//     Ext.onReady(function(){
//
//         Ext.QuickTips.init();
//
//         var msg = function(title, msg){
//             Ext.Msg.show({
//                 title: title,
//                 msg: msg,
//                 minWidth: 200,
//                 modal: true,
//                 icon: Ext.Msg.INFO,
//                 buttons: Ext.Msg.OK
//             });
//         };
//
//         var fibasic = new Ext.ux.form.FileUploadField({
//             renderTo: 'fi-basic',
//             width: 400
//         });
//
//         new Ext.Button({
//             text: 'Get File Path',
//             renderTo: 'fi-basic-btn',
//             handler: function(){
//                 var v = fibasic.getValue();
//                 msg('Selected File', v && v != '' ? v : 'None');
//             }
//         });
//
//         var fbutton = new Ext.ux.form.FileUploadField({
//             renderTo: 'fi-button',
//             buttonOnly: true,
//             listeners: {
//                 'fileselected': function(fb, v){
//                     var el = Ext.fly('fi-button-msg');
//                     el.update('<b>Selected:</b> '+v);
//                     if(!el.isVisible()){
//                         el.slideIn('t', {
//                             duration: .2,
//                             easing: 'easeIn',
//                             callback: function(){
//                                 el.highlight();
//                             }
//                         });
//                     }else{
//                         el.highlight();
//                     }
//                 }
//             }
//         });
//
//         var fp = new Ext.FormPanel({
//             renderTo: 'fileform',
//             fileUpload: true,
//             width: 500,
//             frame: true,
//             title: 'File Upload Form',
//             autoHeight: true,
//             bodyStyle: 'padding: 10px 10px 0 10px;',
//             labelWidth: 50,
//             defaults: {
//                 anchor: '95%',
//                 allowBlank: false,
//                 msgTarget: 'side'
//             },
//             items: [{
//                 xtype: 'textfield',
//                 fieldLabel: 'Name'
//             },{
//                 xtype: 'fileuploadfield',
//                 id: 'form-file',
//                 emptyText: 'Select an image',
//                 fieldLabel: 'Photo',
//                 name: 'photo-path',
//                 buttonText: '',
//                 buttonCfg: {
//                     iconCls: 'upload-icon'
//                 }
//             }],
//             buttons: [{
//                 text: 'Save',
//                 handler: function(){
//                     if(fp.getForm().isValid()){
//                         fp.getForm().submit({
//                             url: 'file-upload.php',
//                             waitMsg: 'Uploading your photo...',
//                             success: function(fp, o){
//                                 msg('Success', 'Processed file "'+o.result.file+'" on the server');
//                             }
//                         });
//                     }
//                 }
//             },{
//                 text: 'Reset',
//                 handler: function(){
//                     fp.getForm().reset();
//                 }
//             }]
//         });
//
//     });
//
// }

function displayContacts() {
    //var grid = createContactsGrid('Employee Contacts');
    genericGridView(grid);
}
function genericGridView(gridConfig, title) {
    var searchitem = '';

    var viewdiv = document.getElementById('contact-grid');

    viewdiv.innerHTML = '';
    Ext.require([
        'Ext.grid.*',
        'Ext.data.*',
        'Ext.form.field.Number',
        'Ext.form.field.Date',
        'Ext.tip.QuickTipManager'
    ]);
    Ext.onReady(function () {

        Ext.QuickTips.init();
        var store = gridConfig.store;
        store.load();

        var closebtn = Ext.get('close-btn');

        var sellAction = Ext.create('Ext.Action', {
            icon: '../shared/icons/fam/delete.gif',  // Use a URL in the icon config
            text: 'Delete',
            disabled: true,
            handler: function (widget, event) {

            }
        });

        var buyAction = Ext.create('Ext.Action', {
            iconCls: 'user-girl',
            text: 'Edit',
            disabled: true,
            handler: function (widget, event) {
                var rec = grid.getSelectionModel().getSelection()[0];
                if (rec) {

                } else {

                }
            }
        });

        var contextMenu = Ext.create('Ext.menu.Menu', {
            items: []
        });

        // Grid Definition
        var grid = Ext.create('Ext.grid.Panel', {
            store: store,
            stateful: true,
            closable: true,
            multiSelect: true,
            iconCls: 'icon-grid',
            stateId: 'stateGrid',
            animCollapse: false,
            constrainHeader: true,
            layout: 'fit',
            columnLines: true,
            bbar: { height: 20 },
            dockedItems: [{
                xtype: 'pagingtoolbar',
                store: store,
                dock: 'bottom',
                displayInfo: true
            }],
            features: [{
                id: 'group',
                ftype: 'groupingsummary',
                groupHeaderTpl: '{name}',
                hideGroupedHeader: true,
                enableGroupingMenu: false
            }],

            columns: gridConfig.columns,
            maxHeight: 600,
            width: 1100,
            resizable: true,
            title: title,
            renderTo: 'landlordData',
            viewConfig: {
                stripeRows: true,
                listeners: {
                    itemcontextmenu: function (view, rec, node, index, e) {
                        e.stopEvent();
                        contextMenu.showAt(e.getXY());
                        return false;
                    }
                }
            }
            ,
            listeners: {
                itemdblclick: function (dv, record, item, index, e) {
                    var empname = record.get('person_name');
                    customizedFormRevised('title', 'savetable', gridConfig.formData, record);
                    // setDisplayConfig(gridConfig);
                    setDisplayConfig({ config: gridConfig.config });
                },
                itemclick: function (dv, record, item, index, e) {
                    customizedFormRevised('title', 'savetable', gridConfig.formData, record);
                    setDisplayConfig({ config: gridConfig.config });

                }
            },
            tbar: [{
                text: 'Add Record',
                tooltip: 'Customize sms message',
                iconCls: 'add',
                handler: function () {
                    createForm("Save", "NOID", "sms_smsmsgcust", "f")
                }
            }, '-', {
                text: 'PDF',
                tooltip: 'Create options',
                iconCls: 'option',
                handler: function (buttonObj, eventObj) {
                    OpenPDFstatement('c21zX3Ntc21zZ2N1c3Q=');
                    // OpenReport('c21zX3Ntc21zZ2N1c3Q=');
                }
            }, '-',
            {
                text: 'Search',
                tooltip: 'Find',
                iconCls: 'find',
                handler: function (grid, rowIndex, colIndex) {
                    //testme();
                }

            }
                ,

            {
                xtype: 'combobox',
                name: 'grdsearchsms_smsmsgcust',
                id: 'grdsearchsms_smsmsgcust',
                forceSelection: false,
                fieldLabel: false,
                queryMode: 'local',
                displayField: 'fieldcaption',
                valueField: 'fieldname',
                listeners: {
                    select: function (combo, record, index) {
                        var selVal = Ext.getCmp('grdsearchsms_smsmsgcust').getValue();
                        var selValtx = Ext.getCmp('searchfield').getValue();
                    }
                }

            },
            {
                title: 'Search',
                tooltip: 'Find record',
                xtype: 'textfield',
                name: 'searchfield',
                id: 'searchfield',
                iconCls: 'remove',
                listeners: {
                }
            }]

        });
    });
}

function contactDataSource(title) {
    var gridDef = {
        dataModel: [
            { name: 'id', type: 'integer' },
            { name: 'firstName', type: 'string' },
            { name: 'middleName', type: 'string' },
            { name: 'lastName', type: 'string' },
            { name: 'pfNumber', type: 'string' },
            { name: 'emailAddress', type: 'string' }],
        gridColumns: [
            new Ext.grid.RowNumberer({ width: 50, sortable: true }),
            { header: 'First Name', width: 75, sortable: true, id: 'firstName', dataIndex: 'firstName' },
            { header: 'Middle Name', width: 75, sortable: true, id: 'middleName', dataIndex: 'middleName' },
            { header: 'Last Name', width: 160, sortable: true, id: 'lastName', dataIndex: 'lastName' },
            { header: 'PF Number', width: 160, sortable: true, id: 'pfNumber', dataIndex: 'pfNumber' },
            { header: 'Email Address', width: 160, sortable: true, id: 'emailAddress', dataIndex: 'emailAddress' }
        ]
    };

    var gridData = gridDataSource(gridModel, 'contacts');

    return { store: gridData, columns: gridDef.gridColumns, title: title, formData: formData, config: {} };
}


function gridDataSourceModel(dataColumns) {
    return Ext.define('gridModel', {
        extend: 'Ext.data.Model',
        fields: dataColumns
    });
}


function gridDataSource(gridModel, searchitem) {
    return Ext.create('Ext.data.Store', {
        model: gridModel,
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
}