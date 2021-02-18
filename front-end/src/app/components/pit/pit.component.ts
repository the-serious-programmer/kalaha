import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Pit} from '../../model/pit';
import {PitType} from '../../model/type/pit.type';

@Component({
  selector: 'kahala-pit',
  templateUrl: './pit.component.html',
  styleUrls: ['./pit.component.css']
})
export class PitComponent {
  @Input() pit: Pit;
  @Output() takeTurn = new EventEmitter<Pit>();

  constructor() {
  }

  isScore() {
    return this.pit.type === PitType.SCORE;
  }

  isRegular() {
    return this.pit.type === PitType.REGULAR;
  }

  isEmpty() {
    return this.pit.stones === 0;
  }
}
