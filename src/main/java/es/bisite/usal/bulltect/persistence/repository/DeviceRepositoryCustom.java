package es.bisite.usal.bulltect.persistence.repository;


/**
 *
 * @author sergio
 */
public interface DeviceRepositoryCustom {
    void updateDeviceToken(String deviceId, String newToken);
}
