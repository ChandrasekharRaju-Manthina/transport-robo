var intervalTimer, clearIntervalTimer;
function scrollToAnchor(aid){
    var aTag = $("a[name='"+ aid +"']");
    $('html,body').animate({scrollTop: aTag.offset().top},'slow');
}

function resetToAdd(form) {
	$("#form").attr('method','POST');
	$("button[type='submit']").html("Add");
	$("#addLink").hide();
	$("#form")[0].reset();
	if(form == "addressForm") {
    	$("#form").attr('data-success-msg','Address has been created successfully.');
    	$("#header").html("Add Address");    	
	} else if(form == "driverForm") {
		$("#form").attr('data-success-msg','Driver has been created successfully.');
    	$("#header").html("Add Driver");
	} else if(form == "vehicleForm") {
		$("#form").attr('data-success-msg','Vehicle has been created successfully.');
    	$("#header").html("Add Vehicle");
	} else if(form == "shiftForm") {
		$("#form").attr('data-success-msg','Shift has been created successfully.');
    	$("#header").html("Add Shift");
	}
}
terminateSolution = function() {	
	clearInterval(intervalTimer);
	clearInterval(clearIntervalTimer);
	$("#loadIcon").hide();
}
updateSolution = function() {
	clearInterval(intervalTimer);
    $.ajax({
      type: $("#tripSheetForm").attr("method"),
      url: "tripSheet",
      dataType : "json",
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify($("#tripSheetForm").serializeObject()),
      success: function(solution) {
    	  console.log(JSON.stringify(solution));
      }, error : function(jqXHR, textStatus, errorThrown) {ajaxError(jqXHR, textStatus, errorThrown)}
    });
  };

$(function() {

    $('#side-menu').metisMenu();
    
    $(document).on("click", "a.delete", function(e){
    	var $this = $(this);
    	e.preventDefault();
    	$.ajax({
            type: "DELETE",
            url: $(this).attr("data-url") + "/" + $(this).attr("data-id"),
            success: function(data)
            {
            	$("#data-table").DataTable().row($this.parents("tr")).remove().draw();
            	$("#successMsgTxt").text($this.attr("data-success-msg"));
            	$("#successMsg").show();
            	$("#successMsg").hide().fadeIn("slow").delay(3000).hide(1);
            },
            error: function(request,status,errorThrown) {
            	alert("Error");
            }
        });
    });
    
    $(document).on("click", "a.approve", function(e){
    	var $this = $(this);
    	e.preventDefault();
    	$.ajax({
            type: "PUT",
            url: $(this).attr("data-url") + "/" + $(this).attr("data-id"),
            success: function(data)
            {
            	$("#data-table").DataTable().row($this.parents("tr")).remove().draw();
            	$("#successMsgTxt").text($this.attr("data-success-msg"));
            	$("#successMsg").show();
            	$("#successMsg").hide().fadeIn("slow").delay(3000).hide(1);
            },
            error: function(request,status,errorThrown) {
            	alert("Error");
            }
        });
    });
    
    $(document).on("click", "a.update", function(e){
    	e.preventDefault();
    	var formName = $("#form").attr("name");
    	$("#form").attr('method','PUT');
    	$("button[type='submit']").html("Update");
    	if(formName == "addressForm") {
	    	var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
	    	$("input[name='addressLine']").val(data.addressLine);
	    	$("input[name='city']").val(data.city);
	    	$("input[name='state']").val(data.state);
	    	$("input[name='zip']").val(data.zip);
	    	$("input[name='country']").val(data.country);
	    	$("input[name='id']").val(data.id);
	    	$("#form").attr('data-success-msg','Address has been updated successfully.');
	    	$("#header").html("Update Address");	    	
    	} else if(formName == "driverForm") {
    		var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
    		$("input[name='name']").val(data.name);
	    	$("input[name='licenseNumber']").val(data.licenseNumber);
	    	$("input[name='phoneNumber']").val(data.phoneNumber);
	    	$("input[name='yearsOfExperience']").val(data.yearsOfExperience);
	    	$("input[name='id']").val(data.id);
	    	$("#form").attr('data-success-msg','Address has been updated successfully.');
	    	$("#header").html("Update Driver details");
    	} else if(formName == "vehicleForm") {
    		var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
    		$("input[name='vehicleNumber']").val(data.vehicleNumber);
	    	$("input[name='seats']").val(data.seats);
	    	$("input[name='trackingDeviceLink']").val(data.trackingDeviceLink);
	    	$("select[name='driverId']").val(data.driverId);
	    	$("input[name='id']").val(data.id);
	    	$("#form").attr('data-success-msg','Vehicle has been updated successfully.');
	    	$("#header").html("Update Vehicle details");
    	} else if(formName == "shiftForm") {
    		var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
    		$("input[name='startTime']").val(data.startTime);
	    	$("input[name='endTime']").val(data.endTime);
	    	$("input[name='id']").val(data.id);
	    	$("#form").attr('data-success-msg','Shift has been updated successfully.');
	    	$("#header").html("Update Shift details");
    	}
    	$("#addLink").show();
    	scrollToAnchor("addMenu");
    });
    
    $("#tripSheetForm").submit(function (e) {
        e.preventDefault();
        console.log(JSON.stringify($("#tripSheetForm").serializeObject()));
        $.ajax({
            type: $("#tripSheetForm").attr("method"),
            url: $("#tripSheetForm").attr("action"),
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify($("#tripSheetForm").serializeObject()),
            success: function(data)
            {
            	console.log(data);
            	$("#loadIcon").show();
            	intervalTimer = setInterval(function () {
                    updateSolution()
                }, 120000);
//            	clearIntervalTimer = setInterval(function () {
//                    terminateSolution()
//                }, 120000);
            },
            error: function(request,status,errorThrown) {
              alert(errorThrown);
            }
        });
    });
    
    $("#form").submit(function (e) {
        e.preventDefault();
        $(".error-msg").hide();
        $("div").removeClass("has-error");
        $.ajax({
            type: $("#form").attr("method"),
            url: $("#form").attr("action"),
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify($("#form").serializeObject()), // serializes the form's elements.
            success: function(data)
            {
            	$("#successMsgTxt").text($("#form").attr("data-success-msg"));
                $("#successMsg").show();
                $("#successMsg").hide().fadeIn("slow").delay(3000).hide(1);
                $("#form")[0].reset();
                $("#data-table").DataTable().ajax.reload();
                resetToAdd($("#form").attr("name"));
            },
            error: function(request,status,errorThrown) {
               if(request.status = 400) {
            	   $.each(request.responseJSON.fieldErrors, function(i, item) {
            		   	var id = "#"+item.field +"-error"
            		    $(id).text(item.message)
            		    var parent = $(id).closest("div")
            		    parent.addClass("has-error");
            		    $(id).show();
            	   });            	   
               }
            }
        });
    });

});

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
	
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    var element = $('ul.nav a').filter(function() {
        return this.href == url || url.href.indexOf(this.href) == 0;
    }).addClass('active').parent().parent().addClass('in').parent();
    if (element.is('li')) {
        element.addClass('active');
    }
});
