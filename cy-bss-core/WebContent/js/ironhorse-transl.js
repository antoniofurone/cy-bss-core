var menuMapTranslation={
		"en":
			{
				"MENU.ADMIN":"Administration",
				"MENU.ADMIN.USER":"Manage users",
				"MENU.ADMIN.PROFILE":"Your Profile"
				
				
			},
		"it":
			{
				"MENU.ADMIN":"Amministrazione",
				"MENU.ADMIN.USER":"Gestione utenti",
				"MENU.ADMIN.PROFILE":"Il tuo profilo"
				
			}
		};

angular.module('irtranslator', []).filter('irtranslate', function() {
	  return function(input) {
	  var langCode=getLanguage();  
	  return menuMapTranslation[langCode][input];
	  };
	});