var appDemo = {

	API_DEMO : 'http://localhost:8080/demo1',
		
	enableEAR : function() {
		app.disableSubMenuByAttribute("type", "war");
		app.enableSubMenuByAttribute("type", "ear");
	},

	enableWAR : function() {
		app.disableSubMenuByAttribute("type", "ear");
		app.enableSubMenuByAttribute("type", "war");
	},

	longTask : function() {
		var currentId = localStorage.getItem("module-id");
		
		$.ajax({
			url : appDemo.API_DEMO + "/longtask",
		}).done(function(data) {
			var currentId = localStorage.getItem("module-id");
			$.ajax({
				url : app.API_LATTE + app.PATH_FRAMEWORK + "/notify.json",
				type : "POST",
				data : currentId,
				contentType : "application/json; charset=UTF-8",
			});
		});
		
		alert("Aufgabe gestartet (10s)");
	}
};

function initDemo() {

	$("#btn-side").on("click", app.toggleSideBar);

	app.disableSubMenuByAttribute("type", "ear");
	app.disableSubMenuByAttribute("type", "war");

	$(".ear").on("click", appDemo.enableEAR);
	$(".war").on("click", appDemo.enableWAR);

	$("#btn-long-task").on("click", appDemo.longTask);

}

$(initDemo);
