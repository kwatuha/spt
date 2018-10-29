<?php
restrictaccessMenu();
function restrictaccessMenu(){
if (!isset($_SESSION)) {
  session_start();
}
$MM_authorizedUsers = "";
$MM_donotCheckaccess = "true";

// *** Restrict Access To Page: Grant or deny access to this page
function isAuthorized_menu($strUsers, $strGroups, $UserName, $UserGroup) { 
  // For security, start by assuming the visitor is NOT authorized. 
  $isValid = False; 

  // When a visitor has logged into this site, the Session variable MM_Username set equal to their username. 
  // Therefore, we know that a user is NOT logged in if that Session variable is blank. 
  if (!empty($UserName)) { 
    // Besides being logged in, you may restrict access to only certain users based on an ID established when they login. 
    // Parse the strings into arrays. 
    $arrUsers = Explode(",", $strUsers); 
    $arrGroups = Explode(",", $strGroups); 
    if (in_array($UserName, $arrUsers)) { 
      $isValid = true; 
    } 
    // Or, you may restrict access to only certain users based on their username. 
    if (in_array($UserGroup, $arrGroups)) { 
      $isValid = true; 
    } 
    if (($strUsers == "") && true) { 
      $isValid = true; 
    } 
  } 
  return $isValid; 
}

$MM_restrictGoTo = "../index.php";
if (!((isset($_SESSION['MM_Username'])) && (isAuthorized_menu("",$MM_authorizedUsers, $_SESSION['MM_Username'], $_SESSION['MM_UserGroup'])))) {   
  $MM_qsChar = "?";
  $MM_referrer = $_SERVER['PHP_SELF'];
  if (strpos($MM_restrictGoTo, "?")) $MM_qsChar = "&";
  if (isset($QUERY_STRING) && strlen($QUERY_STRING) > 0) 
  $MM_referrer .= "?" . $QUERY_STRING;
  $MM_restrictGoTo = $MM_restrictGoTo. $MM_qsChar . "accesscheck=" . urlencode($MM_referrer);
  header("Location: ". $MM_restrictGoTo); 
  exit;
}
}
?>
<?php require_once('../../Connections/cf4_HH.php');
?>
<?php
$searchNotifications="SELECT
employee_name company,
71.72  price,
0.02 changed,
0.03 pctChange,
effective_dt lastChange

FROM

pim_employee

";


          $q=mysql_query( $searchNotifications);

$output='';
    $myarrya='';$ctn=0;
          while($e=mysql_fetch_assoc($q))/*{
		  $company=$e[company];
		  $price=$e[price];
		  $pctChange=$e[pctChange];
		  $lastChange=$e[lastChange];
		   $myarrya[$ctn]="['".$company."',".$price.','.$pctChange.','.$lastChange.'],';
		   $ctn++;
		  }*/

                  $output[]=$e;

               /*print(json_encode($output));
         
        mysql_close();*/
    ?>
<script type="text/javascript">
var arrarData = <?php print(json_encode($output)); ?>;
alert(arrarData.length);

/*for(i=0;i<arrarData.length;i++){

alert(arrarData[i]);
}*/

</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Stateful Array Grid Example</title>
    <link rel="stylesheet" type="text/css" href="../resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="../shared/example.css" />
    <script type="text/javascript" src="../bootstrap.js"></script>

    <!-- page specific -->
    <style type="text/css">
        /* style rows on mouseover */
        .x-grid-row-over .x-grid-cell-inner {
            font-weight: bold;
        }
        /* shared styles for the ActionColumn icons */
        .x-action-col-cell img {
            height: 16px;
            width: 16px;
            cursor: pointer;
        }
        /* custom icon for the "buy" ActionColumn icon */
        .x-action-col-cell img.buy-col {
            background-image: url(../shared/icons/fam/accept.png);
        }
        /* custom icon for the "alert" ActionColumn icon */
        .x-action-col-cell img.alert-col {
            background-image: url(../shared/icons/fam/error.png);
        }

        .x-ie6 .x-action-col-cell img.buy-col {
            background-image: url(../shared/icons/fam/accept.gif);
        }
        .x-ie6.x-action-col-cell img.alert-col {
            background-image: url(../shared/icons/fam/error.gif);
        }

        .x-ie6 .x-action-col-cell img {
            position:relative;
            top:-1px;
        }
    </style>
    <script type="text/javascript" src="grid.js"></script>
</head>
<body>
<div id="grid-example"></div>
</body>
</html>