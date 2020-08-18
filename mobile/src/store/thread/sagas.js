import { Auth } from 'aws-amplify';
import { put, take, takeEvery, call, all } from 'redux-saga/effects';
import { eventChannel as EventChannel } from 'redux-saga';
import {
  BROADCAST_ACTION,
  createChannel,
  sendMessageAsync,
} from '@knotfive/chatpi-client-js/src/chatpi-client';

import envService from '../../services/env/envService';
import { threadActions, threadConstants } from './ducks';

// TODO push tokens
//
function* subscribeMessageUpdates(messagesChannel) {
  while (true) {
    const { ok, message, reason } = yield take(messagesChannel);
    if (!ok) {
      console.warn(reason); //eslint-disable-line
    }

    yield put(threadActions.receiveMessage({ message }));
  }
}

function* watchForSendMessage(channel, action) {
  try {
    yield sendMessageAsync({
      channel,
      action: BROADCAST_ACTION,
      message: {
        text: action.payload[0].text,
      },
    });
  } catch (e) {
    console.warn(e); //eslint-disable-line
  }
}

// function* syncThread() {
// }

function* watchForChannelClose(channel) {
  yield take([threadConstants.CLOSE]);
  channel.close();
}

function* startChannel() {
  const { accessToken } = yield Auth.currentSession();

  const userToken = 10;

  const channel = yield createChannel({
    channelId: 'f902b5f2-f458-47c2-a7f6-1b00d41998a6',
    url: envService.getConfig().chatApiUrl,
    userToken,
    authorizationToken: accessToken.jwtToken,
  });

  const messagesChannel = new EventChannel((emitter) => {
    channel.on(BROADCAST_ACTION, (message) => emitter({ ok: true, message })); //eslint-disable-line

    channel
      .join()
      .receive('ok', (message) => emitter({ ok: true, message }))
      .receive('error', ({ reason }) => emitter({ ok: false, reason }))
      .receive(
        'timeout',
        () => console.log('Networking issue. Still waiting...'), //eslint-disable-line
      );

    return () => channel.leave();
  });

  yield all([
    call(subscribeMessageUpdates, messagesChannel),
    takeEvery(threadConstants.SEND_MESSAGE, watchForSendMessage, channel),
    takeEvery(threadConstants.CLOSE, watchForChannelClose, channel),
  ]);
}

export default function* threadSaga() {
  yield startChannel();
}
