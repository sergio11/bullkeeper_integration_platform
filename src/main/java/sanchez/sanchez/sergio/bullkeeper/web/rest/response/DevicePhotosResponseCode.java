package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * Device Photos Response Code
 * @author sergiosanchezsanchez
 *
 */
public enum DevicePhotosResponseCode implements IResponseCodeTypes {
	
	DEVICE_PHOTO_SAVED_SUCCESSFULLY(1900L),
	SINGLE_DEVICE_PHOTO_DETAIL(1901L),
	DEVICE_PHOTO_DETAIL_NOT_FOUND(1902L),
	ALL_DEVICE_PHOTOS(1903L),
	NO_DEVICE_PHOTOS_FOUND(1904L),
	ALL_DEVICE_PHOTOS_DELETED(1905L),
	DEVICE_PHOTOS_DELETED(1906L),
	ALL_DEVICE_PHOTOS_DISABLED(1907L),
	SINGLE_DEVICE_PHOTO_DISABLED(1908L);

	private Long code;

    private DevicePhotosResponseCode(final Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }
}
