import { combineReducers } from 'redux';
import userReducer from './userReducer';
import selectedBaseReducer from './selectedBaseReducer';

export default combineReducers({
  user: userReducer,
  selectedBase: selectedBaseReducer,
});
