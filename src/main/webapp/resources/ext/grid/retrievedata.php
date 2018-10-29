<?php require_once('../../Connections/cf4_HH.php');
?>
<?php
/*$searchNotifications="SELECT
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
		  }
		  
		  
		  
		  employee_name company,
71.72  price,
0.02 changed,
0.03 pin_number,
Income
		  
		  
		  

                  $output[]=$e;
       print(json_encode($output));
       mysql_close();*/
	   
	//getList();   
///////////////////
/*getList();
$task = '';
  if ( isset($_POST['total'])){
    $task = $_POST['total'];   // Get this from Ext
  }
  switch($task){
    case "50000":              // Give the entire list
      getList();
      break;
    default:
      echo "{failure:true}";  // Simple 1-dim JSON array to tell Ext the request failed.
      break;
  }	   */
//////////////////////
/*SELECT
employee_id,
employee_name,
DOB,
phone_number,
town,
nssf_number,
email_address,
effective_dt
*/
function getList() 
{
	$query = " select * 

FROM

pim_employee";
	$result = mysql_query($query);
	$nbrows = mysql_num_rows($result);
	$ctn=0;	
	$arr='';
	if($nbrows>0){
		while($rec = mysql_fetch_array($result)){
                        // render the right date format
			//$rec['tookoffice']=codeDate($rec['tookoffice']);
			$rec['effective_dt']=codeDate($rec['effective_dt']); 
			$rec['DOB']=codeDate($rec['DOB']);
			
			$arr[] = $rec;
		}
		$jsonresult = json_encode($arr);//JEncode($arr);
		//echo '({"id":"'.$$ctn.'","results":'.$jsonresult.'})';
		echo '({"total":"'.$nbrows.'","results":'.$jsonresult.'})';
	} else {
		echo '({"total":"0", "results":""})';
	}
	
	//echo'kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk';
	
}
    /////////////
	
	// Encodes a YYYY-MM-DD into a MM-DD-YYYY string
function codeDate ($date) {
	$tab = explode ("-", $date);
	$r = $tab[1]."/".$tab[2]."/".$tab[0];
	return $r;
}

	
	getList();
	
	/*if ( isset($_POST['total'])){
    $task = $_POST['total'];
	
	
	   // Get this from Ext
  }*/
	/*if ( isset($_POST['total'])){
    $task = $_POST['total'];   // Get this from Ext
  }
  switch($task){
    case "50000":              // Give the entire list
      getList();
      break;
    default:
      echo "{failure:true}";  // Simple 1-dim JSON array to tell Ext the request failed.
      break;
  }	  */
	
	
	
	?>