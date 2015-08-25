var appModule = {

	API_MODULES : 'api/v1/modules',
	
	currentId : null,

	loadModules : function() {
		appModule.leaveEditMode();

		$.getJSON(appModule.API_MODULES + "/all.json", function(data) {

			var $modules = $("#modules");
			$modules.find("tr:has(td)").remove(); // clear

			$.each(data.modules, function(index, module) {
				var $name = $("<td/>").append(module.name);
				var $version = $("<td/>").append(module.version);
				var $provider = $("<td/>").append(module.provider);
				var $status = $("<td/>").append(module.status);
				var $enabled = $("<td/>")
						.append(module.enabled ? "ja" : "nein");

				var $row = $("<tr/>").attr("data-id", module.id);
				$row.append($name).append($provider).append($version).append(
						$status).append($enabled);

				$modules.append($row);
			});

			$("#module-filter").keyup();
		});
	},

	addNewModule : function() {
		$("#btn-delete-module").hide();
		appModule.enterEditMode();
	},

	showModule : function() {
		$("#btn-delete-module").show();
		appModule.enterEditMode();

		// load module details
		appModule.currentId = $(this).attr("data-id");
		$.getJSON(appModule.API_MODULES + "/get.json/" + appModule.currentId,
				function(data) {

					// fill form
					var m = data.module;
					$("[name=input-name]").val(m.name);
					$("[name=input-provider]").val(m.provider);
					$("[name=input-url-status]").val(m.urlStatus);
					$("[name=input-url-index]").val(m.urlIndex);
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
		m.urlStatus = $("[name=input-url-status]").val();
		m.urlIndex = $("[name=input-url-index]").val();
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
				appModule.storeModuleCallback(data.result, "Modul aktualisiert");
			}).fail(function() {
				app.showErrorMessage("Fehler");
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
				appModule.storeModuleCallback(data.result, "Modul erstellt");
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		}

		return false;
	},

	resetFormValidation : function() {
		// reset form valid
		$("#module-edit-area input").each(function() {
			$(this).closest(".form-group").removeClass("has-error");
			$(this).next().hide(); // hide glyphicon

			var text = $(this).parent().prev().text();
			$(this).parent().prev().text(text.split(": ")[0]);
		})
	},

	storeModuleCallback : function(data, msg) {

		if (data.validation) {
			app.showErrorMessage("Fehler beim Speichern");
			appModule.resetFormValidation();

			// mark invalid
			var entries = [];
			if ($.isArray(data.validation.entry)) {
				$.merge(entries, data.validation.entry);
			} else {
				entries.push(data.validation.entry);
			}
			$.each(entries, function(i, e) {
				// mark input as invalid
				var $input = $("[name=input-" + e.key + "]");
				$input.closest(".form-group").addClass("has-error");

				$input.next().show(); // show glyphicon

				// set label text
				var text = $input.parent().prev().text();
				$input.parent().prev().text(text + ": " + e.value);
			});

		} else {
			app.showMessage(msg);
			appModule.loadModules();
		}
	},

	deleteModule : function() {
		var choice = confirm("Sind Sie sicher?");
		if (choice == true) {
			$.ajax({
				url : appModule.API_MODULES + "/delete.json/" + appModule.currentId,
				type : "DELETE",
			}).done(function(data) {
				app.showMessage("Modul gel&ouml;scht");
				appModule.loadModules();
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		}

		return false;
	},

	restoreModule : function() {
		appModule.leaveEditMode();
		return false;
	},

	filterModule : function() {
		var rex = new RegExp($(this).val(), "i");
		$("tbody tr").hide();
		$("tbody tr").filter(function() {
			return rex.test($(this).text());
		}).show();
	},

	clearModuleFilter : function() {
		$("#module-filter").val("");
		$("tbody tr").show();
	},

	enterEditMode : function() {
		appModule.resetFormValidation();

		$("#module-list-area").hide();
		$("#module-edit-area").show();

		$("#module-edit-area form").trigger("reset");
		appModule.currentId = null;
	},

	leaveEditMode : function() {
		$("#module-edit-area").hide();
		$("#module-list-area").show();
		appModule.currentId = null;
	},

};

// ===========================================================================
// ready & event handlers
// ===========================================================================
function initModule() {

	// init module

	$("#btn-clear-module-filter").on("click", appModule.clearModuleFilter);
	$("#module-filter").on("keyup", appModule.filterModule);

	$("#btn-load-modules").on("click", appModule.loadModules);
	$("#btn-add-module").on("click", appModule.addNewModule);

	$("#btn-store-module").on("click", appModule.storeModule);
	$("#btn-delete-module").on("click", appModule.deleteModule);
	$("#btn-restore-module").on("click", appModule.restoreModule);

	$("#module-list-area tbody").on("click", "tr", appModule.showModule);
	
	appModule.loadModules();

}

$(initModule);
