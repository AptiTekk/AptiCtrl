/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {Component, OnInit} from "@angular/core";
import {NavigationLink} from "../navigation/navigation-link.model";
import {AuthService} from "../../services/singleton/auth.service";
import {User} from "../../models/user.model";
import {Router} from "@angular/router";

@Component({
    selector: 'app-sidebar',
    templateUrl: 'sidebar.component.html',
    styleUrls: ['sidebar.component.css']
})
export class SidebarComponent implements OnInit {

    public reservationManagementLinks: NavigationLink[] = [
        {icon: 'hourglass-half', label: 'Pending', path: ['', 'secure', 'management', 'pending']},
        {icon: 'calendar-check-o', label: 'Approved', path: ['', 'secure', 'management', 'approved']},
        {icon: 'calendar-times-o', label: 'Rejected', path: ['', 'secure', 'management', 'rejected']},
        {icon: 'calendar', label: 'Calendar', path: ['', 'secure', 'management', 'calendar']}
    ];

    public configurationLinks: NavigationLink[] = [
        {icon: 'tags', label: 'Resources', path: ['', 'secure', 'configuration', 'resources']},
        {icon: 'user', label: 'Users', path: ['', 'secure', 'configuration', 'users']},
        {icon: 'unlock', label: 'Permissions', path: ['', 'secure', 'configuration', 'permissions']},
        {icon: 'cog', label: 'Properties', path: ['', 'secure', 'configuration', 'properties']}
    ];

    public myLinks: NavigationLink[] = [
        {icon: 'pencil', label: 'My Account', path: ['', 'secure', 'my', 'account']},
        {icon: 'bell', label: 'My Notifications', path: ['', 'secure', 'my', 'notifications']}
    ];

    private user: User;

    constructor(private authService: AuthService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.authService.getUser().subscribe(user => this.user = user);
    }

    onSignOut() {
        this.authService.signOut().subscribe(
            success => this.router.navigate([''])
        );
    }
}