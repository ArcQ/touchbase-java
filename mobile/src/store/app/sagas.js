import { put, fork } from 'redux-saga/effects';

import apiService from '../../services/api/apiService';
import { watchActionsLatest } from '../../utils/watchActions';
import { appActions, appConstants } from './ducks';

function* requestApp() {
  yield apiService.get('api/example', [
    function* onSuccess(res) {
      yield put(
        appActions.receiveApp({
          app: res.app,
        }),
      );
    },
  ]);
}

export default function* appSaga() {
  yield fork(watchActionsLatest, [[appConstants.SET_EXAMPLE, requestApp]]);
}
