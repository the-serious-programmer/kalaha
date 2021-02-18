import {Pit} from './pit';
import {PlayerType} from './type/player.type';

export interface Player {
  id: string;
  type: PlayerType;
  turn: boolean;
  pits: Pit[];
}
