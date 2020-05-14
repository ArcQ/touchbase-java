import { BASE_SELECTED } from '../actions/types';

export default (selectedBase = null, action) => {
  switch (action.type) {
    case BASE_SELECTED:
      return action.payload;
    default:
      return selectedBase;
  }
};
