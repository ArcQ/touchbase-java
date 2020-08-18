/* @module */
import { call, fork, take } from 'redux-saga/effects';

export default function* takeFirst(pattern, saga, ...args) {
  return yield fork(function* loop() {
    while (true) {
      // eslint-disable-line
      const action = yield take(pattern);
      yield call(saga, ...args.concat(action));
    }
  });
}
