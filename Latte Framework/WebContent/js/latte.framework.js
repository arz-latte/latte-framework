var app = {

	// ==========================================================================
	// module
	// ===========================================================================

	API_MODULES : 'api/v1/modules',
	//modules : [],
	//currentModule : null,
	currentId : null,

	loadModules : function() {
		app.leaveEditMode();

		$.getJSON(app.API_MODULES + "/all", function(data) {

			//app.modules = data.module_base; // save result

			var $modules = $("#modules");
			$modules.find("tr:has(td)").remove(); // clear

			$.each(data.module_multiple, function(index, module) {
				var $name = $("<td/>").append(module.name);
				var $version = $("<td/>").append(module.version);
				var $provider = $("<td/>").append(module.provider);
				var $status = $("<td/>").append(module.status);
				var $enabled = $("<td/>").append(module.enabled ? "ja" : "nein");

				var $row = $('<tr/>').attr("data-id", module.id);
				$row.append($name).append($provider).append($version).append(
						$status).append($enabled);

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
		app.currentId = $(this).attr("data-id");
		$.getJSON(app.API_MODULES + "/" + app.currentId, function(data) {

			// fill form
			var m = data.module_single;
			$('[name=input-name]').val(m.name);
			$('[name=input-provider]').val(m.provider);
			$('[name=input-url]').val(m.url);
			$('[name=input-interval]').val(m.interval);
			$('[name=input-enabled]').prop("checked", m.enabled);
		});
	},

	storeModule : function() {
		// copy form to model
		var m = {};
		m.id = app.currentId;
		m.name = $('[name=input-name]').val();
		m.provider = $('[name=input-provider]').val();
		m.url = $('[name=input-url]').val();
		m.interval = $('[name=input-interval]').val();
		m.enabled = $('[name=input-enabled]').prop("checked");
		
		if (m.id > 0) {

			$.ajax({
				url : app.API_MODULES + "/update",
				type : "PUT",
				data : JSON.stringify({"module_single" : m}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.storeModuleCallback(data.result, "Modul aktualisiert");
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		} else {

			$.ajax({
				url : app.API_MODULES + "/create",
				type : "POST",
				data : JSON.stringify({"module_single" : m}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.storeModuleCallback(data.result, "Modul erstellt");
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		}

		return false;
	},

	resetFormValidation : function() {
		// reset form valid
		$('#moduleEditArea input').each(function() {
			$(this).closest(".form-group").removeClass("has-error");
			$(this).next().hide(); // hide glyphicon

			var text = $(this).parent().prev().text();
			$(this).parent().prev().text(text.split(": ")[0]);
		})
	},

	storeModuleCallback : function(data, msg) {

		if (data.validation) {
			app.showErrorMessage("Fehler beim Speichern");
			app.resetFormValidation();

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
			app.loadModules();
		}
	},

	deleteModule : function() {
		var choice = confirm("Sind Sie sicher?");
		if (choice == true) {
			$.ajax({
				url : app.API_MODULES + "/delete/" + app.currentId,
				type : "DELETE",
			}).done(function(data) {
				app.showMessage("Modul gel&ouml;scht");
				app.loadModules();
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		}

		return false;
	},

	restoreModule : function() {
		app.leaveEditMode();
		return false;
	},

	// ==========================================================================
	// helper functions
	// ===========================================================================

	initWebSocket : function() {

		var uri = "ws://" + document.location.host + "/latte/ws";
		var websocket = new WebSocket(uri);

		console.log("init websocket");

		websocket.onmessage = function(msg) {
			var data = JSON.parse(msg.data);
			console.log("onmessage" + msg.data);
			app.showMessage(data.message);
		};

		websocket.onerror = function(evt) {
			app.showErrorMessage(evt);
		};

		websocket.onclose = function() {
			app.showErrorMessage("VERBINDUNG GESCHLOSSEN");
		};

		/*
		 * $("#button").click(function() { var msg = { sender :
		 * $("#name").val(), color : $("#color").val(), message :
		 * $("#message").val() }
		 * 
		 * websocket.send(JSON.stringify(msg)); $("#message").val(""); });
		 */
	},

	// ==========================================================================
	// helper functions
	// ===========================================================================

	showMessage : function(msg) {
		var $box = $("#messageBox");
		$box.removeClass("error");
		$box.html(msg);
		$box.show(100).delay(2000).hide(100);
	},

	showErrorMessage : function(msg) {
		var $box = $("#messageBox");
		$box.addClass("error");
		$box.html(msg);
		$box.show(100).delay(2000).hide(100);
	},

	enterEditMode : function() {
		app.resetFormValidation();

		$("#moduleListArea").hide();
		$("#moduleEditArea").show();

		$('#moduleEditArea form').trigger("reset");
		app.currentId = null;
	},

	leaveEditMode : function() {
		$("#moduleEditArea").hide();
		$("#moduleListArea").show();
		app.currentId = null;
	},

};

// ===========================================================================
// classes
// ===========================================================================
/*
function Module() {
	this.id = null;
	this.name = null;
	this.provider = null;
	this.version = null;
	this.status = null;
	this.url = null;
	this.checkInterval = null;
	this.enabled = null;
}
Module.prototype.init = function(id, name, provider, version, status, url,
		checkInterval, enabled) {
	this.id = id;
	this.name = name;
	this.provider = provider;
	this.version = version;
	this.status = status;
	this.url = url;
	this.checkInterval = checkInterval;
	this.enabled = enabled;
};
Module.prototype.createJSON = function() {
	return JSON.stringify({
		"module_single" : this
	});
};*/

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

	app.initWebSocket();

	app.loadModules();
}

$(initFramework);
