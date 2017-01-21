package com.aptitekk.aptictrl.core.services;

import com.heroku.api.App;
import com.heroku.api.HerokuAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class HerokuService {

    private final LogService logService;
    private HerokuAPI herokuAPI;
    private String apiKey = System.getenv("HEROKU_API_KEY");

    @Autowired
    public HerokuService(LogService logService) {
        this.logService = logService;
    }

    @PostConstruct
    private void init() {
        logService.logInfo(getClass(), "Starting Heroku API.");
        this.herokuAPI = new HerokuAPI(apiKey);
        if (this.herokuAPI.getConnection() == null)
            this.logService.logError(getClass(), "Could not connect to Heroku API.");
    }

    /**
     * Gets a list of all apps attached to the account.
     *
     * @return A list of apps.
     */
    public List<App> getApps() {
        return this.herokuAPI.listApps();
    }

    /**
     * Gets an app by its name
     *
     * @param name The name of the app.
     * @return The app.
     */
    public App getAppByName(String name) {
        return this.herokuAPI.getApp(name);
    }

    /**
     * Determines if the app by name is in maintenance mode or not.
     *
     * @param name The name of the app to check.
     * @return True if the app is in maintenance mode, false otherwise.
     */
    public boolean isAppInMaintenanceMode(String name) {
        return this.herokuAPI.isMaintenanceModeEnabled(name);
    }

}
