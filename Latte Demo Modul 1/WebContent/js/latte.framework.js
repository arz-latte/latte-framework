var app = {

	// ===========================================================================
	// menu
	// ===========================================================================

	API_FRAMEWORK : 'http://localhost:8080/latte/api/v1/framework',

	/**
	 * create side bar with menu
	 */
	initSideBar : function() {

		var $menu = $("<div/>", {
			'class' : "navbar navbar-default",
			role : "navigation"
		});

		var $button = $("<button/>", {
			type : "button",
			'class' : "navbar-toggle",
			'data-toggle' : "collapse",
			'data-target' : "#side-navbar-collapse"
		}).append($("<span/>", {
			'class' : "sr-only",
			text : "Menü umschalten"
		}), $("<span/>", {
			'class' : "icon-bar"
		}), $("<span/>", {
			'class' : "icon-bar"
		}), $("<span/>", {
			'class' : "icon-bar"
		}));

		var $navbarHeader = $("<div/>", {
			'class' : "navbar-header",
			role : "navigation"
		}).append($button, $("<span/>", {
			'class' : "visible-xs navbar-brand",
			html : "Men&uuml;"
		}));

		var $navbarCollapse = $("<div/>", {
			'class' : "navbar-collapse collapse",
			id : "side-navbar-collapse"
		}).append($("<ul/>", {
			'class' : "nav navbar-nav",
			id : "side-navbar"
		}));

		$menu.append($navbarHeader, $navbarCollapse);

		$("#sidebar").append($menu);
		app.hideSideBar();
	},

	/**
	 * load all menus from server and store them in localStorage
	 */
	loadModules : function() {

		if (!localStorage.getItem("initialized")) {

			console.log("load modules from framework");

			
			// test
			
			$.ajax({
				crossDomain: true,
				   type: 'GET',
				    url: "http://www.geonames.org/postalCodeLookupJSON?postalcode=10504&country=US&callback=?",
				    async: false,
				    contentType: "application/json",
				    dataType: 'jsonp',
			}).done(function(data) {
				console.log("done: " + data);
				console.log(data);

			}).fail(function(data) {
				console.log("fail: " + data);
			});
			
			
			
			
			$.ajax({
				dataType : "json",
				url : app.API_FRAMEWORK + "/init.json",
				async : false,
			}).done(function(data) {
				console.log("data: " + data);

				localStorage.clear();
				localStorage.setItem("initialized", true);

				// store each module separate
				$.each(data.module, function(index, m) {
					localStorage.setItem('module-' + m.id, JSON.stringify(m));

					// remove sub menu for storing list of modules (without sub menus)
					delete m.subMenu;
				});

				// store list of modules
				localStorage.setItem('modules', JSON.stringify(data.module));

			}).fail(function() {
				app.showErrorMessage("Fehler beim Initialisieren");
			});
		}
	},

	/**
	 * initialize main menu from localStorage
	 * 
	 * @returns
	 */
	initMainMenu : function() {

		var $mainMenuLeft = $("#main-navbar-left");
		$mainMenuLeft.html(""); // clear

		var modules = JSON.parse(localStorage.getItem('modules'));
		$.each(modules, function(index, m) {

			// main menu (module name)
			var $entry = $("<li/>").append($("<a/>", {
				href : m.mainMenu.entry.href,
				text : m.mainMenu.entry.value,
				'data-id' : m.id,
				on : {
					click : function() {
						localStorage.setItem("currentModuleId", m.id);
					//	app.initSubMenu();
					}
				}
			}));

			$mainMenuLeft.append($entry);

			// mark current active module
			if (localStorage.getItem("currentModuleId") == m.id) {
				app.initSubMenu();
			}
		});
	},

	/**
	 * parse menu for single module
	 * 
	 * @param id
	 *            module-id
	 */
	initSubMenu : function() {

		var id = localStorage.getItem("currentModuleId");
		var module = JSON.parse(localStorage.getItem("module-" + id));

		var loc = window.location.href.split('#')[0];

		// main menu
		var $mainMenuLeft = $("#main-navbar-left");
		$mainMenuLeft.find("a").parent().removeClass("active");
		$mainMenuLeft.find("a[data-id=" + module.id + "]").parent().addClass(
				"active");

		// sub menu
		var $subMenu = $("#side-navbar");
		$subMenu.html(""); // clear

		if (module.subMenu) {

			$.each(module.subMenu, function(index, menu) {

				// sub menu (root)
				var e = menu.entry;
				var $entry = $("<li/>").append($("<a/>", {
					href : e.href,
					text : e.value
				}));

				// mark current active menu
				if (e.href == loc) {
					$entry.addClass("active");
				}

				$subMenu.append($entry);

				// sub menu (leaf)
				if (menu.children && menu.children.length >= 0) {

					$.each(menu.children, function(index, submenu) {
						var e = submenu.entry;
						var $entry = $("<li/>", {
							'class' : "submenu"
						}).append($("<a/>", {
							href : e.href,
							text : e.value
						}));

						// mark current active menu
						if (e.href == loc) {
							$entry.addClass("active");
						}

						$subMenu.append($entry);
					});
				}
			});
			app.showSideBar();
		} else {
			app.hideSideBar(); // no side menu for this module
		}
	},

	hideSideBar : function() {
		$("#sidebar").hide();
		$("#content").removeClass("col-sm-9");
		$("#content").addClass("col-sm-12");
	},

	showSideBar : function() {
		$("#sidebar").show();
		$("#content").removeClass("col-sm-12");
		$("#content").addClass("col-sm-9");
	},

	showMessage : function(msg) {
		var $box = $("#message-box");
		$box.removeClass("error");
		$box.html(msg);
		$box.show(100).delay(3000).hide(100);
	},

	showErrorMessage : function(msg) {
		var $box = $("#message-box");
		$box.addClass("error");
		$box.html(msg);
		$box.show(100).delay(3000).hide(100);
	},

	// ==========================================================================
	// websocket functions
	// ===========================================================================

	initWebSocket : function() {

		var url = "ws://" + document.location.host + "/latte/ws";
		var ws = new ReconnectingWebSocket(url, null, {
			debug : false
		});

		ws.onmessage = function(msg) {
			console.log("onmessage" + msg.data);
			
			var data = JSON.parse(msg.data);
			app.showMessage(data.message);
			
			if (data.message == "update") {
				localStorage.removeItem("initialized");
				app.loadModules();
				app.initMainMenu();
			}
			
			if (data.message == "inactive") {				
				//app.loadModules();
			//	app.initMainMenu();
				// get list with menu status...
				// todo disable
			}
		};

		ws.onerror = function(evt) {
			app.showErrorMessage(evt);
		};

		ws.onclose = function() {
			app.showErrorMessage("VERBINDUNG GESCHLOSSEN");
		};
	},

};

// ===========================================================================
// ready & event handlers
// ===========================================================================

function initFramework() {

	app.initSideBar();

	app.loadModules();
	
	app.initMainMenu();

	app.initWebSocket();
}

(function() {
	try {
		support = 'localStorage' in window && window['localStorage'] !== null;
	} catch (e) {
		support = false;
	}
	if (!support) {
		alert("Inkompatibler Browser");
	}
})();

$(initFramework);
