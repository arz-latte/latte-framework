var app = {

	// ===========================================================================
	// menu
	// ===========================================================================

	API_FRAMEWORK : 'api/v1/framework',

	loadMenus : function() {
		console.log("load menus");
		
		$.getJSON(app.API_FRAMEWORK + "/init.json", function(data) {

			var $mainMenuLeft = $("#main-navbar-left");
			var $subMenu = $("#main-navbar-right");
			$mainMenuLeft.html(""); // clear

			$.each(data.module, function(index, module) {

				// main menu (module name)
				var $entry = $("<li/>").append($("<a/>").attr("href", "#").append(module.name));
				$mainMenuLeft.append($entry);
				
				// sub menu
				$.each(module.menu, function(index, menu) {
					console.log(menu);
					
					if (menu.children && menu.children.length >= 0) {
						var e = menu.entry; 					// todo position...
						var $entry = $("<li/>").append($("<a/>", { href: e.url, text: e.value }));

						var $a = $("<a/>", {
							href: "#",
							'class' : "dropdown-toogle",
							'data-toggle' : "dropdown",
							role : "button",
							'aria-haspopup' : "true",
							'aria-expanded' :"false",
							'text' : e.value + " "
						}).append($("<span/>", {'class':"caret"}));
						
						var $ul = $("<ul/>", { 'class' : "dropdown-menu" });
						
						$.each(menu.children, function(index, submenu) {
							var e = submenu.entry;
							var $entry = $("<li/>").append($("<a/>", { href: e.url, text: e.value }));	
							$ul.append($entry);
						});
						
						var $dropdown = $("<li/>", { 'class' : "dropdown" }).append($a, $ul);

						$subMenu.append($dropdown);

					} else {
						var e = menu.entry; 					// todo position...
						var $entry = $("<li/>").append($("<a/>", { href: e.url, text: e.value }));
						$subMenu.append($entry);
					}
					
				});
			});
		});
		
	},
		
	// ==========================================================================
	// module
	// ===========================================================================

	API_MODULES : 'api/v1/modules',
	currentId : null,

	loadModules : function() {
		app.leaveEditMode();

		$.getJSON(app.API_MODULES + "/all", function(data) {

			var $modules = $("#modules");
			$modules.find("tr:has(td)").remove(); // clear

			$.each(data.modules, function(index, module) {
				var $name = $("<td/>").append(module.name);
				var $version = $("<td/>").append(module.version);
				var $provider = $("<td/>").append(module.provider);
				var $status = $("<td/>").append(module.status);
				var $enabled = $("<td/>").append(module.enabled ? "ja" : "nein");

				var $row = $("<tr/>").attr("data-id", module.id);
				$row.append($name).append($provider).append($version).append(
						$status).append($enabled);

				$modules.append($row);
			});
			
			$("#moduleFilter").keyup();
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
		m.id = app.currentId;
		m.name = $("[name=input-name]").val();
		m.provider = $("[name=input-provider]").val();
		m.url = $("[name=input-url]").val();
		m.interval = $("[name=input-interval]").val();
		m.enabled = $("[name=input-enabled]").prop("checked");
		
		if (m.id > 0) {

			$.ajax({
				url : app.API_MODULES + "/update",
				type : "PUT",
				data : JSON.stringify({"module" : m}),
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
				data : JSON.stringify({"module" : m}),
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
		$("#moduleEditArea input").each(function() {
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
	
	filterModule : function() {
        var rex = new RegExp($(this).val(), "i");
        $("tbody tr").hide();
        $("tbody tr").filter(function () {
            return rex.test($(this).text());
        }).show();
	},

	clearModuleFilter : function() {
		$("#moduleFilter").val("");
		$("tbody tr").show();
	},
	
	// ==========================================================================
	// websocket functions
	// ===========================================================================

	initWebSocket : function() {

		var url = "ws://" + document.location.host + "/latte/ws";
		//var ws = new WebSocket(uri);
		var ws = new ReconnectingWebSocket(url, null, {debug: true});

		ws.onmessage = function(msg) {
			var data = JSON.parse(msg.data);
			console.log("onmessage" + msg.data);
			app.showMessage(data.message);
		};

		ws.onerror = function(evt) {
			app.showErrorMessage(evt);
		};

		ws.onclose = function() {
			app.showErrorMessage("VERBINDUNG GESCHLOSSEN");
		};

		/*
		 * $("#button").click(function() { var msg = { sender :
		 * $("#name").val(), color : $("#color").val(), message :
		 * $("#message").val() }
		 * 
		 * ws.send(JSON.stringify(msg)); $("#message").val(""); });
		 */
	},

	// ==========================================================================
	// helper functions
	// ===========================================================================

	showMessage : function(msg) {
		var $box = $("#message-box");
		$box.removeClass("error");
		$box.html(msg);
		$box.show(100).delay(2000).hide(100);
	},

	showErrorMessage : function(msg) {
		var $box = $("#message-box");
		$box.addClass("error");
		$box.html(msg);
		$box.show(100).delay(2000).hide(100);
	},

	enterEditMode : function() {
		app.resetFormValidation();

		$("#moduleListArea").hide();
		$("#moduleEditArea").show();

		$("#moduleEditArea form").trigger("reset");
		app.currentId = null;
	},

	leaveEditMode : function() {
		$("#moduleEditArea").hide();
		$("#moduleListArea").show();
		app.currentId = null;
	},

};

// ===========================================================================
// ready & event handlers
// ===========================================================================
function initFramework() {
	
	app.loadMenus();

	$("#btnClearModuleFilter").on("click", app.clearModuleFilter);
	$("#moduleFilter").on("keyup", app.filterModule);	

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
