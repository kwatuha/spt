function uploadFile(title, fileType) {
    var viewdiv = document.getElementById('center-info');
    viewdiv.innerHTML = '';
    Ext.onReady(function () {

        Ext.apply(Ext.form.VTypes, {
            payrollFileUpload: function (val, field) {
                var fileName = /^.*\.(pdf)$/i;
                return fileName.test(val);
            },
            payrollFileUploadText: 'Payroll file must be in .pdf format'
        });


        Ext.apply(Ext.form.VTypes, {
            contactUpload: function (val, field) {
                var fileName = /^.*\.(csv)$/i;
                return fileName.test(val);
            },
            contactUploadText: 'File must be in .csv format'
        });


        Ext.create('Ext.form.Panel', {
            title: title,
            width: 400,
            bodyPadding: 10,
            frame: true,
            renderTo: 'center-info',
            items: [{
                xtype: 'filefield',
                vtype: fileType,
                name: 'file',
                fieldLabel: 'File',
                labelWidth: 50,
                msgTarget: 'side',
                allowBlank: false,
                anchor: '100%',
                buttonText: 'Select a File...'
            }],

            buttons: [{
                text: 'Upload',
                handler: function () {
                    var form = this.up('form').getForm();
                    if (form.isValid()) {
                        form.submit({
                            url: 'upload.action',
                            waitMsg: 'Uploading your file...',
                            success: function (fp, o) {
                                Ext.Msg.alert('Success', 'Your file has been uploaded.');
                            }
                        });
                    }
                }
            }]
        });
    });
}

function createContactForm() {
    var viewdiv = document.getElementById('center-info');
    viewdiv.innerHTML = '';
    Ext.onReady(function () {


        //////////////////

        Ext.define('Ext.FormSerializer', {
            constructor: function (form) {
                this.form = Ext.get(form);
                if (!this.form) {
                    throw "Element " + form + ' not found.';
                }
            }
            , toObject: function () {
                var input = this.form.select('input');
                var select = this.form.select('select');
                var textarea = this.form.select('textarea');

                var elements = input.elements.concat(select.elements).concat(textarea.elements);

                var Data = {};

                Ext.each(elements, function (element) {
                    if (element.name) {
                        if (element.type != 'checkbox') {
                            Data[element.name] = element.value;
                        }
                        else if (element.type == 'checkbox') {
                            Data[element.name] = element.checked ? element.value : false;
                        }
                    }
                }, this);

                return Data;
            }
            , toQueryString: function () {
                var Data = this.toObject();
                var temp = [];
                for (i in Data) {
                    temp.push(encodeURI(i) + '=' + encodeURI(Data[i]));
                }
                return temp.join('&');
            }
            , toJSON: function () {
                return Ext.JSON.encode(this.toObject());
            }
        });

        ///////////////////////

        Ext.create('Ext.form.Panel', {
            title: 'File Uploader',
            width: 400,
            bodyPadding: 10,
            frame: true,
            id: 'contactsForm',
            renderTo: 'center-info',
            items: [

                {
                    xtype: 'textfield',
                    name: 'firstName',
                    fieldLabel: 'firstName'
                },
                {
                    xtype: 'textfield',
                    name: 'middleName',
                    fieldLabel: 'middleName'
                }, {
                    xtype: 'textfield',
                    name: 'lastName',
                    fieldLabel: 'lastName'
                }, {
                    xtype: 'textfield',
                    name: 'pfNumber',
                    fieldLabel: 'pfNumber'
                }, {
                    xtype: 'textfield',
                    name: 'emailAddress',
                    fieldLabel: 'emailAddress'
                },
                {
                    xtype: 'textfield',
                    name: 'idNumber',
                    fieldLabel: 'idNumber'
                }

            ],

            buttons: [{
                text: 'Save',
                handler: function () {
                    var form = this.up('form').getForm();

                    new Ext.FormSerializer('contactsForm').toObject();

                    var formjson = Ext.create('Ext.FormSerializer', 'contactsForm').toJSON();
                    console.log('formjsonformjson', formjson)
                    var formstrng = Ext.create('Ext.FormSerializer', 'contactsForm').toQueryString();
                    //alert('formstrng' + formstrng)
                    saveContact(formjson);
                    //addNewRecords(Ext.encode([form.getValues()]));
                    // var newContact = new App.myContactsModel(formjson);

                    // App.ContactStore.add(newContact);
                    // App.ContactStore.save();


                    // Ext.Ajax.request({
                    //     method: 'POST',
                    //     'Content-Type': 'application/json',
                    //     url: 'contacts',
                    //     params: {
                    //         data: Ext.encode([form.getValues()])
                    //     },
                    //     success: function () {
                    //     },
                    //     failure: function () {
                    //     }
                    // });
                    ///////////////////////////////

                    if (form.isValid()) {




                        // form.submit({
                        //     url: 'contacts',
                        //     waitMsg: 'Uploading your file...',
                        //     success: function (fp, o) {
                        //         Ext.Msg.alert('Success', 'Your file has been uploaded.');
                        //     }
                        // });
                    }
                }
            }]
        });
    });
}


function sendBatchEmail(queue) {
    Ext.Ajax.request({
        url: 'email',
        method: 'POST',
        params: {queue:queue },
        waitMsg: 'Sending Email',
        success: function (fp, o) {
            Ext.Msg.alert('Success', 'Your email has been sent');
        },
        failure: function (fp, o) {
            Ext.Msg.failure('Error', 'Your email has could not send');
        }
    });
}

function getBouncedMessages() {
    Ext.Ajax.request({
        url: 'email',
        method: 'get',
        params: { emailType: 'bounced' },
        waitMsg: 'Finding Bounced Email',
        success: function (fp, o) {
            Ext.Msg.alert('Success', 'Searching Completed');
        },
        failure: function (fp, o) {
            Ext.Msg.failure('Error', 'Your email has could not send');
        }
    });
}

function deleteMessages(queue) {
    Ext.Ajax.request({
        url: 'queue',
        method: 'post',
        params: { queue: queue },
        waitMsg: 'Deleting message queue',
        success: function (fp, o) {
            Ext.Msg.alert('Success', 'Deleted');
        },
        failure: function (fp, o) {
            Ext.Msg.failure('Error', 'Queue could not be cleared');
        }
    });
}

function removeQueedItemsOnconfirmation(queue, title) {

    Ext.Msg.confirm('Confirm Delete', title,
        function (id, value) {
            if (id === 'yes') {
                deleteMessages(queue);
            }
        }, this);


}

function confirmToSendEMail(queue, title) {

    Ext.Msg.confirm('Send Email', title,
        function (id, value) {
            if (id === 'yes') {
                sendBatchEmail(queue);
            }
        }, this);


}