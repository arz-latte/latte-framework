var app = {

	API_MODULES : 'api/v1/modules',
	modules : [],

	loadModules : function() {

		$.getJSON(app.API_MODULES + "/all", function(data) {

			app.modules = data.module; // save result

			var $modules = $("#modules");
			$modules.find("tr:has(td)").remove(); // clear

			$.each(app.modules, function(index, module) {
				var $id = $("<td/>").append(module.id);
				var $name = $("<td/>").append(module.name);
				var $row = $('<tr/>').append($id).append($name);
				$modules.append($row);
			});
		});
	},

	addNewModule : function() {
		this.showMessage("add");
	},

	showMessage : function(msg) {
		$("#messageBox").text(msg);
		$("#messageBox").show(100).delay(2000).hide(100);
	}
};

// ===========================================================================
// ready & event handlers
// ===========================================================================
function initFramework() {

	$("#loadModules").on("click", app.loadModules);
}

$(initFramework);
