package com.aptitekk.aptictrl.rest.controllers.api;

import com.aptitekk.aptictrl.core.services.HerokuService;
import com.aptitekk.aptictrl.rest.controllers.api.annotations.APIController;
import com.heroku.api.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@APIController
public class HerokuController extends APIControllerAbstract {

    private final HerokuService herokuService;

    @Autowired
    public HerokuController(HerokuService herokuService) {
        this.herokuService = herokuService;
    }

    @RequestMapping(value = "/heroku/apps", method = RequestMethod.GET)
    public ResponseEntity<?> getApps() {
        if (!authService.isUserSignedIn())
            return unauthorized();

        List<App> apps = this.herokuService.getApps();
        if (apps == null)
            return serverError("Cannot connect to Heroku API");

        return ok(apps);
    }

    @RequestMapping(value = "/heroku/apps/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getAppByName(@PathVariable String name) {
        if(!authService.isUserSignedIn())
            return unauthorized();

        App app = this.herokuService.getAppByName(name);
        if(app == null)
            return badRequest("App not found.");

        return ok(app);
    }

    @RequestMapping(value = "/heroku/apps/{name}/maintenance", method = RequestMethod.GET)
    public ResponseEntity<?> getAppMaintenanceModeStatus(@PathVariable String name) {
        if(!authService.isUserSignedIn())
            return unauthorized();

        return ok(herokuService.isAppInMaintenanceMode(name));
    }

}
