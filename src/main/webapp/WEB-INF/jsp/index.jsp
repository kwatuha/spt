<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Secure Payslip Transmission</title>
    <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1" />
    <meta name="description" content="Secure Payslip Transmission App" />
    <meta name="keywords" content="extjs,spring framework" />
    <link rel="stylesheet" type="text/css" href="resources/ext/resources/css/ext-all.css"/>
    <link rel="stylesheet" type="text/css" href="resources/ext/desktop/css/desktop.css"/>
    <link rel="stylesheet" type="text/css" href="resources/ext/layout/css/desktop.css"/>
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/resources/css/forms.css"/>" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/resources/css/other_shared.css"/>" rel="stylesheet"  type="text/css" />
    <script type="text/javascript" src="resources/ext/bootstrap.js"></script>
    <script type="text/javascript" src="resources/ext/ux/RowExpander.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/grid-view.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/forms.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/app.js"/>"></script>
    <script>
        window.onload = function() {
            Ext.onReady(function() {
                setTimeout(function(){
                    estateViewPort();
                    }, 300);
            });
        }
    </script>
</head>
<body>
<div id="landing-page"></div>
</body>
</html>