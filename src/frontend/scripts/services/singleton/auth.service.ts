import {Injectable} from "@angular/core";
import {APIService} from "./api.service";
import {Observable, ReplaySubject} from "rxjs";
import {Headers} from "@angular/http";
import {User} from "../../models/user.model";

@Injectable()
export class AuthService {

    private user: ReplaySubject<User> = new ReplaySubject<User>(1);
    private authMessage: ReplaySubject<string> = new ReplaySubject<string>(1);

    constructor(private apiService: APIService) {
        this.reloadUser();
    }

    /**
     * Forces a reload of the user from the REST API
     */
    public reloadUser(): void {
        this.apiService.get("auth/sign-in").subscribe(
            response => this.user.next(<User>response),
            err => this.user.next(undefined));
    }

    /**
     * @returns The User ReplaySubject which is updated infrequently.
     */
    public getUser(): ReplaySubject<User> {
        return this.user;
    }

    /**
     * @returns The auth message (a message that should be shown to users) ReplaySubject
     */
    public getAuthMessage(): ReplaySubject<string> {
        return this.authMessage;
    }

    /**
     * Signs the user into AptiBook using the credentials provided.
     * @param emailAddress The email address of the user.
     * @param password The password of the user.
     * @returns An observable that returns true if the sign in was successful, false otherwise.
     */
    public signIn(emailAddress: String, password: String): Observable<boolean> {
        return Observable.create(listener => {
            this.apiService.get("auth/sign-in", new Headers({
                "Authorization": "Basic " + btoa(emailAddress + ":" + password)
            })).subscribe(
                response => {
                    if (response) {
                        this.user.next(<User>response);
                        this.authMessage.next(undefined);
                        listener.next(true);
                    } else {
                        this.user.next(undefined);
                        listener.next(false);
                    }
                },
                err => {
                    this.user.next(undefined);
                    this.authMessage.next(err.json().error);
                    listener.next(false);
                })
        });
    }

    /**
     * Signs the user out of AptiBook
     * @returns An observable that returns true if the sign out was successful, false otherwise.
     */
    public signOut(): Observable<boolean> {
        return Observable.create(listener => {
            this.apiService.get("auth/sign-out").subscribe(
                response => {
                    this.user.next(undefined);
                    this.authMessage.next(undefined);
                    listener.next(true)
                },
                err => {
                    this.authMessage.next(undefined);
                    listener.next(false);
                }
            );
        });
    }
}