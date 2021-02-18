import {GameService} from './game.service';
import {HttpClient} from '@angular/common/http';
import {of} from 'rxjs';
import {Game} from '../../model/game';

describe('GameService', () => {
  let service: GameService;
  const game = {} as Game;
  const httpClient = ({
    post<T>(url, body, options) {
      return of(game);
    },
    put<T>(url, body, options) {
      return of(game);
    }
  } as unknown) as HttpClient;

  beforeEach(() => {
    service = new GameService(httpClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call create game', () => {
    service.createGame().subscribe((result) => {
      expect(result).toEqual(game);
    });
  });

  it('should call take turn', () => {
    service.takeTurn('', '', '').subscribe((result) => {
      expect(result).toEqual(game);
    });
  });
});
