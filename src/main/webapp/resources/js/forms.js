Ext.require([
    'Ext.form.*',
    'Ext.tip.*'
]);

function createForm() {
    var viewdiv = document.getElementById('upload-form');

    viewdiv.innerHTML = '';
    Ext.onReady(function () {

        /////////////////////////////


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


        //////////////////////////
        Ext.apply(Ext.form.field.VTypes, {
            password: function (val, field) {
                if (/^[a-z0-9]+$/i.test(val)) {
                    return true;
                }
            },
            passwordText: 'Password may only contain letters and numbers.'
        });

        Ext.QuickTips.init();

        function submitOnEnter(field, event) {
            if (event.getKey() == event.ENTER) {
                var form = field.up('form').getForm();
                form.submit();
            }
        }

        // From http://bit.ly/Bvvv8
        function password(length, special) {
            var iteration = 0;
            var password = '';
            var randomNumber;

            if (special == undefined) {
                var special = false;
            }

            while (iteration < length) {
                randomNumber = (Math.floor((Math.random() * 100)) % 94) + 33;
                if (!special) {
                    if ((randomNumber >= 33) && (randomNumber <= 47)) { continue; }
                    if ((randomNumber >= 58) && (randomNumber <= 64)) { continue; }
                    if ((randomNumber >= 91) && (randomNumber <= 96)) { continue; }
                    if ((randomNumber >= 123) && (randomNumber <= 126)) { continue; }
                }
                iteration++;
                password += String.fromCharCode(randomNumber);
            }
            return password;
        }

        // Form
        // -----------------------------------------------------------------------
        var addUserForm = Ext.create('Ext.form.Panel', {
            renderTo: 'upload-form',
            id: 'fileUploadForm',
            bodyStyle: 'padding: 5px 5px 0 5px;',
            defaults: {
                xtype: 'textfield',
                anchor: '80%',
            },
            items: [{
                fieldLabel: 'Email',
                name: 'emails',
                xtype: 'textfield',
                allowBlank: true
            },
            {
                xtype: 'textfield',
                msgTarget: 'side',
                name: 'file_brouwse',
                fieldLabel: 'File Brouwse ',
                allowBlank: true,
                minLength: 1
            }
            ],
            buttons: [
                {
                    xtype: 'button',
                    text: 'Submit Data',
                    handler: function () {
                        var form = this.up('form').getForm();

                        form.submit({
                            url: 'contacts',
                            waitMsg: 'saving changes...',
                            success: function (fp, o) {
                                alert(o);
                            }
                        });

                    }
                },
                {
                    text: 'Cancel',
                    handler: function () {
                        this.up('form').getForm().reset();
                    }
                },
                {
                    text: 'Serialize form',
                    handler: function () {
                        new Ext.FormSerializer('fileUploadForm').toObject();

                        var formjson = Ext.create('Ext.FormSerializer', 'fileUploadForm').toJSON();
                        alert('formjsonformjson' + formjson)
                        var formstrng = Ext.create('Ext.FormSerializer', 'fileUploadForm').toQueryString();
                        alert('formstrng' + formstrng)
                    }
                }

            ]
        });
    });
}
