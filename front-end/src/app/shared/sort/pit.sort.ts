import {PlayerType} from '../../model/type/player.type';
import {PitType} from '../../model/type/pit.type';
import {Player} from '../../model/player';
import {Pit} from '../../model/pit';
import {BaseSort} from './BaseSort';

export class PitSort {
    public static sortPits(player: Player, pits: Pit[]) {
        if (player.type === PlayerType.FIRST) {
            pits.sort(PitSort.sortByTypeAndPositionReverse);
        } else {
            pits.sort(PitSort.sortByPosition);
        }
    }

    private static sortByPosition(a: Pit, b: Pit): number {
        return a.position - b.position;
    }

    private static sortByTypeAndPositionReverse(a: Pit, b: Pit): number {
        if (a.type === b.type) {
            return b.position - a.position;
        } else if (a.type === PitType.REGULAR) {
            return BaseSort.GREATER_THAN;
        }
        return BaseSort.LESS_THAN;
    }
}
