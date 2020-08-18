import { delay, spawn, all } from 'redux-saga/effects';
import { call } from 'ramda';

// ---plop_append_import---
import threadSaga from './thread/sagas';
import chatListSaga from './chatList/sagas';
// import appSaga from './app/sagas';
// import profileSaga from './profile/sagas';

const defaultSagas = [
  // authSaga,
  // ---plop_append_saga---
  threadSaga,
  chatListSaga,
  // appSaga,
  // pushTokenSaga,
  // creditConsentSaga,
  // profileSaga,
];

export default function* rootSaga() {
  // TODO, only fail if n failures within a certain timespan
  let i = 0;
  yield all(
    defaultSagas.map((saga) =>
      spawn(function* () {
        while (i < 5) {
          try {
            yield call(saga);
            break;
          } catch (e) {
            console.error(e);
            i += 1;
          }
          yield delay(200);
        }
      }),
    ),
  );
}
