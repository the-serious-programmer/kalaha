import {Component, OnInit} from '@angular/core';
import {Game} from '../../model/game';
import {GameService} from '../../services/game/game.service';
import {GameStatus} from '../../model/type/game-status.type';
import {PlayerSort} from '../../shared/sort/player.sort';

@Component({
  selector: 'kahala-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  private static readonly CREATE_GAME_ERROR_KEY = 'GAME_CREATE_ERROR';
  private static readonly TAKE_TURN_ERROR_KEY = 'TAKE_TURN_ERROR';

  game: Game;
  errorKeys: string[] = [];

  constructor(private gameService: GameService) {
  }

  ngOnInit(): void {
    this.createGame();
  }

  isGameEnd() {
    return this.game.status === GameStatus.END;
  }

  onTakeTurn($event: { playerId: string; pitId: string }) {
    this.gameService.takeTurn(this.game.id, $event.playerId, $event.pitId).subscribe(
      (result) => {
        this.handleSuccess(result);
      },
      () => {
        this.handleError(BoardComponent.TAKE_TURN_ERROR_KEY);
      }
    );
  }

  private createGame() {
    this.gameService.createGame().subscribe(
      (result: Game) => {
        this.handleSuccess(result);
      },
      () => {
        this.handleError(BoardComponent.CREATE_GAME_ERROR_KEY);
      }
    );
  }

  private handleSuccess(result: Game) {
    this.errorKeys = [];
    this.game = result;
    this.game.players.sort(PlayerSort.sortPlayersByType);
  }

  private handleError(errorKey: string) {
    this.errorKeys = [errorKey];
  }
}
