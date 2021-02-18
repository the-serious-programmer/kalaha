import {Component, Input} from '@angular/core';

@Component({
    selector: 'kahala-error-pane',
    templateUrl: './error-pane.component.html',
    styleUrls: ['./error-pane.component.css']
})
export class ErrorPaneComponent {
    @Input() errorKeys: string[];

    constructor() {
    }
}
