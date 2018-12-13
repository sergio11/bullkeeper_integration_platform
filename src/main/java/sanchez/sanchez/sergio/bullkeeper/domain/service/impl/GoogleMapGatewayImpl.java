package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.apache.http.client.methods.HttpGet;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IGoogleMapGateway;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import java.io.InputStream;
import org.json.JSONObject;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class GoogleMapGatewayImpl implements IGoogleMapGateway {
	
	private static Logger logger = LoggerFactory.getLogger(GoogleMapGatewayImpl.class);

	/**
	 * Get Location Info
	 */
	@Override
	public JSONObject getLocationInfo(final Double latitude, final Double longitude) {
		Assert.notNull(latitude, "Latitude can not be null");
        Assert.notNull(longitude, "Longitude can not be null");

        JSONObject locationJsonObject = new JSONObject();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            final String url = String.format("http://maps.google.com/maps/api/geocode/json?latlng=%f,%f&sensor=false", latitude, longitude);

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
            //Get JSON Array called "results" and then get the 0th complete object as JSON
            final JSONObject location = ret.getJSONArray("results").getJSONObject(0);
            // Get the value of the attribute whose name is "formatted_string"
            location_string = location.getString("formatted_address");
        } catch (Exception e) {
            e.printStackTrace();
        }

		return location_string;
	}

}
