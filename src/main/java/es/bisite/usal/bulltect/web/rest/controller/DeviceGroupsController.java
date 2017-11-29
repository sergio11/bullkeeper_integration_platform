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
import es.bisite.usal.bulltect.domain.service.ITokenGeneratorService;
import es.bisite.usal.bulltect.exception.DeviceAddToGroupFailedException;
import es.bisite.usal.bulltect.exception.DeviceGroupCreateFailedException;
import es.bisite.usal.bulltect.exception.DeviceGroupNotFoundException;
import es.bisite.usal.bulltect.exception.NoDevicesIntoTheGroupException;
import es.bisite.usal.bulltect.exception.RemoveDeviceFromGroupFailedException;
import es.bisite.usal.bulltect.exception.UpdateDeviceFailedException;
import es.bisite.usal.bulltect.fcm.properties.FCMCustomProperties;
import es.bisite.usal.bulltect.fcm.service.IPushNotificationsService;
import es.bisite.usal.bulltect.helper.IDeviceHelper;
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

    private final IDeviceHelper deviceHelper;
    private final IDeviceGroupsService deviceGroupsService;

    public DeviceGroupsController(IDeviceHelper deviceHelper, IDeviceGroupsService deviceGroupsService) {
        super();
        this.deviceHelper = deviceHelper;
        this.deviceGroupsService = deviceGroupsService;
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
    ){
    	
    	
    	return deviceHelper.addDeviceToGroup(selfParent.getUserId(), device.getDeviceId(), device.getRegistrationToken())
    			.map(deviceCreated -> ApiHelper.<DeviceDTO>createAndSendResponse(DeviceGroupResponseCode.DEVICE_ADDED_TO_GROUP, HttpStatus.OK, deviceCreated))
    			.orElseThrow(() -> new DeviceAddToGroupFailedException());
    	
    }
    
    @RequestMapping(value = "/devices/update", method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "UPDATE_DEVICE_REGISTRATION_TOKEN", nickname = "UPDATE_DEVICE_REGISTRATION_TOKEN", notes = "Update Device Registration Token",
            response = DeviceDTO.class)
    public ResponseEntity<APIResponse<DeviceDTO>> updateDeviceRegistrationToken(
    		@ApiParam(name = "device", value = "Device to update", required = true) 
				@Valid @RequestBody final UpdateDeviceDTO updateDevice,
			@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) {
    	
    	return deviceHelper.updateDevice(selfParent.getUserId(), updateDevice.getDeviceId(), updateDevice.getRegistrationToken())
    			.map((deviceUpdated) -> ApiHelper.<DeviceDTO>createAndSendResponse(DeviceGroupResponseCode.DEVICE_TOKEN_UPDATED, 
    					HttpStatus.OK, deviceUpdated))
    			.orElseThrow(() -> new UpdateDeviceFailedException());
    	
    }
    
    
    @RequestMapping(value = "/devices/save", method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "SAVE_DEVICE", nickname = "SAVE_DEVICE", notes = "Save Device into group",
            response = DeviceDTO.class)
    public ResponseEntity<APIResponse<DeviceDTO>> saveDevices(
    		@ApiParam(name = "device", value = "Save Device", required = true) 
				@Valid @RequestBody final SaveDeviceDTO deviceToSave,
			@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent){

        logger.debug("Save Device with Id " + deviceToSave.getDeviceId() + " And Registration Token " + deviceToSave.getRegistrationToken());
        
        DeviceDTO deviceSaved = deviceHelper.createOrUpdateDevice(selfParent.getUserId(), deviceToSave.getDeviceId(), deviceToSave.getRegistrationToken());
        
        return ApiHelper.<DeviceDTO>createAndSendResponse(
                DeviceGroupResponseCode.DEVICE_TOKEN_SAVED, HttpStatus.OK, deviceSaved);
        
        
    	
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
    	
    	logger.debug("Remove device with id " + id);
    	
    	return deviceHelper.removeDeviceFromGroup(id)
    			.map(deviceRemoved -> ApiHelper.<String>createAndSendResponse(DeviceGroupResponseCode.DEVICE_REMOVED_FROM_GROUP,
						HttpStatus.OK, "Device Removed"))
    			.<RemoveDeviceFromGroupFailedException>orElseThrow(() -> {
                    throw new RemoveDeviceFromGroupFailedException();
    			});
    			
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(deviceHelper, "DeviceHelper can not be null");
        Assert.notNull(deviceGroupsService, "DeviceGroupsService can not be null");
    }
}
