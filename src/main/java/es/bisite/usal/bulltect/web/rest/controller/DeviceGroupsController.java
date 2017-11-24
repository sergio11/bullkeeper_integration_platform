package es.bisite.usal.bulltect.web.rest.controller;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Iterables;

import es.bisite.usal.bulltect.domain.service.IDeviceGroupsService;
import es.bisite.usal.bulltect.exception.DeviceAddToGroupFailedException;
import es.bisite.usal.bulltect.exception.DeviceGroupCreateFailedException;
import es.bisite.usal.bulltect.exception.DeviceGroupNotFoundException;
import es.bisite.usal.bulltect.exception.NoDevicesIntoTheGroupException;
import es.bisite.usal.bulltect.exception.RemoveDeviceFromGroupFailedException;
import es.bisite.usal.bulltect.exception.UpdateDeviceFailedException;
import es.bisite.usal.bulltect.fcm.service.IPushNotificationsService;
import es.bisite.usal.bulltect.persistence.constraints.DeviceShouldExists;
import es.bisite.usal.bulltect.util.Unthrow;
import es.bisite.usal.bulltect.web.dto.request.AddDeviceDTO;
import es.bisite.usal.bulltect.web.dto.request.SaveDeviceDTO;
import es.bisite.usal.bulltect.web.dto.request.UpdateDeviceDTO;
import es.bisite.usal.bulltect.web.dto.response.DeviceDTO;
import es.bisite.usal.bulltect.web.dto.response.DeviceGroupDTO;
import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.DeviceGroupResponseCode;
import es.bisite.usal.bulltect.web.security.userdetails.CommonUserDetailsAware;
import es.bisite.usal.bulltect.web.security.utils.CurrentUser;
import es.bisite.usal.bulltect.web.security.utils.OnlyAccessForParent;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;

import springfox.documentation.annotations.ApiIgnore;


@RestController("RestDeviceGroupsController")
@Validated
@RequestMapping("/api/v1/device-groups")
@Api(tags = "devices-groups", value = "/device-groups/", description = "Manejo de grupo de dispositivos del padre/tutor", produces = "application/json")
public class DeviceGroupsController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DeviceGroupsController.class);

    private final IDeviceGroupsService deviceGroupsService;
    private final IPushNotificationsService pushNotificationsService;

    public DeviceGroupsController(IDeviceGroupsService deviceGroupsService,
            IPushNotificationsService pushNotificationsService) {
        super();
        this.deviceGroupsService = deviceGroupsService;
        this.pushNotificationsService = pushNotificationsService;
    }
    
    

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_DEVICES_INTO_GROUP", nickname = "GET_DEVICES_INTO_GROUP", notes = "Get all devices registered in the group",
            response = List.class)
    public ResponseEntity<APIResponse<Iterable<DeviceDTO>>> getDevicesIntoGroup(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
        Iterable<DeviceDTO> devices = deviceGroupsService.getDevicesFromGroup(selfParent.getUserId().toString());
        if (Iterables.size(devices) == 0) {
            throw new NoDevicesIntoTheGroupException();
        }
        return ApiHelper.<Iterable<DeviceDTO>>createAndSendResponse(DeviceGroupResponseCode.ALL_DEVICES_INTO_GROUP, HttpStatus.OK, devices);

    }

    @RequestMapping(value = "/devices/add", method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "ADD_DEVICE_TO_GROUP", nickname = "ADD_DEVICE_TO_GROUP", notes = "Add Device To Group",
            response = DeviceDTO.class)
    public ResponseEntity<APIResponse<DeviceDTO>> addDeviceToGroup(
    		@ApiParam(name = "device", value = "Device to save", required = true) 
    			@Valid @RequestBody final AddDeviceDTO device,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent
    ) throws InterruptedException, ExecutionException {
    	
    	
    	DeviceGroupDTO deviceGroup = Optional.ofNullable(deviceGroupsService.getDeviceGroupByName(selfParent.getUserId().toString()))
    			.orElseThrow(() -> new DeviceGroupNotFoundException());
        		

    	return pushNotificationsService.addDeviceToGroup(deviceGroup.getNotificationKeyName(), 
    			deviceGroup.getNotificationKey(), device.getRegistrationToken())
    		.handle((groupKey, ex)
                -> Optional.ofNullable(deviceGroupsService.addDeviceToGroup(device.getDeviceId(), device.getRegistrationToken(), deviceGroup.getIdentity()))
                .map(deviceCreated -> ApiHelper.<DeviceDTO>createAndSendResponse(DeviceGroupResponseCode.DEVICE_ADDED_TO_GROUP, HttpStatus.OK, deviceCreated))
                .<DeviceAddToGroupFailedException>orElseThrow(() -> new DeviceAddToGroupFailedException())).get();
    	
 
    }
    
    @RequestMapping(value = "/devices/update", method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "UPDATE_DEVICE_REGISTRATION_TOKEN", nickname = "UPDATE_DEVICE_REGISTRATION_TOKEN", notes = "Update Device Registration Token",
            response = DeviceDTO.class)
    public ResponseEntity<APIResponse<DeviceDTO>> updateDeviceRegistrationToken(
    		@ApiParam(name = "device", value = "Device to update", required = true) 
				@Valid @RequestBody final UpdateDeviceDTO updateDevice,
			@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws InterruptedException, ExecutionException {

    	return Optional.ofNullable(deviceGroupsService.getDeviceByDeviceId(updateDevice.getDeviceId()))
        .map(device -> pushNotificationsService.updateDeviceToken(selfParent.getUserId().toString(), 
        		device.getNotificationKey(), device.getRegistrationToken(), updateDevice.getRegistrationToken())
        		.handle((result, ex) -> {
                            
        			deviceGroupsService.updateDeviceToken(device.getDeviceId(), updateDevice.getRegistrationToken());
        			device.setRegistrationToken( updateDevice.getRegistrationToken());
        			return ApiHelper.<DeviceDTO>createAndSendResponse(DeviceGroupResponseCode.DEVICE_TOKEN_UPDATED, 
        					HttpStatus.OK, device);
        		}))
        .map(completableFuture -> Unthrow.wrap(() -> completableFuture.get()))
        .orElseThrow(() -> new UpdateDeviceFailedException());
    	
    }
    
    
    @RequestMapping(value = "/devices/save", method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "SAVE_DEVICE", nickname = "SAVE_DEVICE", notes = "Save Device into group",
            response = DeviceDTO.class)
    public ResponseEntity<APIResponse<DeviceDTO>> saveDevice(
    		@ApiParam(name = "device", value = "Save Device", required = true) 
				@Valid @RequestBody final SaveDeviceDTO deviceToSave,
			@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws InterruptedException, ExecutionException {

        logger.debug("Save Device with Id " + deviceToSave.getDeviceId() + " And Registration Token " + deviceToSave.getRegistrationToken());
        
     DeviceDTO device = Optional.ofNullable(deviceGroupsService.getDeviceByDeviceId(deviceToSave.getDeviceId()))
             // Device already exists, update registration token if it has changed
            .map((deviceSaved) ->  deviceSaved.getRegistrationToken() != null && 
                        !deviceSaved.getRegistrationToken().isEmpty() &&
                        deviceSaved.getRegistrationToken().equals(deviceToSave.getRegistrationToken()) ? 
                        deviceSaved : Unthrow.wrap(() -> pushNotificationsService.updateDeviceToken(selfParent.getUserId().toString(), 
                                deviceSaved.getNotificationKey(), deviceSaved.getRegistrationToken(), deviceSaved.getRegistrationToken())
        		.handle((result, ex) -> {
                            if(ex != null) {
                                logger.error(ex.getMessage());
                                throw new DeviceAddToGroupFailedException();
                            }
                            logger.debug("Registration Token for device " + deviceSaved.getDeviceId() + " updated");
                            deviceGroupsService.updateDeviceToken(deviceSaved.getDeviceId(), deviceToSave.getRegistrationToken());
                            deviceSaved.setRegistrationToken( deviceToSave.getRegistrationToken());
                            return deviceSaved;
        		}).get()))
             // Device not Exists. First check if device group already exists
        .orElseGet(() ->  Optional.ofNullable(deviceGroupsService.getDeviceGroupByName(selfParent.getUserId().toString()))
        		.map(deviceGroup -> Unthrow.wrap(() -> {
        			// Device Group exists. Add Device to this group
        			return pushNotificationsService.addDeviceToGroup(
                                        deviceGroup.getNotificationKeyName(), 
                			deviceGroup.getNotificationKey(),
                                        deviceToSave.getRegistrationToken())
                                        .handle((groupKey, ex)  -> {
                                            
                                            if(ex != null){
                                                logger.error(ex.getMessage());
                                                throw new DeviceAddToGroupFailedException();
                                            }
                                            logger.debug("Device Group exists. Add Device to this group");
                                            return Optional
                                                        .ofNullable(deviceGroupsService.addDeviceToGroup(
                                                                deviceToSave.getDeviceId(), 
                                                                deviceToSave.getRegistrationToken(), 
                                                                deviceGroup.getIdentity()))
                                                        .<DeviceAddToGroupFailedException>orElseThrow(() -> new DeviceAddToGroupFailedException());
                                        }).get();
                            }))
                // Device Group not exists. Create new Device Group with this device
        		.orElseGet(() -> Unthrow.wrap(() -> pushNotificationsService.createNotificationGroup(selfParent.getUserId().toString(), 
        				Collections.singletonList(deviceToSave.getRegistrationToken()))
                                .handle((response, ex) -> {
                                    
        			if (
                                        ex != null || 
                                        response == null || 
                                        response.getBody() == null || 
                                        ( !response.getBody().containsKey("notification_key") 
                                        || response.getBody().get("notification_key").isEmpty())) {
                                    throw new DeviceGroupCreateFailedException();
                                }
                                
                                String groupKey = response.getBody().get("notification_key");
                                logger.debug("Group Key -> " + 	groupKey);
        			logger.debug("Device Group Created With Key -> " + groupKey);
        			DeviceGroupDTO deviceGroup = deviceGroupsService.createDeviceGroup(groupKey, new ObjectId(selfParent.getUserId().toString()));
        			logger.debug("Device Group -> " + deviceGroup.toString());
        			return deviceGroupsService.addDeviceToGroup(deviceToSave.getDeviceId(), deviceToSave.getRegistrationToken(), deviceGroup.getIdentity());
        		}).get())));
       
     
     
     return ApiHelper.<DeviceDTO>createAndSendResponse(
             DeviceGroupResponseCode.DEVICE_TOKEN_SAVED, HttpStatus.OK, device);
    	
    }
    

    @RequestMapping(value = "/devices/{id}/delete", method = RequestMethod.DELETE)
    @OnlyAccessForParent
    @ApiOperation(value = "DELETE_DEVICE_FROM_GROUP", nickname = "DELETE_DEVICE_FROM_GROUP", notes = "Delete Device From Group",
            response = String.class)
    public ResponseEntity<APIResponse<String>> deleteDeviceFromGroup(
    		@ApiParam(name = "id", value = "Device Id", required = true)
            	@Valid @DeviceShouldExists(message = "{device.should.exists}")
             		@PathVariable String id,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent
    ) {
    	
        return Optional.ofNullable(deviceGroupsService.getDeviceByDeviceId(id))
                .map(device -> pushNotificationsService.removeDeviceFromGroup(device.getNotificationKeyName(), 
                		device.getNotificationKey(), device.getRegistrationToken())
                		.handle((groupKey, ex)
                				-> Optional.ofNullable(deviceGroupsService.removeDevice(device.getDeviceId()))
                					.map(deviceRemoved -> ApiHelper.<String>createAndSendResponse(DeviceGroupResponseCode.DEVICE_REMOVED_FROM_GROUP,
                									HttpStatus.OK, "Device Removed"))
		                        .<RemoveDeviceFromGroupFailedException>orElseThrow(() -> {
		                            throw new RemoveDeviceFromGroupFailedException();
		                        })))
                .map(completableFuture -> Unthrow.wrap(() -> completableFuture.get()))
                .<RemoveDeviceFromGroupFailedException>orElseThrow(() -> {
                    throw new RemoveDeviceFromGroupFailedException();
                });
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(deviceGroupsService, "Device Group Service can not be null");
        Assert.notNull(pushNotificationsService, "Push Notification Service can not be null");
    }
}
