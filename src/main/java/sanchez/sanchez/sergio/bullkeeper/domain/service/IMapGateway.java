package sanchez.sanchez.sergio.bullkeeper.domain.service;


import org.json.JSONObject;

/**
 * Map Gateway
 * @author sergiosanchezsanchez
 *
 */
public interface IMapGateway {
	
	/**
     * Get Location Info
     * @param latitude
     * @param longitude
     * @return
     */
    JSONObject getLocationInfo(final Double latitude, final Double longitude);

    /**
     * Get Formatted Address
     * @param latitude
     * @param longitude
     * @return
     */
    String getFormattedAddress(final Double latitude, final Double longitude);

}
