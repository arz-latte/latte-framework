/**
 * version 31.08.2015
 */
var appUser = {

	API_USERS : 'api/v1/users',

	currentId : null,

	loadUsers : function() {
		appUser.leaveEditMode();

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

			$("#user-filter").keyup();
		});
	},

	addNewUser : function() {
		$("#btn-delete-user").hide();
		appUser.enterEditMode();
	},

	showUser : function() {
		$("#btn-delete-user").show();
		appUser.enterEditMode();

		// load user details
		appUser.currentId = $(this).attr("data-id");
		$.getJSON(appUser.API_USERS + "/get.json/" + appUser.currentId,
				function(data) {

					// fill form
					var m = data.user;
					$("[name=input-firstname]").val(m.firstName);
					$("[name=input-lastname]").val(m.lastName);
					$("[name=input-username]").val(m.username);
					$("[name=input-password]").val(m.password);
				});
	},

	storeUser : function() {
		// copy form to model
		var u = {};
		u.id = appUser.currentId;
		u.firstname = $("[name=input-firstname]").val();
		u.lastname = $("[name=input-lastname]").val();
		u.username = $("[name=input-username]").val();
		u.password = $("[name=input-password]").val();

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
				appUser.validateUser(error);
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
				appUser.validateUser(error);
			});
		}

		return false;
	},

	resetFormValidation : function() {
		// reset form valid
		$("#user-edit-area input").each(function() {
			$(this).closest(".form-group").removeClass("has-error");
			$(this).next().hide(); // hide glyphicon

			var text = $(this).parent().prev().text();
			$(this).parent().prev().text(text.split(": ")[0]);
		})
	},

	validateUser : function(error) {
		app.showErrorMessage("Fehler beim Speichern");

		if (error.status == 400) {
			appUser.resetFormValidation();

			var validation = error.responseJSON.response.validation;

			// mark inputs as invalid
			var entries = [];
			if ($.isArray(validation.entry)) {
				$.merge(entries, validation.entry);
			} else {
				entries.push(validation.entry);
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
		}
	},

	deleteUser : function() {
		var choice = confirm("Sind Sie sicher?");
		if (choice == true) {
			$.ajax(
					{
						url : appUser.API_USERS + "/delete.json/"
								+ appUser.currentId,
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
		appUser.leaveEditMode();
		return false;
	},

	filterUser : function() {
		var rex = new RegExp($(this).val(), "i");
		$("tbody tr").hide();
		$("tbody tr").filter(function() {
			return rex.test($(this).text());
		}).show();
	},

	clearUserFilter : function() {
		$("#user-filter").val("");
		$("tbody tr").show();
	},

	enterEditMode : function() {
		appUser.resetFormValidation();

		$("#user-list-area").hide();
		$("#user-edit-area").show();

		$("#user-edit-area form").trigger("reset");
		appUser.currentId = null;
	},

	leaveEditMode : function() {
		$("#user-edit-area").hide();
		$("#user-list-area").show();
		appUser.currentId = null;
	},

};

// ===========================================================================
// ready & event handlers
// ===========================================================================
function initUser() {

	$("#btn-clear-user-filter").on("click", appUser.clearUserFilter);
	$("#user-filter").on("keyup", appUser.filterUser);

	$("#btn-load-users").on("click", appUser.loadUsers);
	$("#btn-add-user").on("click", appUser.addNewUser);

	$("#btn-store-user").on("click", appUser.storeUser);
	$("#btn-delete-user").on("click", appUser.deleteUser);
	$("#btn-restore-user").on("click", appUser.restoreUser);

	$("#user-list-area tbody").on("click", "tr", appUser.showUser);

	appUser.loadUsers();
}

$(initUser);
