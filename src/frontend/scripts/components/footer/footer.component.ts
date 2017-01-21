/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

import {Component, Input} from "@angular/core";

@Component({
    selector: 'app-footer',
    templateUrl: 'footer.component.html',
    styleUrls: ['footer.component.css']
})
export class FooterComponent {

    @Input()
    includeSpacer: boolean = true;

}