app.controller('loginController',function($scope,$http,$state,$window,$cookieStore){
	$scope.login = {};
	$scope.onSingIn = function(data){
		$http({method:'POST',url:'user/login', data: data})
		 .success(function(response){
			 $cookieStore.put("access-token", response.token);
			 $http.defaults.headers.common["X-AUTH-TOKEN"] = response.token;
			 $state.go("event");
		 }).error(function(data){
			 console.log("error");
		});
	};
});

app.controller('eventController',function($scope,$http,$state,$window,$stateParams){
	$scope.event={}; 
	$scope.event.group= []; 
	$scope.event.inventory= []; 
	$scope.event.organiser= [];
	
	$scope.gridOptionsInfo = {};
	$scope.detailsGridOptionsInfo = {};
	
	$scope.companyData = [];
	$scope.inventorySource = [];
	$scope.groupSource = [];
	$scope.organiserSource = [];
	
	$scope.initilizeCreateEvent = function(flag){
		$('#startdate').datepicker({
            format: "dd/mm/yyyy",
            autoclose: true
        });
		
		$('#enddate').datepicker({
            format: "dd/mm/yyyy",
            autoclose: true
        });
		
		/*load merchandise in multiselect*/
		$http({method:'GET',url:'wasps/getMerchendise', data: {}})
		 .success(function(response){
		     $scope.inventorySource = response;
		 }).error(function(data){
			 console.log("error");
		});
		
		//get all companies
		if(flag){
			$http({method:'GET',url:'wasps/company/list', data: {}})
			 .success(function(respnose){
				 console.log(respnose);
				 $scope.companyData = respnose;
			 }).error(function(data){
				 console.log("error");
			});
		}
		
	}
	
	$scope.onCompanyChange = function(id){
		$scope.event.group= []; 
		$scope.event.organiser= [];

		/*load groups in multiselect */
		$http({method:'GET',url:'wasps/groupByCompany/'+id, data: {}})
		 .success(function(response){
			 var source = [];
			 for(var i=0; i<response.length; i++){
				 var temp = {
						 "id": response[i].groupId,
						 "label": response[i].name
				 };
				 source.push(temp);
			 }
			 $scope.groupSource = source;
		 }).error(function(data){
			 console.log("error");
		});
		
		$http({method:'GET',url:'wasps/organiserByCompany/'+id, data: {}})
		 .success(function(response){
			 var source = [];
			 for(var i=0; i<response.length; i++){
				 var temp = {
						 "id": response[i].companyOrganiserId,
						 "label": response[i].firstName+" "+response[i].lastName
				 };
				 source.push(temp);
			 }
			 $scope.organiserSource = source;
		 }).error(function(data){
			 console.log("error");
		});
		
	}
	
	$scope.initializeEventGrid = function(){

		//get all events
		$http({method:'GET',url:'wasps/event/all', data: {}})
		 .success(function(response){
			 $scope.gridOptionsInfo.data = response;
		 }).error(function(data){
			 console.log("error");
		});
		
		/*event grid*/ 
		$scope.gridOptionsInfo.columnDefs = [
		                                      {name: 'id', displayName: 'id', enableFiltering:false, visible:false },
		                                      {name: 'eventDescription', displayName: 'Name',width:'20%'},
		                                      {name: 'startDate', displayName: 'Start Date', width: '20%', cellFilter: 'date:\'yyyy-MM-dd\''},
		                                      {name: 'endDate', displayName: 'End Date', width: '20%', cellFilter: 'date:\'yyyy-MM-dd\''},
		                                      {name: 'group', displayName: 'Groups', width: '15%', cellTemplate:'<div class="col-sm-12"><i ng-click="grid.appScope.viewUsers(row)"  data-toggle="modal" data-target="#gridModal" class="glyphicon glyphicon-eye-open col-sm-4 col-sm-offset-4" style="margin-top: 4px;"></i></div>'},
		                                      {name: 'merchandise', displayName: 'Merchandise', width: '15%', cellTemplate:'<div class="col-sm-12"><i ng-click="grid.appScope.viewMerchandise(row)" data-toggle="modal" data-target="#gridModal" class="glyphicon glyphicon-eye-open col-sm-4 col-sm-offset-4" style="margin-top: 4px;"></i></div>'},
		                                      {name: 'edit', displayName: 'Edit',enableFiltering: false,width:'10%',cellTemplate:'<div class="col-sm-12"><i ng-click="grid.appScope.edit(row)" class="glyphicon glyphicon-edit col-sm-4" style="margin-top: 4px;" ></i></div>'}
		                                      /*{name: 'action', displayName: 'Action',enableFiltering: false,width:'35%',cellTemplate:'<div class="col-sm-12"><i ng-click="grid.appScope.edit(row)" class="glyphicon glyphicon-edit col-sm-4" style="margin-top: 4px;" ></i><i ng-click="grid.appScope.deleteRow(row)" class="glyphicon glyphicon-trash col-sm-4" style="margin-top: 4px;"></i></div>'}*/
	      ];
		
		$scope.gridOptionsInfo.data = [];
	}
	
	/*grid's details grid */
	$scope.detailsGridOptionsInfo = { enablePaginationControls: false,
									  columnDefs: [
			                                      {name: 'id', displayName: 'id', enableFiltering:false, visible:false},
			                                      {name: 'label', displayName: 'Name'}
			                                      ]
									 };
	$scope.detailsGridOptionsInfo.data = [{"id": "", "label": ""}];
	
	$scope.viewUsers = function(data) {
		
		/*get user by id*/
		$http({method:'GET',url:'wasps/group/'+data.entity.eventId, data: {}})
		 .success(function(response){
			 $scope.detailsGridOptionsInfo.data = response;
		 }).error(function(data){
			 console.log("error");
		});
		
	}
	
	$scope.viewMerchandise = function(data) {
		/*get merchandise by id*/
		$http({method:'GET',url:'wasps/merchandise/'+data.entity.eventId, data: {}})
		 .success(function(response){
			 $scope.detailsGridOptionsInfo.data = response;
		 }).error(function(data){
			 console.log("error");
		});
	}
	
	$scope.goToCreateEvent = function(){
		$state.go("createEvent");
	}
	
	$scope.onSaveEvent = function(event){
		$http({method:'POST',url:'wasps/event/save', data: event})
		 .success(function(response){
			 $state.go("event");
			 toastr.success('successfully Created!', 'Event')
		 }).error(function(data){
			 console.log("error");
		});
	};
	
	$scope.onShowEvents = function(){
		$state.go("event");
	}
	
	$scope.edit = function(data) {
		console.log("data.entity.id",data.entity.eventId);
		$state.go('editEvent',{rowid :  data.entity.eventId});
	}
	
	if($stateParams.rowid !=null){
		$scope.initilizeCreateEvent(false);
		
		$http({method:'GET',url:'wasps/company/list', data: {}})
		 .success(function(respnose){
			 $scope.companyData = respnose;
			 
			 $http({method:'GET',url:'wasps/event/edit/'+$stateParams.rowid, data: {}})
			 .success(function(response){

				 var start = new Date(Date.parse(response.startDate));
				 response.startDate = start.getDate()  + "/" + (start.getMonth()+1) + "/" + start.getFullYear();
				 if(!response.oneofevent){
					 var end = new Date(Date.parse(response.endDate));
					 response.endDate = end.getDate()  + "/" + (end.getMonth()+1) + "/" + end.getFullYear();
				 }
				 
				 $scope.onCompanyChange(response.company);
				 $scope.event = response;
			 
			 }).error(function(data){
				 console.log("error");
			});
			 
		 }).error(function(data){
			 console.log("error");
		});
	}
	
	$scope.onUpdateEvent = function(event){
		$http({method:'POST',url:'wasps/event/update', data: event})
		 .success(function(response){
			 $state.go("event");
			 toastr.success('Successfully updated!', 'EVENT')
		 }).error(function(data){
			 console.log("error");
			 toastr.error('Error for updating.', 'EVENT')
		});
	}
});

app.controller('membershipRenewalController',function($scope,$http,$state,$window,$stateParams){
	var token = $stateParams.token;
	$scope.currency = "$";
	$scope.hideMembershipRenewal = false;
	$scope.hideConfirmation = true; 
	
	$scope.initializeMembership = function() {
		$http({method:'GET',url:'user/token/'+token, data: {}})
		 .success(function(response){
			 $scope.tokens = response;
			 $http.defaults.headers.common["X-AUTH-TOKEN"] = response.token;
			 $scope.getInventories(response.eventId, response.authId);
			 
		 }).error(function(data){
			 console.log("error");
		});
	}
	
	$scope.getInventories = function(eventId, authId){
		$http({method:'GET',url:'wasps/inventoriesByEvent/'+eventId, data: {}})
		 .success(function(res){
			 $scope.inventories = res;
			 $scope.getChildUsers(authId, eventId);
		 }).error(function(data){
			 console.log("error");
		});
	}
	
	$scope.getChildUsers = function(parentId, eventId){
		$http({method:'GET',url:'wasps/getChildUsers/'+parentId+'/'+eventId, data: {}})
		 .success(function(response){
			 $scope.childUsers = [];
			 $scope.childUsers = response;
			 for(var i=0; i<$scope.childUsers.length; i++) {
				 $scope.childUsers[i].childinventories = angular.copy($scope.inventories);
			 }
		 }).error(function(data){
			 console.log("error");
		});
	}
	
	$scope.onMakePayment = function() {
		$scope.hideMembershipRenewal = true;
		$scope.hideConfirmation = false;
		
		$scope.selectedMembers = [];
		$scope.membershipTotal = 0;
		
		$scope.paidUserId = "";
		
		if($scope.tokens.checked) {
			var selectedInventories = [];
			$scope.selectedMembers.push($scope.tokens);
			$scope.paidUserId = $scope.tokens.eventUserId+"";
			$scope.membershipTotal += parseFloat($scope.tokens.eventPrice);
			for(var i=0; i<$scope.inventories.length; i++){
				var current = $scope.inventories[i];
				if(current.checked){
					$scope.paidUserId += "#"+current.inventoryId;
					selectedInventories.push(current);
					$scope.membershipTotal += parseFloat(current.unitSellingPrice);
				}
			}
			var index = 0; 
			if($scope.selectedMembers.length > 0){
				index = $scope.selectedMembers.length - 1; 
			}
			$scope.selectedMembers[index]['inventories'] = selectedInventories;
		}
		
		for(var i=0; i<$scope.childUsers.length; i++) {
			var selectedInventories = [];
			var currentUser = $scope.childUsers[i];
			if(currentUser.checked) {
				$scope.selectedMembers.push(currentUser);
				if($scope.paidUserId == ""){
					$scope.paidUserId = currentUser.eventUserId+"";
				}else{
					$scope.paidUserId += ","+currentUser.eventUserId+"";
				}
				
				$scope.membershipTotal += parseFloat(currentUser.eventPrice);
				for(var j=0; j<currentUser.childinventories.length; j++){
					var current = currentUser.childinventories[j];
					if(current.checked){
						$scope.paidUserId += "#"+current.inventoryId;
						selectedInventories.push(current);
						$scope.membershipTotal += parseFloat(current.unitSellingPrice);
					}
				}
				var index = 0; 
				if($scope.selectedMembers.length > 0){
					index = $scope.selectedMembers.length - 1; 
				}
				$scope.selectedMembers[index]['inventories'] = selectedInventories;
			}
		}
		$scope.membershipTotalPayment = $scope.membershipTotal * 100;
		var buttonText = "Pay Now ($"+$scope.membershipTotalPayment+")"
		$(".eway-button > span").text(buttonText);
	}
	
	$scope.onBack = function() {
		$scope.hideMembershipRenewal = false;
		$scope.hideConfirmation = true;
	}
	
	/*$scope.onContinue = function() {
		$state.go("payment");
	}*/
});

app.controller('inventoryController',function($scope,$http,$state,$window,$upload){
	$scope.gridOptionsInfo = {};
	$scope.companyData = []; 
	//get all companies
	$http({method:'GET',url:'wasps/company/list', data: {}})
	 .success(function(respnose){
		 $scope.companyData = respnose;
	 }).error(function(data){
		 console.log("error");
	});
	
	$scope.initializeInventoryGrid = function(){
		//get all inventories
		$http({method:'GET',url:'wasps/inventory/all', data: {}})
		 .success(function(response){
			 $scope.gridOptionsInfo.data = response;
		 }).error(function(data){
			 console.log("error");
		});
		
		
		
		/*inventory grid*/ 
		$scope.gridOptionsInfo.columnDefs = [
		                                      {name: 'inventoryId', displayName: 'id', enableFiltering:false, visible:false },
		                                      {name: 'description', displayName: 'Description',width:'30%'},
		                                      {name: 'unitSellingPrice', displayName: 'Unit Price',width:'25%'},
		                                      {name: 'company.companyName', displayName: 'Company',width:'25%'},
		                                      {name: 'delete', displayName: 'Delete',enableFiltering: false,width:'35%',cellTemplate:'<div class="col-sm-12"><i ng-click="grid.appScope.deleteRow(row)" class="glyphicon glyphicon-trash col-sm-4" style="margin-top: 4px;"></i></div>'}
		                                      /*{name: 'action', displayName: 'Action',enableFiltering: false,width:'35%',cellTemplate:'<div class="col-sm-12"><i ng-click="grid.appScope.edit(row)" class="glyphicon glyphicon-edit col-sm-4" style="margin-top: 4px;" ></i><i ng-click="grid.appScope.deleteRow(row)" class="glyphicon glyphicon-trash col-sm-4" style="margin-top: 4px;"></i></div>'}*/
	      ];
		
		$scope.gridOptionsInfo.data = [];
	}
	
	$scope.goToCreateInventory = function(){
		$state.go("createInventory");
	}
	
	var file = null;
	$scope.getImage = function($files) {
		file = $files[0];
		$scope.inventoryImage = file;
	};
	
	$scope.onShowInventories = function() {
		$state.go("inventory");
	}
	
	$scope.onSaveInventory = function(data) {
		$upload.upload({
			url : 'wasps/inventory/save',
			data : data,
			file : file,
			method : 'post'
		}).success(function(data) {
			console.log("success");
			$state.go("inventory");
			 toastr.success('Successfully created !!!', 'INVENTORY');
		 }).error(function(data){
			console.log("error");
			toastr.error('Error for creating invontory.', 'INVENTORY')
		});
	}
	
	$scope.deleteRow = function(row) {
		 bootbox.dialog({
	            message: "Do you want to delete this Inventory?",
	            title: "Delete Inventory",
	            buttons: {
	                success: {
	                    label: "Yes ",
	                    className: "btn-success",
	                    callback: function() {
	                    	console.log("Inventory Row id ::: ",row.entity.inventoryId);
	                      $http({method:'DELETE',url:'wasps/deleteInventoryById/'+row.entity.inventoryId, data: {}})
	                       .success(function(response){
								 $scope.gridOptionsInfo.data = response;
								 $scope.initializeInventoryGrid();
								 toastr.success('Successfully deleted.', 'INVENTORY');
			                     console.log("Inventory Deleted" );
							 }).error(function(data){
								 console.log("error");
								 toastr.error('Error for delete Invontory.', 'INVENTORY')
							 })	                    
	                    }
	                },
	                danger: {
	                    label: "No ",
	                    className: "btn-danger",
	                    callback: function() {
	                    	
	                    }
	                }
	            }
		 });
	};		
	
});

app.controller('userController',function($scope,$http,$state){
	$scope.gridOptionsInfo = {};
	$scope.userListData = []; 
	
	//get all users
	$http({method:'GET',url:'wasps/user/all', data: {}})
	 .success(function(response){
		 console.log(response);
		 $scope.userListData = response;
		 $scope.gridOptionsInfo.data = response;
	 }).error(function(data){
		 console.log("error");
	});
	
	$scope.initializeUserGrid = function(){
		
		/*user grid*/ 
		$scope.gridOptionsInfo.columnDefs = [
		                                      {name: 'auth_id', displayName: 'id', enableFiltering:false, visible:false },
		                                      {name: 'name', displayName: 'Name',width:'40%'},
		                                      {name: 'username', displayName: 'User Name',width:'30%'},
		                                      {name: 'parent_name', displayName: 'Parent',width:'30%'},
		                                      /*{name: 'action', displayName: 'Action',enableFiltering: false,width:'35%',cellTemplate:'<div class="col-sm-12"><i ng-click="grid.appScope.edit(row)" class="glyphicon glyphicon-edit col-sm-4" style="margin-top: 4px;" ></i><i ng-click="grid.appScope.deleteRow(row)" class="glyphicon glyphicon-trash col-sm-4" style="margin-top: 4px;"></i></div>'}*/
	      ];
		
		$scope.gridOptionsInfo.data = [];
	}
	
	$scope.goToCreateUser = function(){
		$state.go("createUser");
	}
	
	$scope.onShowUsers = function() {
		$state.go("user");
	}
	
	$scope.onSaveUser = function(data) {
		$http({method:'POST',url:'wasps/user/save', data: data})
		 .success(function(response){
			 $state.go("user");
		 }).error(function(data){
			 console.log("error");
		});
	}
	
});