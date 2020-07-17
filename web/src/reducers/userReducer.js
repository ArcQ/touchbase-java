import { FETCH_USER } from '../actions/types';

export default (user = null, action) => {
  switch (action.type) {
    case FETCH_USER:
      return action.payload;
    default:
      return user;
  }
};
