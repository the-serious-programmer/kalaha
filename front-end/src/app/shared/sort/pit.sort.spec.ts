import {PlayerType} from '../../model/type/player.type';
import {PitType} from '../../model/type/pit.type';
import {PitSort} from './pit.sort';
import {Player} from '../../model/player';
import {Pit} from '../../model/pit';

describe('PitSortTest', () => {
    const regularZero = {
        position: 0,
        type: PitType.REGULAR
    };
    const regularOne = {
        position: 1,
        type: PitType.REGULAR
    };
    const scoreZero = {
        position: 0,
        type: PitType.SCORE
    };
    const scoreOne = {
        position: 1,
        type: PitType.SCORE
    };

    it('should sort based on type and position reversed when player has type FIRST', () => {
        const player = {type: PlayerType.FIRST} as Player;
        const expectedResult = [scoreOne, scoreZero, regularOne, regularZero] as Pit[];
        pitSortTest(player, expectedResult);
    });

    it('should sort based on position and incoming order when player has type SECOND', () => {
        const player = {type: PlayerType.SECOND} as Player;
        const expectedResult = [scoreZero, regularZero, regularOne, scoreOne] as Pit[];
        pitSortTest(player, expectedResult);
    });

    const pitSortTest = (player, expectedResult) => {
        const input = [regularOne, scoreZero, scoreOne, regularZero] as Pit[];
        PitSort.sortPits(player, input);
        expect(input).toEqual(expectedResult);
    };
});
