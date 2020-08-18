import produce from 'immer';

import {
  createConstantsAndActions,
  createSelectorsAndState,
} from '../../utils/reduxHelpers';
import { images } from '../../constants';

export const threadNamespace = 'thread';

const constArr = [
  'INITIALIZE',
  'SEND_MESSAGE',
  'SEND_MESSAGE_SUCCESS',
  'RECEIVE_MESSAGE',
  'SEND_MESSAGE_ERROR',
  'CLOSE',

  // TEMPORARY
  'CLEAR_MESSAGES',
];

export const {
  constants: threadConstants,
  actions: threadActions,
} = createConstantsAndActions(threadNamespace, constArr);

// TEMPORARY
const initialMessages = [
  {
    _id: 1,
    text: 'Hello developer!',
    createdAt: new Date(),

    user: {
      _id: 1,
      avatar: images.profile,
      name: 'Caleb Nance',
    },
  },
];

const { initialState, selectors } = createSelectorsAndState(threadNamespace, {
  isSending: false,
  recipient: {},
  messages: initialMessages,
  // messageList: initialMessages.result,
  // messageEntities: initialMessages.entities.dbObject,
  // messageList: [],
  // messageEntities: {},
});

export const threadSelectors = {
  ...selectors,
};

const c = threadConstants;

// Messages are sent by default as short, and no progress bar, if an error returns or
// TODO A large message like an attachment needs to show progress + status
const threadReducer = produce((state = initialState, action) => {
  switch (action.type) {
    case c.SEND_MESSAGE:
      state.isSending = true;
      return state;
    case c.SEND_MESSAGE_SUCCESS:
      state.isSending = false;
      return state;
    case c.CLEAR_MESSAGES:
      return initialState;
    case c.SEND_MESSAGE_ERROR:
      state.isSending = false;
      return state;
    case c.RECEIVE_MESSAGE: {
      const messageRes = action.payload.message;

      if (!messageRes) return state;

      const message = {
        _id: messageRes?.id,
        text: messageRes?.text,
        createdAt: messageRes?.inserted_at,
        user: {
          _id: messageRes?.user_id,
          name: messageRes?.user_id,
        },
      };

      state.messages.push(message);
      return state;
    }
    default:
      return state;
  }
});

export default threadReducer;
