package sanchez.sanchez.sergio.masoc.web.rest.response;

public enum DeviceGroupResponseCode implements IResponseCodeTypes {

	ALL_DEVICES_INTO_GROUP(800L), DEVICES_GROUP_CREATED(801L), DEVICES_GROUP_CREATE_FAILED(802L), DEVICE_ADDED_TO_GROUP(803L),
    DEVICE_ADD_TO_GROUP_FAILED(804L), DEVICE_REMOVED_FROM_GROUP(805L), DEVICE_GROUP_NOT_FOUND(806L), NO_DEVICES_INTO_GROUP(807L),
    REMOVE_DEVICE_FROM_GROUP_FAILED(808L), UPDATE_DEVICE_FAILED(809L), DEVICE_TOKEN_UPDATED(810L), DEVICE_TOKEN_SAVED(811L);

    private Long code;

    private DeviceGroupResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
