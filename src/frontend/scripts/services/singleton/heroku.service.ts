/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {Injectable} from "@angular/core";
import {APIService} from "./api.service";
import {HerokuApp} from "../../models/heroku-app.model";
import {ReplaySubject, Observable} from "rxjs";

@Injectable()
export class HerokuService {

    private apps = new ReplaySubject<HerokuApp[]>(1);

    constructor(private apiService: APIService) {
        this.fetchApps();
    }

    /**
     * Fetches all the Heroku Apps from the server and stores them in a replay subject.
     * See getApps().
     */
    public fetchApps() {
        this.apiService
            .get("/heroku/apps")
            .subscribe(
                apps => this.apps.next(apps),
                err => this.apps.next([])
            );
    }

    /**
     * Gets all the Heroku Apps (from a replay subject)
     * @returns {ReplaySubject<HerokuApp[]>}
     */
    public getApps(): ReplaySubject<HerokuApp[]> {
        return this.apps;
    }

    /**
     * Gets an app by its name from the server.
     * @param name The name of the app to get.
     * @returns HerokuApp if it exists, otherwise undefined.
     */
    public getAppByName(name: string): Observable<HerokuApp> {
        return Observable.create(listener => {
            this.apiService
                .get("heroku/apps/" + name)
                .subscribe(
                    app => listener.next(app),
                    err => listener.next(undefined)
                )
        });
    }

    /**
     * Determines if the given HerokuApp is in maintenance mode.
     * @param app The app to check.
     * @returns {boolean} True if it is in maintenance mode, false if not or error.
     */
    public isAppInMaintenanceMode(app: HerokuApp): Observable<boolean> {
        return Observable.create(listener => {
            this.apiService
                .get("heroku/apps/" + app.name + "/maintenance")
                .subscribe(
                    maintenanceModeEnabled => listener.next(maintenanceModeEnabled),
                    err => listener.next(false)
                )
        });
    }
}