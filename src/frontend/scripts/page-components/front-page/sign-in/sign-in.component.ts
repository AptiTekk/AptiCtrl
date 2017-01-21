/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

import {Component, ViewChild, AfterViewInit} from "@angular/core";
import {Router, ActivatedRoute} from "@angular/router";
import {AuthService} from "../../../services/singleton/auth.service";
import {AlertComponent} from "../../../components/alert/alert.component";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoaderService} from "../../../services/singleton/loader.service";

@Component({
    selector: 'sign-in',
    templateUrl: 'sign-in.component.html'
})
export class SignInComponent implements AfterViewInit {

    @ViewChild('loginAlert')
    loginAlert: AlertComponent;

    signInFormGroup: FormGroup;

    googleSignInUrl: string;

    constructor(formBuilder: FormBuilder,
                private router: Router,
                private activeRoute: ActivatedRoute,
                private authService: AuthService,
                private loaderService: LoaderService) {

        this.signInFormGroup = formBuilder.group({
            emailAddress: [null, Validators.required],
            password: [null, Validators.required]
        });
    }

    ngAfterViewInit(): void {
        //Check for errors in the parameters
        this.activeRoute.queryParams.subscribe(
            params => {
            });

        //Subscribe to auth messages
        this.authService.getAuthMessage().subscribe(message => this.loginAlert.display(message));
    }

    onSubmit() {
        this.loaderService.startLoading();
        this.authService
            .signIn(this.signInFormGroup.controls['emailAddress'].value, this.signInFormGroup.controls['password'].value)
            .subscribe(
                successful => {
                    if (successful)
                        this.router.navigateByUrl("/secure").then(() => this.loaderService.stopLoading());
                    else
                        this.loaderService.stopLoading();
                });
    }

    onGoogleSignIn() {
        window.location.href = this.googleSignInUrl;
    }

}