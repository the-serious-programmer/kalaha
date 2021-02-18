import {AppComponent} from './app.component';
import {TranslateService} from '@ngx-translate/core';

describe('AppComponent', () => {
    const defaultLanguage = 'nl';
    let appComponent: AppComponent;
    const translateServiceMock = ({
        setDefaultLang() {
        }
    } as unknown) as TranslateService;
    let defaultLangSpy;

    beforeEach(() => {
        defaultLangSpy = spyOn(translateServiceMock, 'setDefaultLang');
        appComponent = new AppComponent(translateServiceMock);
    });

    it('should create the app and set the default language for translation', () => {
        expect(defaultLangSpy).toHaveBeenCalledWith(defaultLanguage);
    });
});
