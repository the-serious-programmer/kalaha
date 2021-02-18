import {environment} from '../../environments/environment';

export class BaseService {
  constructor() {
  }

  static getUrl(url: string): string {
    return `${environment.ENDPOINT}` + url;
  }
}
