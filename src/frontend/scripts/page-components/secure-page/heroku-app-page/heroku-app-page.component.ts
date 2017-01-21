/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {HerokuApp} from "../../../models/heroku-app.model";
import {HerokuService} from "../../../services/singleton/heroku.service";

@Component({
    selector: 'heroku-app-page',
    templateUrl: 'heroku-app-page.component.html'
})
export class HerokuAppPageComponent implements OnInit {

    app: HerokuApp;

    constructor(private activatedRoute: ActivatedRoute,
                private herokuService: HerokuService) {
    }

    ngOnInit(): void {
        this.activatedRoute.params.subscribe(
            params => {
                let appName: string = params['appName'];

                if (appName) {
                    this.herokuService.getAppByName(appName).subscribe(app => {
                        this.app = app;
                        this.herokuService
                            .isAppInMaintenanceMode(app)
                            .subscribe(
                                status => app.maintenanceModeStatus = status
                            )
                    });
                }
            }
        )
    }

    onVisitAppUrl() {
        let win = window.open(this.app.webUrl, '_blank');
        if (win) {
            win.focus();
        } else {
            alert('The App URL could not be opened. Please allow popups for this website.');
        }
    }
}