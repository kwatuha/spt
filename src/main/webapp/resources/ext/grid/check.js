function showinfo(tablename){
Ext.Ajax.request({
    url: 'http://localhost/formgen/home/buidgriddesign.php?t='+tablename,
    params: {
        id: 1
    },
    success: function(response){
         rsptext = response.responseText;
		    var e = document.getElementById('personaldata');
            e.innerHTML = rsptext;
            eval(document.getElementById('personaldata').innerHTML);
          }
});

}

/*
////////////
$.getScript("ajax/test.js")
.done(function(script, textStatus) {
  console.log( textStatus );
})
.fail(function(jqxhr, settings, exception) {
  $( "div.log" ).text( "Triggered ajaxError handler." );
});  */

