import {PlayerType} from '../../model/type/player.type';
import {Player} from '../../model/player';
import {PlayerSort} from './player.sort';

describe('PlayerSortTest', () => {
    it('should sort players by type', () => {
        const first = {type: PlayerType.FIRST} as Player;
        const second = {type: PlayerType.SECOND} as Player;
        const input = [second, first];
        const expectedResult = [first, second];

        expect(input.sort(PlayerSort.sortPlayersByType)).toEqual(expectedResult);
    });

    it('should sort players based on incoming order when types are the same', () => {
        const first = ({type: PlayerType.FIRST, id: 1} as unknown) as Player;
        const second = ({type: PlayerType.FIRST, id: 2} as unknown) as Player;
        const input = [second, first];
        const expectedResult = [second, first];

        expect(input.sort(PlayerSort.sortPlayersByType)).toEqual(expectedResult);
    });
});
