var appUser = {

	API_USERS : 'api/v1/users',

	currentId : null,

	loadUsers : function() {
		appAdmin.leaveEditMode();
		appUser.currentId = null;

		$.getJSON(appUser.API_USERS + "/all.json", function(data) {

			var $users = $("#users");
			$users.find("tr:has(td)").remove(); // clear

			$.each(data.user, function(index, user) {
				var $firstName = $("<td/>").append(user.firstName);
				var $lastName = $("<td/>").append(user.lastName);
				var $username = $("<td/>").append(user.username);

				var $row = $("<tr/>").attr("data-id", user.id);
				$row.append($firstName).append($lastName).append($username);

				$users.append($row);
			});

			$("#list-filter").keyup();
		});
	},

	loadRoles : function() {
		$.getJSON(appUser.API_USERS + "/roles.json", function(data) {

			var $roles = $("[name=select-role]");
			$roles.find("option").remove(); // clear

			$.each(data.role, function(index, role) {
				var $option = $('<option />');
				$option.attr('value', role.id).text(role.name);

				$roles.append($option);
			});
		});
	},

	addNewUser : function() {
		$("#btn-delete-user").hide();
		appAdmin.enterEditMode();
		appUser.currentId = null;
	},

	showUser : function() {
		$("#btn-delete-user").show();
		appAdmin.enterEditMode();
		appUser.currentId = null;

		// load user details
		appUser.currentId = $(this).attr("data-id");
		$.getJSON(appUser.API_USERS + "/get.json/" + appUser.currentId,
				function(data) {

					// fill form
					var u = data.user;
					$("[name=input-firstName]").val(u.firstName);
					$("[name=input-lastName]").val(u.lastName);
					$("[name=input-username]").val(u.username);
					$("[name=input-password]").val(u.password);
					
					var $role = $("[name=select-role]");
					
					if (u.role.length > 0) {
						$.each(u.role, function(index, role) {
							//$("[name=select-role] option[value='" + role + "']").prop("selected", true);
							$role.find("option[value='" + role.id + "']").prop("selected", true);
						});
					} else {
						$role.find("option[value='" + u.role.id + "']").prop("selected", true);
					}
				});
	},

	storeUser : function() {
		// copy form to model
		var u = {};
		u.id = appUser.currentId;
		u.firstName = $("[name=input-firstName]").val();
		u.lastName = $("[name=input-lastName]").val();
		u.username = $("[name=input-username]").val();
		u.password = $("[name=input-password]").val();

		u.role = [];
		$("[name=select-role]").find(":selected").each(function(index, selected) {
			u.role.push({"id" : $(selected).val()});
		});

		if (u.id > 0) {

			$.ajax({
				url : appUser.API_USERS + "/update.json",
				type : "PUT",
				data : JSON.stringify({
					"user" : u
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showMessage("Benutzer aktualisiert");
				appUser.loadUsers();
			}).fail(function(error) {
				appAdmin.validateData(error);
			});
		} else {

			$.ajax({
				url : appUser.API_USERS + "/create.json",
				type : "POST",
				data : JSON.stringify({
					"user" : u
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showMessage("Benutzer erstellt");
				appUser.loadUsers();
			}).fail(function(error) {
				appAdmin.validateData(error);
			});
		}

		return false;
	},

	deleteUser : function() {
		var choice = confirm("Sind Sie sicher?");
		if (choice == true) {
			$.ajax({
				url : appUser.API_USERS + "/delete.json/" + appUser.currentId,
				type : "DELETE",
			}).done(function(data) {
				app.showMessage("Benutzer gel&ouml;scht");
				appUser.loadUsers();
			}).fail(function() {
				app.showErrorMessage("Fehler");
			});
		}

		return false;
	},

	restoreUser : function() {
		appAdmin.leaveEditMode();
		appUser.currentId = null;
		return false;
	},

};

// ===========================================================================
// ready & event handlers
// ===========================================================================
function initUser() {

	appAdmin.init();

	$("#btn-load").on("click", appUser.loadUsers);
	$("#btn-add").on("click", appUser.addNewUser);

	$("#btn-store").on("click", appUser.storeUser);
	$("#btn-delete").on("click", appUser.deleteUser);
	$("#btn-restore").on("click", appUser.restoreUser);

	$("#list-area tbody").on("click", "tr", appUser.showUser);

	appUser.loadRoles();
	appUser.loadUsers();
}

$(initUser);
