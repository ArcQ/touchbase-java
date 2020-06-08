import touchbase from '../apis/touchbase';
import { BASE_SELECTED, FETCH_USER_BASES } from './types';

export const selectBase = (base) => {
  return {
    type: BASE_SELECTED,
    payload: base,
  };
};

export const fetchUserBases = () => async (dispatch) => {
  const instance = await touchbase.getTouchbaseInstance();
  const response = await instance.get('/api/v1/person/me');

  dispatch({
    type: FETCH_USER_BASES,
    payload: response.data,
  });
};
