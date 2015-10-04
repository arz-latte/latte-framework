/**
 * version 31.08.2015
 */
var appAdmin = {

	/**
	 * reset invalid input fields
	 */
	resetFormValidation : function() {
		// reset form valid
		$("#edit-area input").each(function() {
			$(this).closest(".form-group").removeClass("has-error");
			$(this).next().hide(); // hide glyphicon

			var text = $(this).parent().prev().text();
			$(this).parent().prev().text(text.split(": ")[0]);
		})
	},

	/**
	 * validate form
	 * 
	 * @param error
	 *            JSON-structure with validation messages
	 */
	validateData : function(error) {
		app.showErrorMessage("Fehler beim Speichern");

		if (error.status == 400) {
			appAdmin.resetFormValidation();

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

	/**
	 * filter list view
	 */
	filterList : function() {
		var rex = new RegExp($(this).val(), "i");
		$("tbody tr").hide();
		$("tbody tr").filter(function() {
			return rex.test($(this).text());
		}).show();
	},

	/**
	 * clear list view filter
	 */
	clearListFilter : function() {
		$("#list-filter").val("");
		$("tbody tr").show();
	},

	/**
	 * enter edit mode, show input fields
	 */
	enterEditMode : function() {
		appAdmin.resetFormValidation();

		$("#list-area").hide();
		$("#edit-area").show();

		$("#edit-area form").trigger("reset");
	},

	/**
	 * leave edit mode, hide input fields
	 */
	leaveEditMode : function() {
		$("#edit-area").hide();
		$("#list-area").show();
	},

	/**
	 * initialize filter
	 */
	init : function() {
		$("#btn-clear-list-filter").on("click", appAdmin.clearListFilter);
		$("#list-filter").on("keyup", appAdmin.filterList);
	}
};