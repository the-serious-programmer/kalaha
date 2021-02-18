import {ErrorPaneComponent} from './error-pane.component';

describe('ErrorPaneComponent', () => {
    let component: ErrorPaneComponent;

    beforeEach(() => {
        component = new ErrorPaneComponent();
    });

    it('should create and be able to set error keys', () => {
        component.errorKeys = [];
        expect(component).toBeTruthy();
    });
});
