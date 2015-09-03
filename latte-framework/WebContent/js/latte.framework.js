/**
 * version 31.08.2015
 */
var app = {

	API_LATTE : 'http://localhost:8080',
	PATH_INDEX : '/latte/index.html',
	PATH_FRAMEWORK : '/latte/api/v1/framework',

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
	 * enable all submenus by specific attribute and value
	 * @param attribute
	 * @param value
	 */
	enableSubMenuByAttribute : function(attribute, value) {

		$("#side-menu").find("li").each(function(i, li) {
			var $li = $(li);			
			if ($li.data(attribute) == value && !$li.data("denied")) {
				$li.removeClass("disabled");
			}
		});
	},
	
	/**
	 * disable all submenus by specific attribute and value
	 * @param attribute
	 * @param value
	 */
	disableSubMenuByAttribute : function(attribute, value) {
		
		$("#side-menu").find("li").each(function(i, li) {
			var $li = $(li);			
			if ($li.data(attribute) == value && !$li.data("denied")) {
				$li.addClass("disabled");
			}
		});
	},

	/**
	 * show message to user
	 * @param msg
	 */
	showMessage : function(msg) {
		var $box = $("#message-box");
		$box.removeClass("error");
		$box.html(msg);
		$box.show(100).delay(3000).hide(100);
	},

	/**
	 * show error message to user
	 * @param msg
	 */
	showErrorMessage : function(msg) {
		var $box = $("#message-box");
		$box.addClass("error");
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
			text : "Latte"
		}));

		var $navbarCollapse = $("<div/>", {
			'class' : "navbar-collapse collapse",
			id : "main-navbar-collapse"
		}).append($("<ul/>", {
			'class' : "nav navbar-nav",
			id : "main-menu-left"
		}), $("<ul/>", {
			'class' : "nav navbar-nav navbar-right",
			id : "main-menu-right"
		}).append($("<li/>").append($("<a/>", {
			href : "#",
			text : "Logout"
		}))));

		$div.append($navbarHeader, $navbarCollapse);

		$menu.append($div);

		$("#main-navbar-placeholder").replaceWith($menu);
	},

	/**
	 * create and place message box
	 */
	createMessageBox : function() {

		var $container = $("<div/>", {
			'class' : "container-fluid"
		}).append($("<div/>", {
			'class' : "row"
		}).append($("<div/>", {
			'class' : "col-sm-12",
			id : "message-box"
		})));

		$("#message-box-placeholder").replaceWith($container);
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
	 * load all modules and menus from server and store them in localStorage
	 */
	loadModules : function() {

		if (localStorage.getItem("initialized")) {
			return;
		}

		$.ajax({
			url : app.API_LATTE + app.PATH_FRAMEWORK + "/init.json",
			async : false,
		}).done(function(data) {
			//localStorage.clear();
			localStorage.setItem("initialized", true);

			// store each module separate
			$.each(data.module, function(index, m) {
				localStorage.setItem('module-' + m.id, JSON.stringify(m.menu));

				// remove sub menu for storing list of modules
				// (without sub menus)
				delete m.menu.submenu;
			});

			// store list of modules
			localStorage.setItem('modules', JSON.stringify(data.module));

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

		var modules = JSON.parse(localStorage.getItem('modules'));
		$.each(modules, function(index, m) {

			// main menu (module name)
			var $entry = $("<li/>").append($("<a/>", {
				href : m.menu.url,
				text : m.menu.name,
				'data-id' : m.id,
				on : {
					click : function() {
						localStorage.setItem("module-id", m.id);
						app.initSubMenu();
					}
				}
			}));

			$mainMenuLeft.append($entry);

			// mark current active module
			if (localStorage.getItem("module-id") == m.id) {
				app.initSubMenu();
			}
		});

		// logout button
		$("#main-menu-right").find("a").on("click", function() {
			localStorage.clear();
			app.ws
			alert("storage cleared");
		});

	},

	/**
	 * initializes sub menu for single module
	 */
	initSubMenu : function() {

		var id = localStorage.getItem("module-id");
		var menu = JSON.parse(localStorage.getItem("module-" + id));

		// activate main menu
		var $mainMenuLeft = $("#main-menu-left");
		$mainMenuLeft.find("a").parent().removeClass("active");
		$mainMenuLeft.find("a[data-id=" + id + "]").parent().addClass("active");

		// create sub menu
		var $subMenu = $("#side-menu");
		$subMenu.html(""); // clear

		if (menu.submenu) {			
			if (menu.submenu.length > 0) {
				$.each(menu.submenu, function(index, menu) {
					app.appendSubMenuRec($subMenu, menu, 0);
				});
			} else {
				app.appendSubMenuRec($subMenu, menu.submenu, 0);
			}

			app.showSideBar();
		} else {
			app.hideSideBar(); // no side menu for this module
		}
	},

	/**
	 * helper for creating submenu
	 * @param $subMenu target element
	 * @param menu datastructure with menu informations
	 * @param level information about submenu level
	 */
	appendSubMenuRec : function($subMenu, menu, level) {

		// sub menu entry
		var $entry = $("<li/>").append($("<a/>", {
			href : menu.url,
			text : menu.name,
		}));

		// mark current active menu
		var loc = window.location.href.split('#')[0];
		if (menu.url == loc) {
			$entry.addClass("active");
		}

		// mark submenu
		if (level > 0) {
			$entry.addClass("submenu submenu-" + level);
		}
		
		// add permission for this element
		if (menu.denied) {
			$entry.addClass("disabled");
			$entry.attr("data-denied", true);
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
			console.log("ws onmessage" + msg.data);

			var data = JSON.parse(msg.data);
			app.showMessage(data.message);
			
			
			// new module
			// module deleted
			// module active
			// module inactive
			// module specific
			

			// module update available
			if (data.message == "update") {
				localStorage.removeItem("initialized");
				app.loadModules();
				app.initMainMenu();
			}

			if (data.message == "inactive") {
				// app.loadModules();
				// app.initMainMenu();
				// get list with menu status...
				// todo disable
			}
		};

		app.ws.onerror = function(evt) {
			app.showErrorMessage(evt);
		};

		app.ws.onclose = function() {
			app.showErrorMessage("Verbindung zum Server geschlossen");
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
