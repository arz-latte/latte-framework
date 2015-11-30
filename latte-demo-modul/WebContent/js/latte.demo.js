var appDemo = {

	API_DEMO : 'http://localhost:8080/demo',
		
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
			app.notifyFramework();
		});
		
		alert("Aufgabe gestartet (10s)");
	},
	
	functionTest : function(data) {
		alert("function test: " + data);
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
