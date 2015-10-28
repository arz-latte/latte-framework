"use strict";

var appGroup = {
		
	URI_GROUPS : '/latte/api/admin/groups',
	URI_PERMISSIONS : '/latte/api/admin/permissions',

	loadGroups : function() {
		appAdmin.leaveEditMode();
		appGroup.currentId = null;

		latte.call(appGroup.URI_GROUPS, function(data) {
			var $groups = $("#groups");
			$groups.find("tr:has(td)").remove(); // clear

			$.each(data.group, function(index, group) {
				var $name = $("<td/>").append(group.name);

				var $row = $("<tr/>").attr("data-id", group.id);
				$row.append($name);

				$groups.append($row);
			});

			$("#list-filter").keyup();
		}).error(function(error) {
			if (error.status == 403) {
				app.showErrorMessage("Keine Berechtigung")
			} else {
				app.showErrorMessage("Fehler " + error.statusText)
			}
		});
	},

	loadPermissions : function() {
		latte.call(appGroup.URI_PERMISSIONS, function(data) {
			var $permissions = $("[name=select-permission]");
			$permissions.find("option").remove(); // clear

			$.each(data.permission, function(index, permission) {
				var $option = $('<option />');
				$option.attr('value', permission.id).text(permission.name);

				$permissions.append($option);
			});
		});
	},

	addNewGroup : function() {
		$("#btn-delete").hide();
		appAdmin.enterEditMode();
		appGroup.currentId = null;
	},

	showGroup : function() {
		$("#btn-delete").show();
		appAdmin.enterEditMode();

		// load group details
		appGroup.currentId = $(this).attr("data-id");
		latte.call(appGroup.URI_GROUPS + "/" + appGroup.currentId,
				function(data) {

					// fill form
					var g = data.group;
					$("[name=input-name]").val(g.name);

					if (g.permission) {
						var $permission = $("[name=select-permission]");
						if (g.permission.length > 0) {
							$.each(g.permission, function(index, permission) {
								$permission
										.find(
												"option[value='"
														+ permission.id + "']")
										.prop("selected", true);
							});
						} else {
							$permission.find(
									"option[value='" + g.permission.id + "']")
									.prop("selected", true);
						}
					}
				});
	},

	storeGroup : function() {
		// copy form to model
		var g = {};
		g.id = appGroup.currentId;
		g.name = $("[name=input-name]").val();

		g.permission = [];
		$("[name=select-permission]").find(":selected").each(
				function(index, selected) {
					g.permission.push({
						"id" : $(selected).val()
					});
				});

		if (g.id > 0) {

			latte.ajax({
				url : appGroup.URI_GROUPS,
				type : "PUT",
				data : JSON.stringify({
					"group" : g
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showSuccessMessage("Gruppe aktualisiert");
				appGroup.loadGroups();
			}).fail(function(error) {
				appAdmin.validateData(error);
			});
		} else {

			latte.ajax({
				url : appGroup.URI_GROUPS,
				type : "POST",
				data : JSON.stringify({
					"group" : g
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showSuccessMessage("Gruppe erstellt");
				appGroup.loadGroups();
			}).fail(function(error) {
				appAdmin.validateData(error);
			});
		}

		return false;
	},

	deleteGroup : function() {
		var choice = confirm("Sind Sie sicher?");
		if (choice == true) {
			latte.ajax({
				url : appGroup.URI_GROUPS + "/" + appGroup.currentId,
				type : "DELETE",
			}).done(function(data) {
				app.showSuccessMessage("Gruppe gel&ouml;scht");
				appGroup.loadGroups();
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		}

		return false;
	},

	restoreGroup : function() {
		appAdmin.leaveEditMode();
		appGroup.currentId = null;
		return false;
	},

	initModule : function() {
		appAdmin.init();

		$("#btn-load").on("click", appGroup.loadGroups);
		$("#btn-add").on("click", appGroup.addNewGroup);

		$("#btn-store").on("click", appGroup.storeGroup);
		$("#btn-delete").on("click", appGroup.deleteGroup);
		$("#btn-restore").on("click", appGroup.restoreGroup);

		$("#list-area tbody").on("click", "tr", appGroup.showGroup);

		appGroup.loadGroups();
		appGroup.loadPermissions();
	},
};

$(function() {
	appGroup.initModule();
});