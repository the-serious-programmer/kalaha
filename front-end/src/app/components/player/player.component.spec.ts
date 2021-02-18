import {PlayerComponent} from './player.component';
import {Player} from '../../model/player';
import {PitSort} from '../../shared/sort/pit.sort';
import {Pit} from '../../model/pit';
import {throwError} from 'rxjs';
import {PlayerType} from '../../model/type/player.type';

describe('PlayerComponent', () => {
  let component: PlayerComponent;

  beforeEach(() => {
    component = new PlayerComponent();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should sort pits on init', () => {
    const sortSpy = spyOn(PitSort, 'sortPits');
    component.player = {pits: []} as Player;
    component.ngOnInit();
    expect(sortSpy).toHaveBeenCalledWith(component.player, component.player.pits);
  });

  it('should emit take turn on receiving emit from pit', () => {
    const pitId = '1';
    component.player = {id: '2'} as Player;
    component.takeTurn.subscribe(
      (result) => {
        expect(result.playerId).toEqual(component.player.id);
        expect(result.pitId).toEqual(pitId);
      },
      () => throwError('should not fail on emitting player take turn event')
    );
    component.onTakeTurn({id: pitId} as Pit);
  });

  it('should determine who is first player', () => {
    component.player = {type: PlayerType.FIRST} as Player;
    expect(component.isFirstPlayer()).toBeTrue();
    component.player = {type: PlayerType.SECOND} as Player;
    expect(component.isFirstPlayer()).toBeFalse();
  });
});
