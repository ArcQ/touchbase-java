import { combineReducers } from 'redux';
import userReducer from './userReducer';
import selectedBaseReducer from './selectedBaseReducer';
import { reducer as formReducer } from 'redux-form';

export default combineReducers({
  user: userReducer,
  selectedBase: selectedBaseReducer,
  form: formReducer,
});
