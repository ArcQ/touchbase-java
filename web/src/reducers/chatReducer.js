import {
  ADD_MESSAGE,
  MESSAGE_RECEIVED,
  ADD_USER,
  USERS_LIST,
} from '../actions/types';

export default (state = [], action) => {
  switch (action.type) {
    case ADD_MESSAGE:
    case MESSAGE_RECEIVED:
      return [
        ...state,
        { message: action.message, author: action.author, id: action.id },
      ];
    case ADD_USER:
      return [...state, { name: action.name, id: action.id }];
    case USERS_LIST:
      return action.users;
    default:
      return state;
  }
};
