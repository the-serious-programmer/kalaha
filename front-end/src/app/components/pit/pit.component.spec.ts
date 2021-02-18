import {PitComponent} from './pit.component';
import {Pit} from '../../model/pit';
import {PitType} from '../../model/type/pit.type';

describe('PitComponent', () => {
  let component: PitComponent;

  beforeEach(() => {
    component = new PitComponent();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should determine pit type', () => {
    component.pit = {type: PitType.REGULAR} as Pit;
    expect(component.isRegular()).toBeTrue();
    expect(component.isScore()).toBeFalse();

    component.pit = {type: PitType.SCORE} as Pit;
    expect(component.isRegular()).toBeFalse();
    expect(component.isScore()).toBeTrue();
  });

  it('should determine if pit is empty', () => {
    component.pit = {stones: 1} as Pit;
    expect(component.isEmpty()).toBeFalse();

    component.pit = {stones: 0} as Pit;
    expect(component.isEmpty()).toBeTrue();
  });
});
