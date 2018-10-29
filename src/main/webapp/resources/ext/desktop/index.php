<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>ExtTop - Desktop Sample App</title>

    <link rel="stylesheet" type="text/css" href="../resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="css/desktop.css" />

    <script type="text/javascript" src="../builds/ext-core.js"></script>
    <script type="text/javascript" src="classes.js"></script>

    <script type="text/javascript">
        Ext.Loader.setPath({
            'Ext.ux.desktop': 'js',
            MyDesktop: ''
        });

        Ext.require('MyDesktop.App');

        var myDesktopApp;
        Ext.onReady(function () {
            myDesktopApp = new MyDesktop.App();
        });
    </script>
</head>

<body>

    <a href="http://www.sencha.com" target="_blank" alt="Powered by Ext JS"
       id="poweredby"><div></div></a>

</body>
</html>