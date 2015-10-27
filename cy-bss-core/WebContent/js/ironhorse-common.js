var RESULT_OK = "00";

var ROLE_SA="Super Administrator";
var ROLE_ADMIN="Administrator";
var ROLE_USER="User";
var ROLE_PARTNER="Partner";



function getLocalStorageItem(paramName){ 
	if (typeof(Storage) !== "undefined") 
	   return localStorage.getItem(paramName);
	else 
		{
		alert("Local storage isn't supported !");
		}
}	

function setLocalStorageItem(paramName,value){
	if (typeof(Storage) !== "undefined") 
		localStorage.setItem(paramName, value); 
	else 
    alert("Local storage isn't supported !");
}

function getLanguage(){
	var userLang=getLocalStorageItem("org.cysoft.bss.ih.user.languageCode");
	if (userLang==undefined)  		
		userLang = navigator.language || navigator.userLanguage;
	if (userLang=='it-IT' || userLang=='it')
   	 return 'it';
    else
   	 return 'en';
}


function callRestWs($http,endPoint,method,headers,data,success,error){
	var request = '{"method": "'+method+'","url": "';
	request+=getLocalStorageItem("org.cysoft.bss.ih.coreurl")+'/rest/'+endPoint+'",'; 
	request+='"headers": {'+headers+'},';
	request+='"data": {'+data+'}}';
	
	console.log("request="+request);
	
	//alert(request);
	
	var reqObj=JSON.parse(request);
   	$http(reqObj).then(
			function(response){
				success(response);
			}, 
			function(response){
				error(response);
			});
}


function setMenuCntl(app) {
	
	
	
	app.controller('menuCtrl', function($scope, $http, $translate) {
		var userRole=getLocalStorageItem('org.cysoft.bss.ih.user.role');
		if (userRole!=undefined)
			$scope.userRole=userRole;
		console.log("user role="+$scope.userRole);
		
		 $scope.onLogOff = function() {
			 $scope.securityToken=getLocalStorageItem("org.cysoft.bss.ih.securityToken");
			 callRestWs($http,'/cybss-auth/logOff','GET',
						'"Security-Token": "'+$scope.securityToken+'"',
						'',
						function(response){
								if (response.data.resultCode==RESULT_OK){
									setLocalStorageItem("org.cysoft.bss.ih.securityToken",'');
									$("#callPage").attr("action","logOn.html");
		   	       		 			$("#callPage").submit();
								}
								else
								{
									alert(response.data.resultCode+'-'+response.data.resultDesc);
								}
							}, 
							function(response){
									alert(response.status+'-'+response.data);
							});
			 
		 }
		
	});  
}

function manageError($scope,status,data){
		$scope.errorMessage=status+" - "+data;	
}
