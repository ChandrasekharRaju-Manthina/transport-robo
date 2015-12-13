function scrollToAnchor(aid){
    var aTag = $("a[name='"+ aid +"']");
    $('html,body').animate({scrollTop: aTag.offset().top},'slow');
}

function resetToAdd(form) {
	if(form == "addressForm") {
		$("#form").attr('method','POST');
    	$("#form").attr('data-success-msg','Address has been created successfully.');
    	$("button[type='submit']").html("Add");
    	$("#header").html("Add Address");
    	$("#addAddressLink").hide();
    	$("#form")[0].reset();
	}
}

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
    
    $(document).on("click", "a.update", function(e){
    	e.preventDefault();
    	var data = $("#data-table").DataTable().row($(this).parents("tr")).data();
    	$("input[name='addressLine']").val(data.addressLine);
    	$("input[name='city']").val(data.city);
    	$("input[name='state']").val(data.state);
    	$("input[name='zip']").val(data.zip);
    	$("input[name='country']").val(data.country);
    	$("input[name='id']").val(data.id);
    	$("#form").attr('method','PUT');
    	$("#form").attr('data-success-msg','Address has been updated successfully.');
    	$("button[type='submit']").html("Update");
    	$("#header").html("Update Address");
    	$("#addAddressLink").show();
    	scrollToAnchor("addressMenu");
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
