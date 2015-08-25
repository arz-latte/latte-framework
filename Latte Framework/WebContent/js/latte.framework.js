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

			$.ajax({
				dataType : "json",
				url : app.API_FRAMEWORK + "/init.json",
				async : false,
			}).done(function(data) {

				localStorage.clear();
				localStorage.setItem("initialized", true);
				localStorage.setItem("currentModuleId", 0);

				// store each module separate
				$.each(data.module, function(index, m) {
					localStorage.setItem('module-' + m.id, JSON.stringify(m));
					delete m.menu;
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
				href : "#",// module.url, todo change
				text : m.name,
				'data-id' : m.id,
				on : {
					click : function() {
						localStorage.setItem("currentModuleId", m.id);
						app.initSubMenu();
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
		$mainMenuLeft.find("a[data-id=" + module.id + "]").parent().addClass("active");
		
		// side menu
		var $subMenu = $("#side-navbar");
		$subMenu.html(""); // clear

		if (module.menu) {

			$.each(module.menu, function(index, menu) {

				// menu (root)
				var e = menu.entry;
				var $entry = $("<li/>").append($("<a/>", {
					href : e.url,
					text : e.value
				}));

				// mark current active menu
				if (e.url == loc) {
					$entry.addClass("active");
				}
				
				$subMenu.append($entry);

				if (menu.children && menu.children.length >= 0) {

					// menu (leaf)
					$.each(menu.children, function(index, submenu) {
						var e = submenu.entry;
						var $entry = $("<li/>", {
							'class' : "submenu"
						}).append($("<a/>", {
							href : e.url,
							text : e.value
						}));
						
						// mark current active menu
						if (e.url == loc) {
							$entry.addClass("active");
						}
						
						$subMenu.append($entry);
					});
				}
			});
			app.showSideBar();
		} else {
			// no side menu for this module
			app.hideSideBar();
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
		$box.show(100).delay(2000).hide(100);
	},

	showErrorMessage : function(msg) {
		var $box = $("#message-box");
		$box.addClass("error");
		$box.html(msg);
		$box.show(100).delay(2000).hide(100);
	},

	// ==========================================================================
	// websocket functions
	// ===========================================================================

	initWebSocket : function() {

		var url = "ws://" + document.location.host + "/latte/ws";
		// var ws = new WebSocket(uri);
		var ws = new ReconnectingWebSocket(url, null, {
			debug : false
		});

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
