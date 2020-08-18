/** @module * */
import { fork, race, delay, put, join } from 'redux-saga/effects';

import { appActions } from '../store/app/ducks';

/**
 * wrapInShowProgressBar - wrap around generators insde of sagas to start main progress
 * bar at top of page, once generator starts, and completes progress once generator ends
 *
 * @param generator
 * @returns {undefined}
 */
export default function wrapInShowProgressBar(k, generator) {
  return function* wrappedGenerator(...args) {
    yield put(appActions.setLoading({ [k]: true }));
    const task = yield fork(generator, ...args);
    let { res, timeout } = yield race({
      res: join(task),
      // if takes too long, cancel call
      timeout: delay(5000),
    });
    if (timeout) {
      res = join(task);
    }
    yield put(appActions.setLoading({ [k]: false }));
    return res;
  };
}
