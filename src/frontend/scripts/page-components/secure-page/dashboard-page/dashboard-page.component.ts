import {Component, trigger, state, style, transition, animate} from "@angular/core";
import {APIService} from "../../../services/singleton/api.service";
import {AuthService} from "../../../services/singleton/auth.service";
import {User} from "../../../models/user.model";

@Component({
    selector: 'dashboard-page',
    templateUrl: 'dashboard-page.component.html',
    animations: [
        trigger('sidebarDisplayStatus', [
            state('visible', style({opacity: 1})),
            state('hidden', style({overflow: 'hidden', opacity: 0, 'height': '0', 'pointer-events': 'none'})),
            transition('* => *', animate('300ms'))
        ])
    ]
})
export class DashboardPageComponent {

    currentUser: User;

    constructor(protected apiService: APIService, authService: AuthService) {
        authService.getUser().subscribe(user => this.currentUser = user);
    }

}