import { combineReducers } from 'redux';
import basesReducer from './basesReducer';
import selectedBaseReducer from './selectedBaseReducer';

export default combineReducers({
  bases: basesReducer,
  selectedBase: selectedBaseReducer,
});
