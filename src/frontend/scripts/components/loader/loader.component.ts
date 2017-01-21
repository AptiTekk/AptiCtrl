/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

import {Component} from "@angular/core";
import {LoaderService} from "../../services/singleton/loader.service";

@Component({
    selector: 'loader',
    templateUrl: 'loader.component.html',
    styleUrls: ['loader.component.css']
})
export class LoaderComponent {

    loading: boolean;

    constructor(loaderService: LoaderService) {
        loaderService.isLoading().subscribe(loading => this.loading = loading);
    }

}