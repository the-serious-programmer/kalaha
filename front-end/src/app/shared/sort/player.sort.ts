import {Player} from '../../model/player';
import {PlayerType} from '../../model/type/player.type';
import {BaseSort} from './BaseSort';

export class PlayerSort {
    public static sortPlayersByType(a: Player, b: Player) {
        if (a.type === b.type) {
            return BaseSort.SAME;
        } else if (a.type === PlayerType.FIRST) {
            return BaseSort.LESS_THAN;
        }
        return BaseSort.GREATER_THAN;
    }
}
