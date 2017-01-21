/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {Component, Input, ContentChildren, QueryList} from "@angular/core";
import {NavigationLink} from "./navigation-link.model";
import {NavigationLinkComponent} from "./navigation-link.component";

@Component({
    selector: 'navigation',
    templateUrl: 'navigation.component.html',
    styleUrls: ['navigation.component.css']
})
export class NavigationComponent {

    @Input() links: NavigationLink[];
    @Input() horizontal: boolean = false;

    @ContentChildren(NavigationLinkComponent) protected navigationLinkComponents: QueryList<NavigationLinkComponent>;

}