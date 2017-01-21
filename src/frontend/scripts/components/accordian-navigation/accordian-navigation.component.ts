/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

import {Component, ContentChildren, QueryList, Input, Inject, forwardRef, OnInit, AfterViewInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
    selector: 'accordian-navigation',
    templateUrl: 'accordian-navigation.component.html',
    styleUrls: ['accordian-navigation.component.css']
})
export class AccordianNavigationComponent implements AfterViewInit {

    @ContentChildren(AccordianNavigationComponent) navigations: QueryList<AccordianNavigationComponent>;

    protected parent: AccordianNavigationComponent;

    @Input() icon: string;
    @Input() label: string;
    @Input() link: string[];

    @Input() autoCollapse: boolean = true;
    @Input() canCollapse: boolean = true;
    @Input() expanded: boolean = false;

    constructor(private router: Router) {
    }

    ngAfterViewInit(): void {
        this.navigations.forEach(navigation => {
            if (navigation !== this)
                navigation.parent = this;
        });

        this.navigations.changes.subscribe(
            changes => {
                this.navigations.forEach(navigation => {
                    if (navigation !== this)
                        navigation.parent = this;
                });
            }
        );
    }

    onClick(): void {
        // This navigation has children, it should expand and collapse.
        if (this.navigations.length > 1) {
            // If it can't collapse, we don't want to do anything to it.
            if (this.canCollapse) {
                if (this.expanded)
                    this.collapse();
                else
                    this.expand();
            }
        } else {
            // This navigation has no children. It is a link.
            this.router.navigate(this.link);
        }
    }

    protected expand(): void {
        if (this.parent) {
            this.parent.navigations.forEach(navigation => {
                if (navigation !== this)
                    if (navigation.autoCollapse)
                        navigation.collapse();
            });
        }
        this.expanded = true;
    }

    protected collapse(): void {
        if (this.canCollapse)
            this.expanded = false;
    }

}