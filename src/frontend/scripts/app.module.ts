import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {AppComponent} from "./page-components/app/app.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import * as components from "./components";
import * as pageComponents from "./page-components";
import * as singletons from "./services/singleton";
import {routes} from "./routing/routes";
import * as guards from "./routing/guards";
//import * as pipes from "./pipes";
import {vendorImports} from "./vendors/angular-vendors";
import {vendorComponents} from "./vendors/angular-vendors";

const mapImports = (obj: Object) => Object.keys(obj).map(key => obj[key]);

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpModule,
        routes,
        ...vendorImports
    ],
    providers: [
        ...mapImports(singletons),
        ...mapImports(guards)
    ],
    declarations: [
        ...mapImports(components),
        ...mapImports(pageComponents),
        //...mapImports(pipes),
        ...vendorComponents
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}