var appGroup = {

	API_GROUPS : 'api/groups',

	currentId : null,

	loadGroups : function() {
		appAdmin.leaveEditMode();
		appGroup.currentId = null;

		$.getJSON(appGroup.API_GROUPS + "/all.json", function(data) {

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
		$.getJSON(appGroup.API_GROUPS + "/permissions.json", function(data) {

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
		appGroup.currentId = null;

		// load group details
		appGroup.currentId = $(this).attr("data-id");
		$.getJSON(appGroup.API_GROUPS + "/get.json/" + appGroup.currentId,
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

			$.ajax({
				url : appGroup.API_GROUPS + "/update.json",
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

			$.ajax({
				url : appGroup.API_GROUPS + "/create.json",
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
			$.ajax({
				url : appGroup.API_GROUPS + "/delete.json/" + appGroup.currentId,
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

};

// ===========================================================================
// ready & event handlers
// ===========================================================================
function initGroup() {

	appAdmin.init();

	$("#btn-load").on("click", appGroup.loadGroups);
	$("#btn-add").on("click", appGroup.addNewGroup);

	$("#btn-store").on("click", appGroup.storeGroup);
	$("#btn-delete").on("click", appGroup.deleteGroup);
	$("#btn-restore").on("click", appGroup.restoreGroup);

	$("#list-area tbody").on("click", "tr", appGroup.showGroup);

	appGroup.loadGroups();
	appGroup.loadPermissions();
}

$(initGroup);
