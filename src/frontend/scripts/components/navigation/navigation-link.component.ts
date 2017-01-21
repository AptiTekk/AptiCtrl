/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {Component, Input, Output, EventEmitter} from "@angular/core";

@Component({
    selector: 'navigation-link',
    template: ''
})
export class NavigationLinkComponent {

    @Input() label: string;
    @Input() icon: string;
    @Input() active: boolean;
    @Output() selected: EventEmitter<void> = new EventEmitter<void>();

}