package sanchez.sanchez.sergio.bullkeeper.domain.service;


import org.json.JSONObject;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public interface IGoogleMapGateway {
	
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
