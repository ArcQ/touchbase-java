import { ADD_MESSAGE, MESSAGE_RECEIVED, ADD_USER, USERS_LIST } from './types';

let nextMessageId = 0;
let nextUserId = 0;

export const addMessage = (message, author) => {
  return {
    type: ADD_MESSAGE,
    id: nextMessageId++,
    message,
    author,
  };
};

export const addUser = (name) => {
  return {
    type: ADD_USER,
    id: nextUserId++,
    name,
  };
};

export const messageReceived = (message, author) => {
  return {
    type: MESSAGE_RECEIVED,
    id: nextMessageId++,
    message,
    author,
  };
};

export const populateUsersList = (users) => {
  return {
    type: USERS_LIST,
    users,
  };
};
