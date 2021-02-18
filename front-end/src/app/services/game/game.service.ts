import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Game} from '../../model/game';
import {Observable} from 'rxjs';
import {BaseService} from '../base-service';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private static GAME_ENDPOINT = 'game/';
  private static PLAYER_SUB_ENDPOINT = '/player/';
  private static PIT_SUB_ENDPOINT = '/pit/';

  constructor(private httpClient: HttpClient) {
  }

  public createGame(): Observable<Game> {
    const url = BaseService.getUrl(GameService.GAME_ENDPOINT);
    return this.httpClient.post<Game>(url, undefined, {});
  }

  public takeTurn(gameId, playerId, pitId): Observable<Game> {
    const url = BaseService.getUrl(
      `${GameService.GAME_ENDPOINT}${gameId}${GameService.PLAYER_SUB_ENDPOINT}${playerId}${GameService.PIT_SUB_ENDPOINT}${pitId}`
    );
    return this.httpClient.put<Game>(url, undefined, {});
  }
}
