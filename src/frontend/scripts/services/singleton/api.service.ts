import {Injectable} from "@angular/core";
import {Http, Response, Headers, RequestOptions} from "@angular/http";
import {Observable} from "rxjs";

@Injectable()
export class APIService {

    private apiUrl: string = "/api/";
    private headers: Headers = new Headers({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    });

    constructor(private http: Http) {
    }

    public getApiUrlFromEndpoint(endpoint: string): string {
        return this.apiUrl + APIService.removeTrailingSlash(endpoint);
    }

    private static checkForErrors(response: Response): any {
        if (response.status >= 200 && response.status < 300) {
            if (response.text().length > 0)
                return response.json();
            return undefined;
        } else {
            let error = new Error(response.statusText);
            error['response'] = response;
            throw error;
        }
    }

    private static removeTrailingSlash(path: string): string {
        if (path && path.startsWith("/"))
            path = path.substring(1);
        return path;
    }

    public get(path: string, additionalHeaders?: Headers): Observable<any> {
        let options;
        if (additionalHeaders) {
            let newHeaders: Headers = new Headers(this.headers);
            additionalHeaders.forEach((values: string[], name: string) => {
                values.forEach((value: string) => newHeaders.append(name, value));
            });
            options = new RequestOptions({headers: newHeaders});
        }
        else options = new RequestOptions({headers: this.headers});
        return this.http.get(`${this.apiUrl}${APIService.removeTrailingSlash(path)}`, options)
            .map(APIService.checkForErrors)
            .catch(e => Observable.throw(e));
    }

    public post(path: string, data: any): Observable<any> {
        let options = new RequestOptions({headers: this.headers});
        return this.http.post(`${this.apiUrl}${APIService.removeTrailingSlash(path)}`, data, options)
            .map(APIService.checkForErrors)
            .catch(e => Observable.throw(e));
    }

    public put(path: string, data: any): Observable<any> {
        let options = new RequestOptions({headers: this.headers});
        return this.http.put(`${this.apiUrl}${APIService.removeTrailingSlash(path)}`, data, options)
            .map(APIService.checkForErrors)
            .catch(e => Observable.throw(e));
    }

    public patch(path: string, data?: any): Observable<any> {
        let options = new RequestOptions({headers: this.headers});
        return this.http.patch(`${this.apiUrl}${APIService.removeTrailingSlash(path)}`, data, options)
            .map(APIService.checkForErrors)
            .catch(e => Observable.throw(e));
    }

    public del(path: string): Observable<any> {
        let options = new RequestOptions({headers: this.headers});
        return this.http.delete(`${this.apiUrl}${APIService.removeTrailingSlash(path)}`, options)
            .map(APIService.checkForErrors)
            .catch(e => Observable.throw(e));
    }

}