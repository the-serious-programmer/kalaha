import {Player} from './player';
import {GameStatus} from './type/game-status.type';

export interface Game {
  id: string;
  status: GameStatus;
  players: Player[];
}
