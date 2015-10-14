/**
 * version 04.10.2015
 */
'use strict';

var latte = {
		
	URL_API : "/latte/api",

	call : function (path, message){
		return $.getJSON(path, message);
	},
	ajax : function (message){
		return $.ajax(message);
	},
		
}
var app = {
	
	API_LATTE : 'http://localhost:8080/latte',
	PATH_INDEX : '/index.html',
	PATH_FRAMEWORK : '/api/framework',

	API_WEBSOCKET : 'ws://localhost:8080/latte/ws',

	// ==========================================================================
	// framework API
	// ===========================================================================

	/**
	 * show sidebar with submenu
	 */
	showSideBar : function() {
		$("#sidebar").show();
		$("#content").removeClass("col-sm-12");
		$("#content").addClass("col-sm-9");
	},

	/**
	 * hide sidebar with submenu (use full width for content)
	 */
	hideSideBar : function() {
		$("#sidebar").hide();
		$("#content").removeClass("col-sm-9");
		$("#content").addClass("col-sm-12");
	},

	/**
	 * toggle sidebar with submenu
	 */
	toggleSideBar : function() {
		if ($("#sidebar").is(":visible")) {
			app.hideSideBar();
		} else {
			app.showSideBar();
		}
	},

	/**
	 * enable all submenus by specific attribute and value
	 * 
	 * @param attribute
	 * @param value
	 */
	enableSubMenuByAttribute : function(attribute, value) {

		$("#side-menu").find("li").each(function(i, li) {
			var $li = $(li);
			if ($li.data(attribute)) {
				var values = $li.data(attribute).split(",");				
				if ($.inArray(value, values) >= 0 && !$li.data("disabled")) {
					$li.removeClass("disabled");
				}
			}
		});
	},

	/**
	 * disable all submenus by specific attribute and value
	 * 
	 * @param attribute
	 * @param value
	 */
	disableSubMenuByAttribute : function(attribute, value) {

		$("#side-menu").find("li").each(function(i, li) {
			var $li = $(li);
			if ($li.data(attribute) == value && !$li.data("disabled")) {
				$li.addClass("disabled");
			}
		});
	},

	/**
	 * enable all submenu entries
	 * 
	 * @param moduleId
	 */
	activateSubMenu : function(moduleId) {
		$("#main-menu-left").find("[module-id=" + moduleId + "]").parent()
				.removeClass("disabled");
		if (localStorage.getItem("module-id") == moduleId) {
			$("#side-menu").find("li").removeClass("disabled");
		}
	},

	/**
	 * disable all submenu entries
	 * 
	 * @param moduleId
	 */
	deactivateSubMenu : function(moduleId) {
		$("#main-menu-left").find("[module-id=" + moduleId + "]").parent()
				.addClass("disabled");
		if (localStorage.getItem("module-id") == moduleId) {
			$("#side-menu").find("li").addClass("disabled");
		}
	},

	/**
	 * show success message to user
	 * 
	 * @param msg
	 */
	showSuccessMessage : function(msg) {
		var $box = $("#message-box");
		$box.removeClass("alert-info");
		$box.removeClass("alert-warning");
		$box.removeClass("alert-danger");
		$box.addClass("alert-success");
		$box.html(msg);
		$box.show(100).delay(3000).hide(100);
	},

	/**
	 * show info message to user
	 * 
	 * @param msg
	 */
	showInfoMessage : function(msg) {
		var $box = $("#message-box");
		$box.removeClass("alert-success");
		$box.removeClass("alert-warning");
		$box.removeClass("alert-danger");
		$box.addClass("alert-info");
		$box.html(msg);
		$box.show(100).delay(3000).hide(100);
	},

	/**
	 * show warning message to user
	 * 
	 * @param msg
	 */
	showWarningMessage : function(msg) {
		var $box = $("#message-box");
		$box.removeClass("alert-success");
		$box.removeClass("alert-info");
		$box.removeClass("alert-danger");
		$box.addClass("alert-warning");
		$box.html(msg);
		$box.show(100).delay(3000).hide(100);
	},

	/**
	 * show error message to user
	 * 
	 * @param msg
	 */
	showErrorMessage : function(msg) {
		var $box = $("#message-box");
		$box.removeClass("alert-success");
		$box.removeClass("alert-info");
		$box.removeClass("alert-warning");
		$box.addClass("alert-danger");
		$box.html(msg);
		$box.show(100).delay(3000).hide(100);
	},

	// ===========================================================================
	// layout creation
	// ===========================================================================

	/**
	 * create nav bar with main menu
	 */
	createMainNavBar : function() {

		var $menu = $("<nav/>", {
			'class' : "navbar navbar-default"
		});

		var $div = $("<div/>", {
			'class' : "container-fluid"
		});

		var $button = $("<button/>", {
			type : "button",
			'class' : "navbar-toggle collapsed",
			'data-toggle' : "collapse",
			'data-target' : "#main-navbar-collapse",
			'aria-expanded' : "false"
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
			'class' : "navbar-header"
		}).append($button, $("<a/>", {
			'class' : "navbar-brand",
			href : app.API_LATTE + app.PATH_INDEX,
			text : "Latte",
			on : {
				click : function() {
					localStorage.removeItem("module-id");
				}
			}
		}));

		var $mainMenuLeft = $("<ul/>", {
			'class' : "nav navbar-nav",
			id : "main-menu-left"
		});

		var $mainMenuRight = $("<ul/>", {
			'class' : "nav navbar-nav navbar-right",
			id : "main-menu-right"
		});

		var $navbarCollapse = $("<div/>", {
			'class' : "navbar-collapse collapse",
			id : "main-navbar-collapse"
		}).append($mainMenuLeft, $mainMenuRight);

		$div.append($navbarHeader, $navbarCollapse);

		$menu.append($div);

		$("#main-navbar-placeholder").replaceWith($menu);
	},

	/**
	 * create and place message box
	 */
	createMessageBox : function() {

		var $messageBox = $("<div/>", {
			'class' : "alert",
			role : "alert",
			id : "message-box"
		});
		$messageBox.hide();

		$("#message-box-placeholder").replaceWith($messageBox);
	},

	/**
	 * create and place content area and sidebar
	 */
	createContentAndSideBarArea : function() {

		var $container = $("<div/>", {
			'class' : "container-fluid"
		});

		var $row = $("<div/>", {
			'class' : "row"
		});

		var $sidebar = $("<div/>", {
			'class' : "col-sm-3",
			id : "sidebar"
		});

		var $content = $("<div/>", {
			'class' : "col-sm-9",
			id : "content"
		});

		// fill with predefined content from html-file
		$content.append($("#content-placeholder").html());

		$row.append($sidebar, $content);

		$container.append($row);

		$("#content-placeholder").replaceWith($container);
	},

	/**
	 * create empty side bar for menu
	 */
	createSideNavBar : function() {

		var $menu = $("<div/>", {
			'class' : "navbar navbar-default"
		});

		var $button = $("<button/>", {
			type : "button",
			'class' : "navbar-toggle collapsed",
			'data-toggle' : "collapse",
			'data-target' : "#side-navbar-collapse",
			'aria-expanded' : "false"
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
			id : "side-menu"
		}));

		$menu.append($navbarHeader, $navbarCollapse);

		$("#sidebar").append($menu);
		app.hideSideBar();
	},

	// ===========================================================================
	// initialize framework
	// ===========================================================================

	/**
	 * load user data from server and store them in localStorage
	 */
	loadUser : function() {

		if (localStorage.getItem("initialized")) {
			app.initUserMenu();
			return;
		}

		$.ajax({
			url : app.API_LATTE + app.PATH_FRAMEWORK + "/user.json",
			async : false,
		}).done(function(data) {
			localStorage.setItem("user", JSON.stringify(data.user));
			app.initUserMenu();
		}).fail(function() {
			app.showErrorMessage("Fehler beim Initialisieren");
		});
	},

	/**
	 * initialize user menu (located in main/top menu right side)
	 */
	initUserMenu : function() {
		var user = JSON.parse(localStorage.getItem('user'));

		var $dropDownLink = $("<a/>", {
			id : "user-menu",
			href : "#",
			'class' : "dropdown-toggle",
			'data-toggle' : "dropdown",
			role : "button",
			'aria-haspopup' : "true",
			'aria-expanded' : "true"
		}).append(user.firstName, " ", $("<span/>", {
			'class' : "caret"
		}));

		// logout
		var $btnLogout = $("<a/>", {
			href : "#",
			id : "btn-logout",
			text : "Abmelden"
		}).on("click", function() {
			$.get(app.API_LATTE + "/logout", function(data) {
				localStorage.clear();
				window.location.replace(app.API_LATTE  + "/index.html");
			});
		});

		var $dropDownMenu = $("<ul/>", {
			'class' : "dropdown-menu"
		}).append($("<li/>").append($btnLogout));

		var $mainMenuRight = $("#main-menu-right");
		$mainMenuRight.html(""); // clear
		$mainMenuRight.append($("<li/>", {
			'class' : "dropdown"
		}).append($dropDownLink, $dropDownMenu));
	},

	/**
	 * load all modules and menus from server and store them in localStorage
	 */
	loadModules : function() {

		if (localStorage.getItem("initialized")) {
			app.initMainMenu();
			return;
		}

		$.ajax({
			url : app.API_LATTE + app.PATH_FRAMEWORK + "/init.json",
			async : true,
		}).done(function(data) {

			var ids = [];

			// store each module separate
			$.each(data.module, function(index, m) {
				localStorage.setItem("module-" + m.id, JSON.stringify(m));
				ids.push("" + m.id); // convert to string -> $.inArray is strict
			});

			// store ids of modules
			localStorage.setItem("modules", JSON.stringify(ids));

			// check if current module still guilty, else remove selection
			var id = localStorage.getItem("module-id");
			if (id != null && $.inArray(id, ids) == -1) {
				localStorage.removeItem("module-id");
			}

			app.initMainMenu();

		}).fail(function() {
			app.showErrorMessage("Fehler beim Initialisieren");
		});
	},

	/**
	 * initialize main menu
	 */
	initMainMenu : function() {

		var $mainMenuLeft = $("#main-menu-left");
		$mainMenuLeft.html(""); // clear

		app.hideSideBar(); // hide side menu

		var ids = JSON.parse(localStorage.getItem('modules'));
		$.each(ids, function(index, id) {
			var module = JSON.parse(localStorage.getItem('module-' + id));
			
			// main menu (module name)
			var $entry = $("<li/>").append($("<a/>", {
				href : module.menu.url,
				'module-id' : module.id
			}).append(module.menu.name + " ", $("<span/>", {
				'class' : "badge"
			})));
			
			$entry.on("click", function() {
				if (!$(this).hasClass("disabled")) {
					localStorage.setItem("module-id", module.id);
					app.initSubMenu();
				} else {
					return false;
				}
			});

			// module currently inactive
			if (!module.running) {
				$entry.addClass("disabled");
			}

			$mainMenuLeft.append($entry);

			// check location with module url			
			if (window.location.pathname == module.menu.url || 
					window.location.pathname + "index.html" == module.menu.url) {
				localStorage.setItem("module-id", module.id);
			}
			
			// check locations with submenu urls
			$.each(module.menu.submenu, function(index, submenu) {
				if (window.location.pathname == submenu.url) {
					localStorage.setItem("module-id", module.id);
				}
			});
			
			// initialize submenu of current active module
			if (localStorage.getItem("module-id") == module.id) {
				app.initSubMenu();
			}
		});

	},

	/**
	 * initializes sub menu for single module
	 */
	initSubMenu : function() {

		var id = localStorage.getItem("module-id");

		// mark current active main menu entry
		var $mainMenuLeft = $("#main-menu-left");
		$mainMenuLeft.find("a").parent().removeClass("active");
		$mainMenuLeft.find("a[module-id=" + id + "]").parent().addClass("active");

		// create sub menu
		var $subMenu = $("#side-menu");
		$subMenu.html(""); // clear

		var module = JSON.parse(localStorage.getItem("module-" + id));
		var submenu = module.menu.submenu;
		if (submenu) {
			if (submenu.length > 0) {
				$.each(submenu, function(index, menu) {
					app.appendSubMenuRec($subMenu, menu, 0);
				});
			} else {
				app.appendSubMenuRec($subMenu, submenu, 0);
			}

			app.showSideBar();
		} else {
			app.hideSideBar(); // no side menu for this module
		}

		// deactivate sub menu if module is currently inactive
		if (!module.running) {
			app.deactivateSubMenu(id);
		}
	},

	/**
	 * helper for creating submenu
	 * 
	 * @param $subMenu
	 *            target element
	 * @param menu
	 *            datastructure with menu informations
	 * @param level
	 *            information about submenu level
	 */
	appendSubMenuRec : function($subMenu, menu, level) {
	
		// sub menu entry
		var $entry = $("<li/>").append($("<a/>", {
			href : menu.url ? menu.url : "#",
			text : menu.name,
		}));
		
		// add javacript function
		if (menu.script) {
			$entry.on("click", function() {
				if (!$(this).hasClass("disabled")) {				
					new Function(menu.script).call(this);
				} else {
					return false;
				}
			});
		}

		// mark current active menu
		var loc = window.location.href.split('#')[0];
		if (menu.url == loc) {
			$entry.addClass("active");
		}

		// mark submenu
		if (level > 0) {
			$entry.addClass("submenu submenu-" + level);
		}

		// disabled for this element
		if (menu.disabled && menu.disabled == true) {
			$entry.addClass("disabled");
			$entry.attr("data-disabled", true);
		}

		// add type and group for this element
		if (menu.type) {
			$entry.attr("data-type", menu.type);
		}

		if (menu.group) {
			$entry.attr("data-group", menu.group);
		}

		$subMenu.append($entry);

		// check recursive sub menus
		if (menu.submenu) {
			if (menu.submenu.length > 0) {
				$.each(menu.submenu, function(index, submenu) {
					app.appendSubMenuRec($subMenu, submenu, level + 1);
				});
			} else {
				app.appendSubMenuRec($subMenu, menu.submenu, level + 1);
			}
		}

	},

	// ==========================================================================
	// websocket functions
	// ===========================================================================

	ws : null,

	initWebSocket : function() {

		app.ws = new ReconnectingWebSocket(app.API_WEBSOCKET, null, {
			debug : false
		});

		app.ws.onmessage = function(msg) {

			var data = JSON.parse(msg.data);
			var id = data["module-id"];

			// module activated
			if (data.message == "activate-module") {
				var module = JSON.parse(localStorage.getItem("module-" + id));
				module.running = true;
				localStorage.setItem("module-" + id, JSON.stringify(module));

				app.showInfoMessage(module.menu.name + " aktiviert");
				app.activateSubMenu(id);
			} else
			// module deactivated
			if (data.message == "deactivate-module") {
				var module = JSON.parse(localStorage.getItem("module-" + id));
				module.running = false;
				localStorage.setItem("module-" + id, JSON.stringify(module));

				app.showWarningMessage(module.menu.name + " deaktiviert");
				app.deactivateSubMenu(id);
			} else
			// new module available
			if (data.message == "new-module") {
				app.showInfoMessage("Modul hinzugef&uuml;gt");
				localStorage.removeItem("initialized");
				app.loadModules();
			} else
			// module deleted/disabled
			if (data.message == "delete-module") {
				app.showInfoMessage("Modul entfernt");
				localStorage.removeItem("initialized");
				app.loadModules();
			} else
			// module update available (menu changed)
			if (data.message == "update-module") {
				var module = JSON.parse(localStorage.getItem("module-" + id));
				if (module && module.menu) {
					app.showInfoMessage(module.menu.name + " aktualisiert");
				}
				localStorage.removeItem("initialized");
				app.loadModules();
			} else
			// notify
			if (data.message == "notify") {
				var module = JSON.parse(localStorage.getItem("module-" + id));
				app.showInfoMessage("Nachricht von " + module.menu.name);

				var $span = $("#main-menu-left").find("[module-id=" + id + "]")
						.find("span");

				if ($span.text() == "") {
					$span.text(1);
				} else {
					$span.text(parseInt($span.text()) + 1);
				}
			} else {
				console.log(msg);
				app.showInfoMessage(msg);
			}
		};

		app.ws.onerror = function(evt) {
			app.showErrorMessage("WebSocket-Fehler");
			console.log(evt);
		};

		app.ws.onclose = function() {
			app.showWarningMessage("WebSocket geschlossen");
		};
	},

};

// ===========================================================================
// ready & event handlers
// ===========================================================================

function initFramework() {

	app.createMainNavBar();
	app.createMessageBox();
	app.createContentAndSideBarArea();
	app.createSideNavBar();

	app.loadUser();
	app.loadModules();
	localStorage.setItem("initialized", true);

	app.initWebSocket();
}

$(initFramework);
