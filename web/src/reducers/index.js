import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';

import userReducer from './userReducer';
import selectedBaseReducer from './selectedBaseReducer';
import chatReducer from './chatReducer';

export default combineReducers({
  user: userReducer,
  selectedBase: selectedBaseReducer,
  form: formReducer,
  chat: chatReducer,
});
