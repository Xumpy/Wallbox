package com.xumpy.wallbox.module;

import com.xumpy.wallbox.module.model.Action;
import com.xumpy.wallbox.module.model.Authentication;
import com.xumpy.wallbox.module.model.ChargerResponse;
import com.xumpy.wallbox.module.model.Status;
import com.xumpy.wallbox.module.model.result.Charger;
import com.xumpy.wallbox.module.model.result.Group;
import com.xumpy.wallbox.module.model.result.Response;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;


@Service
public class RestWallbox {
    @Autowired private RestTemplate restTemplate;
    @Value("${wallbox.username}") private String username;
    @Value("${wallbox.password}") private String password;

    private static final String BASEURL = "https://api.wall-box.com/";
    private Authentication authenticate;

    private HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    private HttpHeaders createBearer(){
        return new HttpHeaders() {{
            String authHeader = "Bearer " + authenticate.getJwt();
            set( "Authorization", authHeader );
            set( "Content-Type", "application/json");
        }};
    }

    public RestWallbox(@Autowired RestTemplate restTemplate,
                       @Value("${wallbox.username}") String username,
                       @Value("${wallbox.password}") String password){
        HttpEntity<String> request = new HttpEntity<String>(createHeaders(username, password));
        authenticate = restTemplate.exchange(BASEURL + "auth/token/user", HttpMethod.GET, request, Authentication.class).getBody();
    }

    public void refreshAuthenticateToken(){
        HttpEntity<String> request = new HttpEntity<String>(createHeaders(username, password));
        authenticate = restTemplate.exchange(BASEURL + "auth/token/user", HttpMethod.GET, request, Authentication.class).getBody();
    }

    public List<Group> getGroups(){
        HttpEntity<String> request = new HttpEntity<String>(createBearer());
        Response response = restTemplate.exchange(BASEURL + "v3/chargers/groups", HttpMethod.GET, request, Response.class).getBody();

        return response.getResult().getGroups();
    }

    public Status getStatus(Integer chargerId){
        HttpEntity<String> request = new HttpEntity<String>(createBearer());
        Status status = restTemplate.exchange(BASEURL + "chargers/status/" + chargerId, HttpMethod.GET, request, Status.class).getBody();

        return status;
    }

    public Charger unlockCharger(Integer chargerId){
        HttpEntity<String> request = new HttpEntity<String>("{\"locked\":0}", createBearer());
        ChargerResponse lockingResponse = restTemplate.exchange(BASEURL + "v2/charger/" + chargerId, HttpMethod.PUT, request, ChargerResponse.class).getBody();

        return lockingResponse.getData().getChargerData();
    }

    public Charger lockCharger(Integer chargerId){
        HttpEntity<String> request = new HttpEntity<String>("{\"locked\":1}", createBearer());
        ChargerResponse chargerResponse = restTemplate.exchange(BASEURL + "v2/charger/" + chargerId, HttpMethod.PUT, request, ChargerResponse.class).getBody();

        return chargerResponse.getData().getChargerData();
    }

    public Charger setMaxChargingCurrent(Integer chargerId, Integer maxChargingCurrent){
        HttpEntity<String> request = new HttpEntity<String>("{\"maxChargingCurrent\":" + maxChargingCurrent + "}", createBearer());
        ChargerResponse chargerResponse = restTemplate.exchange(BASEURL + "v2/charger/" + chargerId, HttpMethod.PUT, request, ChargerResponse.class).getBody();

        return chargerResponse.getData().getChargerData();
    }

    public Action pauseChargingSession(Integer chargerId) throws Exception {
        HttpEntity<String> request = new HttpEntity<String>("{\"action\":2}", createBearer());
        try {
            Action action = restTemplate.exchange(BASEURL + "v3/chargers/" + chargerId + "/remote-action", HttpMethod.POST, request, Action.class).getBody();

            return action;
        } catch(Exception exception){
            throw new Exception("Charger could not be paused");
        }
    }

    public Action resumeChargingSession(Integer chargerId) throws Exception {
        HttpEntity<String> request = new HttpEntity<String>("{\"action\":1}", createBearer());
        try {
            Action action = restTemplate.exchange(BASEURL + "v3/chargers/" + chargerId + "/remote-action", HttpMethod.POST, request, Action.class).getBody();

            return action;
        } catch(Exception exception){
            throw new Exception("Charger could not be resumed");
        }
    }

    public void stopCharging(Integer chargerId){
        lockCharger(chargerId);
        try{ pauseChargingSession(chargerId); } catch (Exception exc) { System.out.println(exc.getMessage()); }
    }

    public void startCharging(Integer chargerId){
        unlockCharger(chargerId);
        try{ resumeChargingSession(chargerId); } catch (Exception exc) { System.out.println(exc.getMessage()); }
    }
}
