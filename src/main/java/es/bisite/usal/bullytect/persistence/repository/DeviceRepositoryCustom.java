package es.bisite.usal.bullytect.persistence.repository;


/**
 *
 * @author sergio
 */
public interface DeviceRepositoryCustom {
    void updateDeviceToken(String deviceId, String newToken);
}
