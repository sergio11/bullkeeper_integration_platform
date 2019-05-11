package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.apache.http.client.methods.HttpGet;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IMapGateway;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import java.io.InputStream;
import org.json.JSONObject;

/**
 * Here Map Gateway 
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class HereMapGatewayImpl implements IMapGateway {
	
	private static Logger logger = LoggerFactory.getLogger(HereMapGatewayImpl.class);
	
	/**
	 * Reverse Geocode URL
	 */
	private final String REVERSE_GEOCODE_URL = "https://reverse.geocoder.api.here.com/6.2/reversegeocode.json?prox=%f,%f&mode=retrieveAddresses&maxresults=1&gen=9&app_id=%s&app_code=%s";
	
	/**
	 * Here App Id
	 */
	 @Value("{here.app.id}")
	private String hereAppId;
	
	/**
	 * Here App Code
	 */
	 @Value("{here.app.code}")
	private String hereAppCode;
	
	/**
	 * Get Location Info
	 */
	@Override
	public JSONObject getLocationInfo(final Double latitude, final Double longitude) {
		Assert.notNull(latitude, "Latitude can not be null");
        Assert.notNull(longitude, "Longitude can not be null");

        JSONObject locationJsonObject = new JSONObject();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            final String url = String.format(REVERSE_GEOCODE_URL, latitude, longitude, hereAppId, hereAppCode);

            logger.error("URL -> " + url);
            final StringBuilder stringBuilder = new StringBuilder();
            HttpResponse response = httpClient.execute(new HttpGet(url));

            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }

            locationJsonObject = new JSONObject(stringBuilder.toString());

        } catch (final Exception ex) {
            logger.error(ex.getMessage());
        }
        
       return locationJsonObject;

	}

	/**
	 * Get Formatted Address
	 */
	@Override
	public String getFormattedAddress(final Double latitude, final Double longitude) {
		final JSONObject ret = getLocationInfo(latitude, longitude);

        String location_string = null;
        try {
            final JSONObject location = ret.getJSONObject("Response").getJSONArray("View").getJSONObject(0)
            		.getJSONArray("Result").getJSONObject(0).getJSONObject("Location").getJSONObject("Address");
            location_string = location.getString("label");
        } catch (Exception e) {
            e.printStackTrace();
        }

		return location_string;
	}

}
