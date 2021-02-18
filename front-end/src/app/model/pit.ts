import {PitType} from './type/pit.type';

export interface Pit {
  id: string;
  position: number;
  type: PitType;
  stones: number;
}
