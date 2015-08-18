var app = {

	API_MODULES : 'api/v1/modules',
	modules : [],
	currentModule : null,

	loadModules : function() {
		app.leaveEditMode();
		
		$.getJSON(app.API_MODULES + "/all", function(data) {

			app.modules = data.module; // save result

			var $modules = $("#modules");
			$modules.find("tr:has(td)").remove(); // clear

			$.each(app.modules, function(index, module) {
				var $id = $("<td/>").append(module.id);
				var $name = $("<td/>").append(module.name);
				var $row = $('<tr/>').attr("data-id", module.id).append($id)
						.append($name);
				$modules.append($row);
			});
		});
	},

	addNewModule : function() {
		$("#btnDeleteModule").hide();
		app.enterEditMode();
	},

	showModule : function() {
		$("#btnDeleteModule").show();
		app.enterEditMode();

		var id = $(this).attr("data-id");
		$.getJSON(app.API_MODULES + "/" + id, function(data) {
			var m = data.module[0];

			app.currentModule = new Module();
			app.currentModule.init(m.id, m.name);

			$('[name=name]').val(app.currentModule.name);
		});
	},

	storeModule : function() {
		app.currentModule.name = $('[name=name]').val();

		if (app.currentModule && app.currentModule.id > 0) {

			$.ajax({
				url : app.API_MODULES + "/update",
				type : "PUT",
				data : app.currentModule.createJSON(),
				contentType : "application/json; charset=UTF-8",
				complete : function(data) {
					app.showMessage("Modul aktualisiert");
					app.loadModules();
				}
			});
		} else {

			$.ajax({
				url : app.API_MODULES + "/create",
				type : "POST",
				data : app.currentModule.createJSON(),
				contentType : "application/json; charset=UTF-8",
				complete : function(data) {
					app.showMessage("Modul erstellt");
					app.loadModules();
				}
			});
		}
		
		return false;
	},

	deleteModule : function() {

		$.ajax({
			url : app.API_MODULES + "/delete/" + app.currentModule.id,
			type : "DELETE",
			complete : function(data) {
				app.showMessage("Modul gelöscht");
				app.loadModules();
			}
		});
		
		return false;
	},

	restoreModule : function() {
		app.leaveEditMode();
		return false;
	},

	// ==========================================================================
	//  helper functions
	// ===========================================================================
			
	showMessage : function(msg) {
		$("#messageBox").text(msg);
		$("#messageBox").show(100).delay(2000).hide(100);
	},

	enterEditMode : function() {
		$("#moduleListArea").hide();
		$("#moduleEditArea").show();

		$('#moduleEditArea form').trigger("reset");
		app.currentModule = new Module();
	},
	
	leaveEditMode : function() {
		$("#moduleEditArea").hide();		
		$("#moduleListArea").show();
		app.currentModule = null;
	},
	
};

// ===========================================================================
// classes
// ===========================================================================

function Module() {
	this.id = null;
	this.name = null;
}
Module.prototype.init = function(id, name) {
	this.id = id;
	this.name = name;
};
Module.prototype.createJSON = function() {
	return JSON.stringify({
		"module" : this
	});
};

// ===========================================================================
// ready & event handlers
// ===========================================================================
function initFramework() {

	$("#btnLoadModules").on("click", app.loadModules);
	$("#btnAddNewModule").on("click", app.addNewModule);

	$("#btnStoreModule").on("click", app.storeModule);
	$("#btnDeleteModule").on("click", app.deleteModule);
	$("#btnRestoreModule").on("click", app.restoreModule);

	$("#moduleListArea tbody").on("click", "tr", app.showModule);

	app.loadModules();
}

$(initFramework);
