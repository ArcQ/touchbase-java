import { put, fork } from 'redux-saga/effects';

import apiService, { apiCall } from '../../services/api/apiService';
import { watchActionsLatest } from '../../utils/watchActions';
import { chatListConstants, chatListActions } from './ducks';

/**
 * Get all chats to display
 */
function* getChats() {
  yield apiCall(
    {
      call: apiService.chat.get,
      *onSuccess(response) {
        yield put(
          chatListActions.receiveChats({
            chats: response.chats,
          }),
        );
      },
    },
    '/v1/chats',
  );
}

export default function* chatListSaga() {
  yield fork(watchActionsLatest, [[chatListConstants.GET_CHATS, getChats]]);
}
