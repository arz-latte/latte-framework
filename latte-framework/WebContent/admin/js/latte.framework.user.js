"use strict";

var appUser = {

	API_USERS : 'api/users',

	URI_USERS : '/latte/api/admin/users',
	URI_GROUPS : '/latte/api/admin/groups',

	loadUsers : function() {
		appAdmin.leaveEditMode();
		appUser.currentId = null;

		latte.call(appUser.URI_USERS, function(data) {
			var $users = $("#users");
			$users.find("tr:has(td)").remove(); // clear

			$.each(data.user, function(index, user) {
				var $firstName = $("<td/>").append(user.firstName);
				var $lastName = $("<td/>").append(user.lastName);
				var $email = $("<td/>").append(user.email);

				var $row = $("<tr/>").attr("data-id", user.id);
				$row.append($firstName).append($lastName).append($email);

				$users.append($row);
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

	loadGroups : function() {
		latte.call(appUser.URI_GROUPS, function(data) {

			var $groups = $("[name=select-group]");
			$groups.find("option").remove(); // clear

			$.each(data.group, function(index, group) {
				var $option = $('<option />');
				$option.attr('value', group.id).text(group.name);

				$groups.append($option);
			});
		});
	},

	addNewUser : function() {
		$("#btn-delete").hide();
		appAdmin.enterEditMode();
		appUser.currentId = null;
	},

	showUser : function() {
		$("#btn-delete").show();
		appAdmin.enterEditMode();

		// load user details
		appUser.currentId = $(this).attr("data-id");
		latte.call(appUser.URI_USERS + "/" + appUser.currentId, function(data) {

			// fill form
			var u = data.user;
			$("[name=input-firstName]").val(u.firstName);
			$("[name=input-lastName]").val(u.lastName);
			$("[name=input-email]").val(u.email);
			$("[name=input-password]").val(u.password);

			if (u.group) {
				var $group = $("[name=select-group]");
				if (u.group.length > 0) {
					$.each(u.group, function(index, group) {
						$group.find("option[value='" + group.id + "']").prop(
								"selected", true);
					});
				} else {
					$group.find("option[value='" + u.group.id + "']").prop(
							"selected", true);
				}
			}
		});
	},

	storeUser : function() {
		// copy form to model
		var u = {};
		u.id = appUser.currentId;
		u.firstName = $("[name=input-firstName]").val();
		u.lastName = $("[name=input-lastName]").val();
		u.email = $("[name=input-email]").val();
		u.password = $("[name=input-password]").val();

		u.group = [];
		$("[name=select-group]").find(":selected").each(
				function(index, selected) {
					u.group.push({
						"id" : $(selected).val()
					});
				});

		if (u.id > 0) {

			latte.ajax({
				url : appUser.URI_USERS,
				type : "PUT",
				data : JSON.stringify({
					"user" : u
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showSuccessMessage("Benutzer aktualisiert");
				appUser.loadUsers();
			}).fail(function(error) {
				appAdmin.validateData(error);
			});
		} else {

			latte.ajax({
				url : appUser.URI_USERS,
				type : "POST",
				data : JSON.stringify({
					"user" : u
				}),
				contentType : "application/json; charset=UTF-8",
			}).done(function(data) {
				app.showSuccessMessage("Benutzer erstellt");
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
			latte.ajax({
				url : appUser.URI_USERS + "/" + appUser.currentId,
				type : "DELETE",
			}).done(function(data) {
				app.showSuccessMessage("Benutzer gel&ouml;scht");
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

	initModule : function() {
		appAdmin.init();

		$("#btn-load").on("click", appUser.loadUsers);
		$("#btn-add").on("click", appUser.addNewUser);

		$("#btn-store").on("click", appUser.storeUser);
		$("#btn-delete").on("click", appUser.deleteUser);
		$("#btn-restore").on("click", appUser.restoreUser);

		$("#list-area tbody").on("click", "tr", appUser.showUser);

		appUser.loadGroups();
		appUser.loadUsers();
	}
};

$(function() {
	appUser.initModule();
});
