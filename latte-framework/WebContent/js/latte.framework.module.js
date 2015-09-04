/**
 * version 28.08.2015
 */
var appModule = {

	API_MODULES : 'api/v1/modules',

	currentId : null,

	loadModules : function() {
		appAdmin.leaveEditMode();

		$.getJSON(appModule.API_MODULES + "/all.json", function(data) {

			var $modules = $("#modules");
			$modules.find("tr:has(td)").remove(); // clear

			$.each(data.module, function(index, module) {
				var $name = $("<td/>").append(module.name);
				
				version = "";
				if (module.lastmodified) {
					var dt = new Date(module.lastmodified);					
					var h = dt.getHours() < 10 ? "0" + dt.getHours() : dt.getHours();
					var m = dt.getMinutes() < 10 ? "0" + dt.getMinutes() : dt.getMinutes();
					var s = dt.getSeconds() < 10 ? "0" + dt.getSeconds() : dt.getSeconds();					
					var d = dt.getDate() < 10 ? "0" + dt.getDate() : dt.getDate();
					var n = dt.getMonth() < 9 ? "0" + (dt.getMonth()+1) : dt.getMonth()+1;					
					version = d + "." + n + "." + dt.getFullYear() + " " + h + ":" + m +":" + s;	
				}
				var $version = $("<td/>").append(version);
				
				var $provider = $("<td/>").append(module.provider);
				var $running = $("<td/>")
						.append(module.running ? "ja" : "nein");
				var $enabled = $("<td/>")
						.append(module.enabled ? "ja" : "nein");

				var $row = $("<tr/>").attr("data-id", module.id);
				$row.append($name).append($provider).append($version).append(
						$running).append($enabled);

				$modules.append($row);
			});

			$("#list-filter").keyup();
		});
	},

	addNewModule : function() {
		$("#btn-delete-module").hide();
		appAdmin.enterEditMode();
		appModule.currentId = null;	
	},

	showModule : function() {
		$("#btn-delete-module").show();
		appAdmin.enterEditMode();
		appModule.currentId = null;

		// load module details
		appModule.currentId = $(this).attr("data-id");
		$.getJSON(appModule.API_MODULES + "/get.json/" + appModule.currentId,
				function(data) {

					// fill form
					var m = data.module;
					$("[name=input-name]").val(m.name);
					$("[name=input-provider]").val(m.provider);
					$("[name=input-url]").val(m.url);
					$("[name=input-interval]").val(m.interval);
					$("[name=input-enabled]").prop("checked", m.enabled);
				});
	},

	storeModule : function() {
		// copy form to model
		var m = {};
		m.id = appModule.currentId;
		m.name = $("[name=input-name]").val();
		m.provider = $("[name=input-provider]").val();
		m.url = $("[name=input-url]").val();
		m.interval = $("[name=input-interval]").val();
		m.enabled = $("[name=input-enabled]").prop("checked");

		if (m.id > 0) {

			$.ajax({
				url : appModule.API_MODULES + "/update.json",
				type : "PUT",
				data : JSON.stringify({
					"module" : m
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showSuccessMessage("Modul aktualisiert");
				appModule.loadModules();
			}).fail(function(error) {
				appAdmin.validateData(error);
			});
		} else {

			$.ajax({
				url : appModule.API_MODULES + "/create.json",
				type : "POST",
				data : JSON.stringify({
					"module" : m
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showSuccessMessage("Modul erstellt");
				appModule.loadModules();
			}).fail(function(error) {
				appAdmin.validateData(error);
			});
		}

		return false;
	},

	deleteModule : function() {
		var choice = confirm("Sind Sie sicher?");
		if (choice == true) {
			$.ajax(
					{
						url : appModule.API_MODULES + "/delete.json/"
								+ appModule.currentId,
						type : "DELETE",
					}).done(function(data) {
				app.showSuccessMessage("Modul gel&ouml;scht");
				appModule.loadModules();
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		}

		return false;
	},

	restoreModule : function() {
		appAdmin.leaveEditMode();
		appModule.currentId = null;
		return false;		
	},

};

// ===========================================================================
// ready & event handlers
// ===========================================================================
function initModule() {

	appAdmin.init();

	$("#btn-load").on("click", appModule.loadModules);
	$("#btn-add").on("click", appModule.addNewModule);

	$("#btn-store").on("click", appModule.storeModule);
	$("#btn-delete").on("click", appModule.deleteModule);
	$("#btn-restore").on("click", appModule.restoreModule);

	$("#list-area tbody").on("click", "tr", appModule.showModule);

	appModule.loadModules();
}

$(initModule);
