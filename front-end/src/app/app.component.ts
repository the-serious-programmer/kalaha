import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
    selector: 'kahala-root',
    templateUrl: './app.component.html'
})
export class AppComponent {
    private static DEFAULT_LANGUAGE = 'nl';
    title = 'Kahala';

    constructor(translate: TranslateService) {
        translate.setDefaultLang(AppComponent.DEFAULT_LANGUAGE);
    }
}
