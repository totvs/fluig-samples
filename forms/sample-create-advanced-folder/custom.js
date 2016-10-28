$(document).ready(function() {	
	$("input[type=zoom").each(function(){		
		
		$(document).on('fluig.filter.itemAdded', '#' + $(this).attr('id'), function(ev){
			$(this).parent().children().find('.tt-input').attr('placeholder', '');
		});
		
		$(document).on('fluig.filter.itemRemoved', '#' + $(this).attr('id'), function(ev){
			$(this).parent().children().find('.tt-input').attr('placeholder', $(this).attr('placeholder'));
		});
	});
	
});
