import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BoardComponent} from './components/board/board.component';
import {PitComponent} from './components/pit/pit.component';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {PlayerComponent} from './components/player/player.component';
import {ErrorPaneComponent} from './shared/components/error-pane/error-pane.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {MenuComponent} from './components/menu/menu.component';

@NgModule({
    declarations: [
        AppComponent,
        BoardComponent,
        PitComponent,
        PlayerComponent,
        ErrorPaneComponent,
        MenuComponent
    ],
    imports: [
        BrowserModule,
        FontAwesomeModule,
        HttpClientModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: HttpLoaderFactory,
                deps: [HttpClient]
            }
        })
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}
