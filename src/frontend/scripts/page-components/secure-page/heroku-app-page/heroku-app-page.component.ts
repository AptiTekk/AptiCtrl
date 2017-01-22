/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {HerokuApp} from "../../../models/heroku-app.model";
import {HerokuService} from "../../../services/singleton/heroku.service";
import {HerokuRelease} from "../../../models/heroku-release.model";
import {LoaderService} from "../../../services/singleton/loader.service";
import {Observable} from "rxjs";
import {HerokuDyno} from "../../../models/heroku-dyno.model";

@Component({
    selector: 'heroku-app-page',
    templateUrl: 'heroku-app-page.component.html',
    styleUrls: ['heroku-app-page.component.css']
})
export class HerokuAppPageComponent implements OnInit {

    app: HerokuApp;
    releases: HerokuRelease[];
    dynos: HerokuDyno[];

    constructor(private activatedRoute: ActivatedRoute,
                private herokuService: HerokuService,
                private loaderService: LoaderService) {
    }

    ngOnInit(): void {
        this.fetchApp();
    }

    private fetchApp() {
        this.loaderService.startLoading();
        this.activatedRoute.params.subscribe(
            params => {
                let appName: string = params['appName'];

                if (appName) {
                    Observable.zip(this.herokuService.getAppByName(appName), this.herokuService.getAppReleases(appName), this.herokuService.getAppDynos(appName))
                        .subscribe(
                            value => {
                                this.app = value[0];
                                this.releases = value[1].reverse();
                                this.dynos = value[2];
                                this.loaderService.stopLoading();
                            }
                        );
                }
            }
        )
    }

    onVisitAppUrl() {
        let win = window.open(this.app.web_url, '_blank');
        if (win) {
            win.focus();
        } else {
            alert('The App URL could not be opened. Please allow popups for this website.');
        }
    }

    onToggleMaintenanceMode() {
        this.herokuService
            .setAppMaintenanceModeEnabled(this.app, !this.app.maintenance)
            .subscribe(
                response => {
                    this.herokuService.fetchApps();
                    this.fetchApp();
                }
            )
    }
}