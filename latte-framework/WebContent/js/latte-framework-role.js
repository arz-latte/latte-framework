var appRole = {

	API_ROLES : 'api/v1/roles',

	currentId : null,

	loadRoles : function() {
		appAdmin.leaveEditMode();
		appRole.currentId = null;

		$.getJSON(appRole.API_ROLES + "/all.json", function(data) {

			var $roles = $("#roles");
			$roles.find("tr:has(td)").remove(); // clear

			$.each(data.role, function(index, role) {
				var $name = $("<td/>").append(role.name);

				var $row = $("<tr/>").attr("data-id", role.id);
				$row.append($name);

				$roles.append($row);
			});

			$("#list-filter").keyup();
		});
	},

	addNewRole : function() {
		$("#btn-delete-role").hide();
		appAdmin.enterEditMode();
		appRole.currentId = null;
	},

	showRole : function() {
		$("#btn-delete-role").show();
		appAdmin.enterEditMode();
		appRole.currentId = null;

		// load role details
		appRole.currentId = $(this).attr("data-id");
		$.getJSON(appRole.API_ROLES + "/get.json/" + appRole.currentId,
				function(data) {

					// fill form
					var r = data.role;
					$("[name=input-name]").val(r.name);
				});
	},

	storeRole : function() {
		// copy form to model
		var r = {};
		r.id = appRole.currentId;
		r.name = $("[name=input-name]").val();

		if (r.id > 0) {

			$.ajax({
				url : appRole.API_ROLES + "/update.json",
				type : "PUT",
				data : JSON.stringify({
					"role" : r
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showMessage("Rolle aktualisiert");
				appRole.loadRoles();
			}).fail(function(error) {
				appAdmin.validateData(error);
			});
		} else {

			$.ajax({
				url : appRole.API_ROLES + "/create.json",
				type : "POST",
				data : JSON.stringify({
					"role" : r
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showMessage("Rolle erstellt");
				appRole.loadRoles();
			}).fail(function(error) {
				appAdmin.validateData(error);
			});
		}

		return false;
	},

	deleteRole : function() {
		var choice = confirm("Sind Sie sicher?");
		if (choice == true) {
			$.ajax(
					{
						url : appRole.API_ROLES + "/delete.json/"
								+ appRole.currentId,
						type : "DELETE",
					}).done(function(data) {
				app.showMessage("Rolle gel&ouml;scht");
				appRole.loadRoles();
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		}

		return false;
	},

	restoreRole : function() {
		appAdmin.leaveEditMode();
		appRole.currentId = null;
		return false;
	},

};

// ===========================================================================
// ready & event handlers
// ===========================================================================
function initRole() {
	
	appAdmin.init();

	$("#btn-load").on("click", appRole.loadRoles);
	$("#btn-add").on("click", appRole.addNewRole);

	$("#btn-store").on("click", appRole.storeRole);
	$("#btn-delete").on("click", appRole.deleteRole);
	$("#btn-restore").on("click", appRole.restoreRole);

	$("#list-area tbody").on("click", "tr", appRole.showRole);

	appRole.loadRoles();
}

$(initRole);
