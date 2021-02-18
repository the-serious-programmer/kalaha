import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Player} from '../../model/player';
import {Pit} from '../../model/pit';
import {PitSort} from '../../shared/sort/pit.sort';
import {PlayerType} from '../../model/type/player.type';
import {faLongArrowAltLeft, faLongArrowAltRight} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'kahala-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {
  faLongArrowLeft = faLongArrowAltLeft;
  faLongArrowRight = faLongArrowAltRight;
  @Input() player: Player;
  @Output() takeTurn = new EventEmitter<{ playerId: string; pitId: string }>();

  constructor() {
  }

  ngOnInit(): void {
    PitSort.sortPits(this.player, this.player.pits);
  }

  onTakeTurn($event: Pit) {
    this.takeTurn.emit({
      playerId: this.player.id,
      pitId: $event.id
    });
  }

  isFirstPlayer() {
    return this.player.type === PlayerType.FIRST;
  }
}
