package com.aptitekk.aptictrl.rest.controllers.api;

import com.aptitekk.aptictrl.rest.controllers.api.annotations.APIController;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@APIController
public class HerokuController extends APIControllerAbstract {

    private static final String HEROKU_URL = "https://api.heroku.com/";
    private static final String HEROKU_API_KEY = "10a19878-d245-46fd-9e87-77b520a3067c";
    private static final Map<String, String> HEROKU_HEADERS;

    static {
        HEROKU_HEADERS = new HashMap<>();
        HEROKU_HEADERS.put("Accept", "application/vnd.heroku+json; version=3");
        HEROKU_HEADERS.put("Authorization", "Bearer " + HEROKU_API_KEY);
        HEROKU_HEADERS.put("Content-Type", "application/json");
    }

    private String getHeroku(String endpoint) {
        try {
            return Unirest.get(HEROKU_URL + endpoint)
                    .headers(HEROKU_HEADERS)
                    .asString()
                    .getBody();
        } catch (UnirestException e) {
            this.logService.logException(getClass(), e, "Could not get from Heroku.");
        }

        return null;
    }

    private String patchHeroku(String endpoint, JsonNode jsonNode) {
        try {
            return Unirest.patch(HEROKU_URL + endpoint)
                    .headers(HEROKU_HEADERS)
                    .body(jsonNode)
                    .asString()
                    .getBody();
        } catch (UnirestException e) {
            this.logService.logException(getClass(), e, "Could not patch to Heroku.");
        }

        return null;
    }

    @RequestMapping(value = "/heroku/apps", method = RequestMethod.GET)
    public ResponseEntity<?> getApps() {
        if (!authService.isUserSignedIn())
            return unauthorized();

        String apps = getHeroku("apps");

        if (apps != null)
            return ok(apps);

        return serverError();
    }

    @RequestMapping(value = "/heroku/apps/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getAppByName(@PathVariable String name) {
        if (!authService.isUserSignedIn())
            return unauthorized();

        String app = getHeroku("apps/" + name);

        if (app != null)
            return ok(app);

        return serverError();
    }

    @RequestMapping(value = "/heroku/apps/{name}/maintenance", method = RequestMethod.PUT)
    public ResponseEntity<?> setAppMaintenanceModeStatus(@PathVariable String name, @RequestBody() Boolean enabled) {
        if (!authService.isUserSignedIn())
            return unauthorized();

        if (enabled == null)
            return badRequest("Body must be true or false.");

        String app = patchHeroku("apps/" + name,
                new JsonNode(
                        new JSONObject()
                                .put("maintenance", enabled)
                                .toString()
                )
        );

        if (app != null)
            return ok(app);

        return serverError();
    }

    @RequestMapping(value = "/heroku/apps/{name}/releases", method = RequestMethod.GET)
    public ResponseEntity<?> getAppReleases(@PathVariable String name) {
        if (!authService.isUserSignedIn())
            return unauthorized();

        String releases = getHeroku("apps/" + name + "/releases");

        if (releases != null)
            return ok(releases);

        return serverError();
    }

    @RequestMapping(value = "/heroku/apps/{name}/dynos", method = RequestMethod.GET)
    public ResponseEntity<?> getAppDynos(@PathVariable String name) {
        if (!authService.isUserSignedIn())
            return unauthorized();

        String dynos = getHeroku("apps/" + name + "/dynos");

        if (dynos != null)
            return ok(dynos);

        return serverError();
    }

}
