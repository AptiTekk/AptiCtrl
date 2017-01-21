/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

import {Component, Input, ContentChildren, QueryList} from "@angular/core";
import {PanelFooterComponent} from "./panel-footer/panel-footer.component";

@Component({
    selector: 'panel',
    templateUrl: 'panel.component.html',
    styleUrls: ['panel.component.css']
})
export class PanelComponent {

    @ContentChildren(PanelFooterComponent) footer: QueryList<PanelFooterComponent>;

    @Input() type: string = 'default';

    @Input() title: string;

    @Input() panelClass: string;

    @Input() panelBodyClass: string;

}