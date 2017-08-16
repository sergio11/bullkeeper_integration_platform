package sanchez.sanchez.sergio.rest.controller;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Iterables;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.dto.response.DeviceDTO;
import sanchez.sanchez.sergio.dto.response.DeviceGroupDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.DeviceAddToGroupFailedException;
import sanchez.sanchez.sergio.rest.exception.DeviceGroupCreateFailedException;
import sanchez.sanchez.sergio.rest.exception.NoDevicesIntoTheGroupException;
import sanchez.sanchez.sergio.rest.exception.RemoveDeviceFromGroupFailedException;
import sanchez.sanchez.sergio.rest.exception.DeviceGroupNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.DeviceGroupResponseCode;
import sanchez.sanchez.sergio.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.security.utils.CurrentUser;
import sanchez.sanchez.sergio.service.IDeviceGroupsService;
import sanchez.sanchez.sergio.service.IPushNotificationsService;
import sanchez.sanchez.sergio.util.Unthrow;

@Api
@RestController("RestDeviceGroupsController")
@Validated
@RequestMapping("/api/v1/device-groups")
public class DeviceGroupsController {
	
	private static Logger logger = LoggerFactory.getLogger(DeviceGroupsController.class);
	
	private final IDeviceGroupsService deviceGroupsService;
	private final IPushNotificationsService pushNotificationsService;
	

	public DeviceGroupsController(IDeviceGroupsService deviceGroupsService,
			IPushNotificationsService pushNotificationsService) {
		super();
		this.deviceGroupsService = deviceGroupsService;
		this.pushNotificationsService = pushNotificationsService;
	}

	
	@GetMapping(path = "/{name}/all")
    @ApiOperation(value = "GET_DEVICES_INTO_GROUP", nickname = "GET_DEVICES_INTO_GROUP", notes="Get all devices registered in the group", 
    	response = List.class)
	@PreAuthorize("@authorizationService.hasParentRole()")
    public ResponseEntity<APIResponse<Iterable<DeviceDTO>>> getDevicesIntoGroup(
    		@ApiParam(value = "name", required = true) @PathVariable String name) throws Throwable {
		
		Iterable<DeviceDTO> devices = deviceGroupsService.getDevicesFromGroup(name);
		
		if(Iterables.size(devices) == 0)
			throw new NoDevicesIntoTheGroupException();
		
		return ApiHelper.<Iterable<DeviceDTO>>createAndSendResponse(DeviceGroupResponseCode.ALL_DEVICES_INTO_GROUP, HttpStatus.OK, devices);
		
    }
    
    @PutMapping(path = "/{name}/create")
    @ApiOperation(value = "CREATE_DEVICES_GROUP", nickname = "CREATE_DEVICES_GROUP", notes="Create Devices Group", 
    	response = DeviceGroupDTO.class)
    @PreAuthorize("@authorizationService.hasParentRole()")
    public ResponseEntity<APIResponse<DeviceGroupDTO>> createDevicesGroup(
            @ApiParam(value = "name", required = true) @PathVariable String name,
            @CurrentUser CommonUserDetailsAware<ObjectId> selfParent
    ) throws InterruptedException, ExecutionException, Throwable { 	
       return pushNotificationsService.createNotificationGroup(name)
                .handle((groupKey, ex) -> 
	                Optional.ofNullable(deviceGroupsService.createDeviceGroup(name, groupKey, selfParent.getUserId()))
	                	.map(deviceGroup -> ApiHelper.<DeviceGroupDTO>createAndSendResponse(DeviceGroupResponseCode.DEVICES_GROUP_CREATED, HttpStatus.OK, deviceGroup))
	                	.<DeviceGroupCreateFailedException>orElseThrow(() -> { throw new DeviceGroupCreateFailedException(); })
                ).get();
    }
    
    
    @PutMapping(path = "/{group}/devices/{token}/add")
    @ApiOperation(value = "ADD_DEVICE_TO_GROUP", nickname = "ADD_DEVICE_TO_GROUP", notes="Add Device To Group", 
    	response = DeviceDTO.class)
    @PreAuthorize("@authorizationService.hasParentRole()")
    public ResponseEntity<APIResponse<DeviceDTO>> addDeviceToGroup(
            @ApiParam(value = "group", required = true) @PathVariable String group,
            @ApiParam(value = "token", required = true) @PathVariable String token
    ){
        return Optional.ofNullable(deviceGroupsService.getDeviceGroupByName(group))
                .map(deviceGroup -> pushNotificationsService.addDeviceToGroup(deviceGroup.getNotificationKeyName(), deviceGroup.getNotificationKey(), token)
                    .handle((groupKey, ex) -> 
                            Optional.ofNullable(deviceGroupsService.addDeviceToGroup(token, deviceGroup.getIdentity()))
                            	.map(device -> ApiHelper.<DeviceDTO>createAndSendResponse(DeviceGroupResponseCode.DEVICE_ADDED_TO_GROUP, HttpStatus.OK, device))
                            	.<DeviceAddToGroupFailedException>orElseThrow(() -> { throw new DeviceAddToGroupFailedException(); })))
                .map(completableFuture -> Unthrow.wrap(() -> completableFuture.get()))
                .<DeviceGroupNotFoundException>orElseThrow(() -> {throw new DeviceGroupNotFoundException(); });
    }
    
    @DeleteMapping(path = "/{group}/devices/{token}/delete")
    @ApiOperation(value = "DELETE_DEVICE_FROM_GROUP", nickname = "DELETE_DEVICE_FROM_GROUP", notes="Delete Device From Group",
    	response = DeviceDTO.class)
    @PreAuthorize("@authorizationService.hasParentRole()")
    public ResponseEntity<APIResponse<DeviceDTO>> deleteDeviceFromGroup(
            @ApiParam(value = "group", required = true) @PathVariable String group,
            @ApiParam(value = "token", required = true) @PathVariable String token
    ){
        return Optional.ofNullable(deviceGroupsService.getDeviceGroupByName(group))
                .map(deviceGroup -> pushNotificationsService.removeDeviceFromGroup(
                        deviceGroup.getNotificationKeyName(), deviceGroup.getNotificationKey(), token)
                    .handle((groupKey, ex) -> 
                            Optional.ofNullable(deviceGroupsService.removeDevice(token))
                            	.map(device -> ApiHelper.<DeviceDTO>createAndSendResponse(DeviceGroupResponseCode.DEVICE_REMOVED_FROM_GROUP, 
                            			HttpStatus.OK, device))
                            	.<RemoveDeviceFromGroupFailedException>orElseThrow(()-> { throw new RemoveDeviceFromGroupFailedException(); })))
                .map(completableFuture -> Unthrow.wrap(() -> completableFuture.get()))
                .<RemoveDeviceFromGroupFailedException>orElseThrow(()-> { throw new RemoveDeviceFromGroupFailedException(); });
    }


	@PostConstruct
	protected void init(){
		Assert.notNull(deviceGroupsService, "Device Group Service can not be null");
		Assert.notNull(pushNotificationsService, "Push Notification Service can not be null");
	}
}
