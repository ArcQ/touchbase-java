import produce from 'immer';

import {
  createConstantsAndActions,
  createSelectorsAndState,
} from '../../utils/reduxHelpers';
import { normalizeBase } from '../../utils/schema';

export const chatListNamespace = 'chatList';

const constArr = ['MARK_SEEN', 'RECEIVE_CHATS', 'GET_CHATS'];

export const {
  constants: chatListConstants,
  actions: chatListActions,
} = createConstantsAndActions(chatListNamespace, constArr);

export const {
  initialState,
  selectors: chatListSelectors,
} = createSelectorsAndState(chatListNamespace, {
  chatList: [],
  chatEntities: {},
  hasLoaded: false,
});

const c = chatListConstants;

const chatListReducer = produce((state = initialState, action) => {
  switch (action.type) {
    case c.RECEIVE_CHATS: {
      const dbObj = normalizeBase(action.payload.chats);
      state.chatEntities = dbObj.entities;
      state.chatList = dbObj.result;
      state.hasLoaded = true;
      return state;
    }
    case c.MARK_SEEN:
      state.chatEntities[action.payload.id].isSeen = true;
      return state;
    default:
      return state;
  }
});

export default chatListReducer;
