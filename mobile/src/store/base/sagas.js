import { put, fork } from 'redux-saga/effects';

import apiService from '../../services/api/apiService';
import { watchActionsLatest } from '../../utils/watchActions';
import { baseActions, baseConstants } from './ducks';

function* requestMyBases() {
  yield apiService.core.get('v1/base', function* onSuccess(res) {
    yield put(
      baseActions.receiveMyBases({
        bases: res.bases,
      }),
    );
  });
}

export default function* baseSaga() {
  yield fork(watchActionsLatest, [
    [baseConstants.REQUEST_MY_BASES, requestMyBases],
  ]);
}
