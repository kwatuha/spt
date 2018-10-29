<?php require_once('../../Connections/cf4_HH.php');
?>
<?php
$SQL=$_GET['q'];

//if($SQL){

$RcdQryAlertresults = mysql_query("select employee_id,      
	       employee_name,  
	       employee_number,
	       phone_number,   
	       effective_dt,   
	       nssf_number,    
	       email_address from pim_employee") or die(mysql_error());
	 $cntreg=mysql_num_rows($RcdQryAlertresults);
	 
                  $output='';
	  while($e=mysql_fetch_assoc($RcdQryAlertresults)){

                  $output[]=$e;

               }
			   
			   //querydata
			   
			   $jsonresult=json_encode($output);
			   echo '({"results":'.$jsonresult.'});';
         echo '{"total":'.$cntreg.'}';
        mysql_close();

	 
	 
	 
	 
	// }
	 
	/* if($cntreg){
	 $adata='';
	 ///while($count_ctrls=mysql_fetch_array($RcdQryAlertresults))
	 {
				$employee_id=$count_ctrls['employee_id'];
				$employee_number=$count_ctrls['employee_number'];
				$employee_name=$count_ctrls['employee_name'];
				$photo=$count_ctrls['photo'];
				$DOB=$count_ctrls['DOB'];
				$national_ID=$count_ctrls['national_ID'];
				$gender=$count_ctrls['gender'];
				$address=$count_ctrls['address'];
				$phone_number=$count_ctrls['phone_number'];
				$town=$count_ctrls['town'];
				$pin_number=$count_ctrls['pin_number'];
				$nssf_number=$count_ctrls['nssf_number'];
				$nhif_number=$count_ctrls['nhif_number'];
				$email_address=$count_ctrls['email_address'];
				$postal_code=$count_ctrls['postal_code'];
				$effective_dt=$count_ctrls['effective_dt'];

			 }*/
			// $q=mysql_query("SELECT * FROM people WHERE birthyear>'".$_REQUEST['year']."'");



         

		///$jsonresult = json_encode($arr);//JEncode($arr);
		
	
	
	
	?>