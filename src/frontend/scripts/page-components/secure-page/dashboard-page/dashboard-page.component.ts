/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {Component, OnInit} from "@angular/core";
import {APIService} from "../../../services/singleton/api.service";
import {AuthService} from "../../../services/singleton/auth.service";
import {HerokuService} from "../../../services/singleton/heroku.service";
import {HerokuApp} from "../../../models/heroku-app.model";
import {LoaderService} from "../../../services/singleton/loader.service";

@Component({
    selector: 'dashboard-page',
    templateUrl: 'dashboard-page.component.html',
    styleUrls: ['dashboard-page.component.css']
})
export class DashboardPageComponent implements OnInit {

    private apps: HerokuApp[];

    constructor(protected apiService: APIService,
                private authService: AuthService,
                private herokuService: HerokuService,
                private loaderService: LoaderService) {
    }

    ngOnInit(): void {
        this.loaderService.startLoading();
        this.herokuService.getApps().subscribe(apps => {
            this.apps = apps;
            this.loaderService.stopLoading();
        });
    }

    onVisitAppUrl(app: HerokuApp) {
        let win = window.open(app.web_url, '_blank');
        if (win) {
            win.focus();
        } else {
            alert('The App URL could not be opened. Please allow popups for this website.');
        }
    }
}