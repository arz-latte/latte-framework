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
				var $name = $("<td/>").append(module.name);
				var $version = $("<td/>").append(module.version);
				var $status = $("<td/>").append(module.status);
				var $enabled = $("<td/>")
						.append(module.enabled ? "ja" : "nein");

				var $row = $('<tr/>').attr("data-id", module.id);
				$row.append($name).append($version).append($status).append(
						$enabled);

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

		// load module details
		var id = $(this).attr("data-id");
		$.getJSON(app.API_MODULES + "/" + id, function(data) {
			var m = data.module[0];

			app.currentModule = new Module();
			app.currentModule.init(m.id, m.name, m.version, m.status, m.url,
					m.checkInterval, m.enabled);

			// fill form
			$('[name=name]').val(app.currentModule.name);
			$('[name=version]').val(app.currentModule.version);
			$('[name=url]').val(app.currentModule.url);
			$('[name=checkInterval]').val(app.currentModule.checkInterval);
			$('[name=enabled]').prop("checked", app.currentModule.enabled);
		});
	},

	storeModule : function() {
		// copy form to model
		app.currentModule.name = $('[name=name]').val();
		app.currentModule.version = $('[name=version]').val();
		app.currentModule.url = $('[name=url]').val();
		app.currentModule.checkInterval = $('[name=checkInterval]').val();
		app.currentModule.enabled = $('[name=enabled]').prop("checked");
		console.log($('[name=enabled]').prop("checked"));

		if (app.currentModule && app.currentModule.id > 0) {

			$.ajax({
				url : app.API_MODULES + "/update",
				type : "PUT",
				data : app.currentModule.createJSON(),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showMessage("Modul aktualisiert");
				app.loadModules();
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		} else {

			$.ajax({
				url : app.API_MODULES + "/create",
				type : "POST",
				data : app.currentModule.createJSON(),
				contentType : "application/json; charset=UTF-8",				
			}).done(function(data) {
				app.showMessage("Modul erstellt");
				app.loadModules();
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		}

		return false;
	},

	deleteModule : function() {

		$.ajax({
			url : app.API_MODULES + "/delete/" + app.currentModule.id,
			type : "DELETE",
		}).done(function(data) {
			app.showMessage("Modul gelöscht");
			app.loadModules();
		}).fail(function() {
			app.showErrorMessage("Fehler");
		});

		return false;
	},

	restoreModule : function() {
		app.leaveEditMode();
		return false;
	},

	// ==========================================================================
	// helper functions
	// ===========================================================================

	showMessage : function(msg) {
		var $box = $("#messageBox");
		$box.removeClass("error");
		$box.text(msg);
		$box.show(100).delay(2000).hide(100);
	},

	showErrorMessage : function(msg) {
		var $box = $("#messageBox");
		$box.addClass("error");
		$box.text(msg);
		$box.show(100).delay(2000).hide(100);
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
	this.version = null;
	this.status = null;
	this.url = null;
	this.checkInterval = null;
	this.enabled = null;
}
Module.prototype.init = function(id, name, version, status, url, checkInterval,
		enabled) {
	this.id = id;
	this.name = name;
	this.version = version;
	this.status = status;
	this.url = url;
	this.checkInterval = checkInterval;
	this.enabled = enabled;
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
