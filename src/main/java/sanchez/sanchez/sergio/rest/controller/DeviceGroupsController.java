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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Iterables;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;

import sanchez.sanchez.sergio.dto.request.AddDeviceDTO;
import sanchez.sanchez.sergio.dto.request.UpdateDeviceDTO;
import sanchez.sanchez.sergio.dto.response.DeviceDTO;
import sanchez.sanchez.sergio.dto.response.DeviceGroupDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.DeviceAddToGroupFailedException;
import sanchez.sanchez.sergio.rest.exception.DeviceGroupCreateFailedException;
import sanchez.sanchez.sergio.rest.exception.NoDevicesIntoTheGroupException;
import sanchez.sanchez.sergio.rest.exception.RemoveDeviceFromGroupFailedException;
import sanchez.sanchez.sergio.rest.exception.UpdateDeviceFailedException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.DeviceGroupResponseCode;
import sanchez.sanchez.sergio.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.security.utils.CurrentUser;
import sanchez.sanchez.sergio.security.utils.OnlyAccessForParent;
import sanchez.sanchez.sergio.service.IDeviceGroupsService;
import sanchez.sanchez.sergio.service.IPushNotificationsService;
import sanchez.sanchez.sergio.util.Unthrow;
import springfox.documentation.annotations.ApiIgnore;
import sanchez.sanchez.sergio.persistence.constraints.DeviceShouldExists;
import sanchez.sanchez.sergio.persistence.constraints.DeviceShouldNotExists;


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
    
    
    private DeviceGroupDTO getUserDeviceGroup(String userId) {
    	return Optional.ofNullable(deviceGroupsService.getDeviceGroupByName(userId))
        		.orElseGet(() -> Unthrow.wrap(() -> pushNotificationsService.createNotificationGroup(userId).handle((groupKey, ex) -> {
        			if (ex != null) {
                        throw new DeviceGroupCreateFailedException();
                    }
        			return deviceGroupsService.createDeviceGroup(groupKey, new ObjectId(userId));
        		}).get()));
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
    	
    	DeviceGroupDTO deviceGroup = getUserDeviceGroup(selfParent.getUserId().toString());
    	
    	return pushNotificationsService.addDeviceToGroup(deviceGroup.getNotificationKeyName(), 
    			deviceGroup.getNotificationKey(), device.getRegistrationToken())
    		.handle((groupKey, ex)
                -> Optional.ofNullable(deviceGroupsService.addDeviceToGroup(device.getDeviceId(), device.getRegistrationToken(), deviceGroup.getIdentity()))
                .map(deviceCreated -> ApiHelper.<DeviceDTO>createAndSendResponse(DeviceGroupResponseCode.DEVICE_ADDED_TO_GROUP, HttpStatus.OK, deviceCreated))
                .<DeviceAddToGroupFailedException>orElseThrow(() -> {
                    throw new DeviceAddToGroupFailedException();
                })).get();
    	
 
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
        .<UpdateDeviceFailedException>orElseThrow(() -> {
            throw new UpdateDeviceFailedException();
        });
    	
    }

    @RequestMapping(value = "/devices/{device}/delete", method = RequestMethod.DELETE)
    @OnlyAccessForParent
    @ApiOperation(value = "DELETE_DEVICE_FROM_GROUP", nickname = "DELETE_DEVICE_FROM_GROUP", notes = "Delete Device From Group",
            response = DeviceDTO.class)
    public ResponseEntity<APIResponse<DeviceDTO>> deleteDeviceFromGroup(
    		@ApiParam(name = "device_id", value = "Device Id", required = true)
            	@Valid @DeviceShouldExists(message = "{device.should.exists}")
             		@PathVariable String deviceId,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent
    ) {
    	
        return Optional.ofNullable(deviceGroupsService.getDeviceByDeviceId(deviceId))
                .map(device -> pushNotificationsService.removeDeviceFromGroup(device.getNotificationKeyName(), 
                		device.getNotificationKey(), device.getRegistrationToken())
                		.handle((groupKey, ex)
                				-> Optional.ofNullable(deviceGroupsService.removeDevice(device.getDeviceId()))
                					.map(deviceRemoved -> ApiHelper.<DeviceDTO>createAndSendResponse(DeviceGroupResponseCode.DEVICE_REMOVED_FROM_GROUP,
                									HttpStatus.OK, deviceRemoved))
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
