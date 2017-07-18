/**
 * Created by ALFAYO on 7/7/2017.
 */
Ext.namespace('App');
Ext.Loader.setConfig({
    enabled: true
});
Ext.Loader.setPath('Ext.ux', '../ext/ux');

Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.ux.RowExpander',
    'Ext.selection.CheckboxModel'
]);

function estateViewPort() {

    var displayhere = 'landing-page';
    var loadtype = 'Save';
    var rid = 'NOID';
    var obj = document.getElementById(displayhere);
    obj.innerHTML = '';

    var menuss = Ext.create('Ext.menu.Menu', {
        width: 200,
        plain: true,
        border: false,
        floating: false,
        renderTo: Ext.getBody(),
        items: [
            {
                text: 'Contacts',
                iconCls: 'user',
                id: 'Contact-list',
                handler: function () {
                    employeeContacts();
                }
            },
            {
                text: 'Message Queue',
                iconCls: 'icon-grid',
                id: 'messageQue',
                handler: function () {
                    emailQueue();
                }
            },
            {
                text: 'Missing Email Address',
                iconCls: 'user-red',
                handler: function () {
                    getEmployeesWithoutEmail();
                }
            },
            {
                text: 'Upload Contacts',
                iconCls: 'user-add',
                id: 'contactUpload',
                handler: function () {

                    uploadFile('Upload Contacts', 'contactUpload');
                }
            },
            {
                text: 'Upload Payroll File',
                id: 'payrollFileUpload',
                iconCls: 'pdf',
                handler: function () {
                    uploadFile('Upload Payroll File', 'payrollFileUpload');
                }
            }




        ]
    });



    Ext.onReady(function () {
        var formPanel3 = Ext.create('Ext.form.Panel', {
            region: 'west',
            margins: '50 0 0 3',
            id: 'estsearchform',
            title: false,
            autoScroll: true,
            border: false,
       
            bodyStyle: 'padding: 10px; background-color: #DFE8F6', //#DFE8F6
            items: menuss

        });

        var centerPanel = Ext.create('Ext.form.Panel', {
            closable: false,
            header: false,
            border: false,
            bodyBorder: false,
            region: 'center',
            margins: '0 0 0 3',
            id: 'displayPanelId',
            html: '<div id="center-info"></div>',
            bodyStyle: 'padding: 10px; background-color: #DFE8F6' //#DFE8F6
        });

        var displayPanel = Ext.create('Ext.Panel', {
            width: 1200,
            height: 900,
            border: false,
            autoScroll: true,
            bodyStyle: 'padding: 10px; background-color: #DFE8F6',
            layout: 'border',
            renderTo: 'landing-page',
            bodyPadding: '5',
            items: [
                formPanel3,
                centerPanel
            ]

        });

        var toolBars = [
            {
                text: 'Contacts',
                iconCls: 'user',
                id: 'Contact-list',
                handler: function () {
                    employeeContacts();
                }
            },'-',
            {
                text: 'Message Queue',
                iconCls: 'icon-grid',
                id: 'messageQue',
                handler: function () {

                   emailQueue();
                }
            },'-',
            {
                text: 'Missing Email Address',
                iconCls: 'user-red',
                handler: function () {
                    getEmployeesWithoutEmail();
                }
            },'-',
            {
                text: 'Upload Contacts',
                iconCls: 'user-add',
                id: 'contactUpload',
                handler: function () {

                    uploadFile('Upload Contacts', 'contactUpload');
                }
            },'-',
            {
                text: 'Upload Payroll File',
                id: 'payrollFileUpload',
                iconCls: 'pdf',
                handler: function () {
                    uploadFile('Upload Payroll File', 'payrollFileUpload');
                }
            },'-'];
        var win = Ext.create('Ext.window.Window', {
            title:  '<div class="app-loader-logo"><img src="resources/ext/shared/icons/fam/ampath.png"/></div>'+'<div class="app-title">SPT</div>',
            bodyPadding: 10,
            autoScroll: true,
            maximizable: false,
            collapsible: true,
            closable: false,
            maximized: true,
            border: false,
            bodyBorder: false,
            bodyStyle: 'padding: 10px; background-color: #DFE8F6',
            margins:'50 0 0 0',
            id: 'idestatemgtwin',
            plain: true,
            items: displayPanel,
            buttonAlign: 'center'
        });

        win.show();
    });
}



