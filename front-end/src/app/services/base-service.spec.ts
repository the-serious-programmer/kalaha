import {environment} from '../../environments/environment';
import {BaseService} from './base-service';

describe('BaseService', () => {
  it('should create', () => {
    const baseService = new BaseService();
    expect(baseService).toBeTruthy();
  });

  it('should get URL with environment endpoint', () => {
    const baseEndpoint = environment.ENDPOINT;
    const url = '/game';
    const expectedResult = baseEndpoint + url;
    expect(BaseService.getUrl(url)).toEqual(expectedResult);
  });
});
