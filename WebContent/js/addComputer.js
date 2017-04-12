//on load
$(function() {
	$('#computerName').blur(function() {
		$(this).css('background-color', 'gray');
	     if(!$.trim(this.value).length) { // zero-length string AFTER a trim
	    	 alert("Empty name. Please include at least two letters.");
	     }
	     if($(this).val().length > 0 && $(this).val().length < 2) { // zero-length string AFTER a trim
	    	 alert("Too short name. Include more letters.");
	     }
	});
	
	$( "#introducedDate" ).datepicker({
		dateFormat: 'dd/MM/yyyy',
		onSelect:function(){
			validateDate();
			}
	});
	$( "#discontinuedDate" ).datepicker({
		dateFormat: 'dd/MM/yyyy',
		onSelect:function(){
			validateDate();
			}
	});
	
	$('#discontinuedDate').on('blur', function() {
		validateDate();
	});
	
	$('#introducedDate').on('blur', function() {
		validateDate();
	});
	

	$('#submitForm').click(function(event) {
		if(!validateDate() || !validateName()){
			event.preventDefault();
		}
	});
	
	
});

function validateDate(){
	alert('My message2');
	var disco=$('#discontinuedDate').val();
	var intro=$('#introducedDate').val();
	if(!intro || !disco || disco>intro){
		$('#discontinuedDate').parent().removeClass("alert alert-danger")
		return true;
	}else{
		$('#discontinuedDate').parent().addClass("alert alert-danger");
		return false;
	}
}

function validateName(){
	alert('My message3');
	var name=$('#computerName').val();
	if(typeof name === "undefined" || name.trim()==""){
		alert("You must suply a name");
		return false;
	}else{
		return true;
	}
}