import {BoardComponent} from './board.component';
import {GameService} from '../../services/game/game.service';
import {Observable, of, throwError} from 'rxjs';
import {Game} from '../../model/game';
import {GameStatus} from '../../model/type/game-status.type';
import {PlayerType} from '../../model/type/player.type';
import {Player} from '../../model/player';

describe('BoardComponent', () => {
  const createGameErrorKey = 'GAME_CREATE_ERROR';
  const takeTurnErrorKey = 'TAKE_TURN_ERROR';
  const takeTurn = 'takeTurn';
  const createGame = 'createGame';
  const first = {type: PlayerType.FIRST} as Player;
  const second = {type: PlayerType.SECOND} as Player;
  const game = {players: [second, first]} as Game;
  const event = {pitId: game.id, playerId: game.id};
  let component: BoardComponent;

  const gameService = {
    createGame(): Observable<Game> {
      return of(game);
    },
    takeTurn(gameId, playerId, pitId): Observable<Game> {
      return of(game);
    }
  } as GameService;

  beforeEach(() => {
    component = new BoardComponent(gameService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create game on init and empty error keys', () => {
    component.errorKeys = [createGameErrorKey];
    const createGameSpy = spyOn(gameService, createGame).and.callThrough();

    component.ngOnInit();

    expect(component.errorKeys).toEqual([]);
    expect(component.game).toEqual(game);
    expect(createGameSpy).toHaveBeenCalled();
  });

  it('should sort player on create game', () => {
    const expectedResult = {players: [first, second]} as Game;
    const createGameSpy = spyOn(gameService, createGame).and.returnValue(of(game));

    component.ngOnInit();

    expect(component.game).toEqual(expectedResult);
    expect(createGameSpy).toHaveBeenCalled();
  });

  it('should handle create game error', () => {
    const createGameSpy = spyOn(gameService, createGame).and.returnValue(throwError('error'));

    component.ngOnInit();

    expect(component.errorKeys).toEqual([createGameErrorKey]);
    expect(component.game).not.toEqual(game);
    expect(createGameSpy).toHaveBeenCalled();
  });

  it('should take turn and empty error keys', () => {
    component.errorKeys = [createGameErrorKey];
    const expectedGameId = '1';
    const expectedPlayerId = '3';
    const expectedPitId = '2';
    component.game = ({id: expectedGameId} as unknown) as Game;
    const takeTurnSpy = spyOn(gameService, takeTurn).and.callThrough();

    component.onTakeTurn({pitId: expectedPitId, playerId: expectedPlayerId});

    expect(component.errorKeys).toEqual([]);
    expect(takeTurnSpy).toHaveBeenCalledWith(expectedGameId, expectedPlayerId, expectedPitId);
    expect(component.game).toEqual(game);
  });

  it('should handle take turn error', () => {
    component.game = {} as Game;
    const takeTurnSpy = spyOn(gameService, takeTurn).and.returnValue(throwError('error'));

    component.onTakeTurn(event);

    expect(component.errorKeys).toEqual([takeTurnErrorKey]);
    expect(takeTurnSpy).toHaveBeenCalled();
    expect(component.game).not.toEqual(game);
  });

  it('should sort player on take turn', () => {
    const expectedResult = {players: [first, second]} as Game;
    const takeTurnSpy = spyOn(gameService, takeTurn).and.returnValue(of(game));
    component.game = game;
    component.onTakeTurn(event);

    expect(component.game).toEqual(expectedResult);
    expect(takeTurnSpy).toHaveBeenCalled();
  });

  it('should determine game end', () => {
    component.game = ({status: GameStatus.PLAYING} as unknown) as Game;
    expect(component.isGameEnd()).toBeFalse();
    component.game = ({status: GameStatus.END} as unknown) as Game;
    expect(component.isGameEnd()).toBeTrue();
  });
});
