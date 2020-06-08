import touchbase from '../apis/touchbase';
import { BASE_SELECTED, FETCH_BASES } from './types';

export const selectBase = (base) => {
  return {
    type: BASE_SELECTED,
    payload: base,
  };
};

export const fetchBases = () => async (dispatch) => {
  const instance = await touchbase.getTouchbaseInstance();
  const response = await instance.get('/base');

  dispatch({
    type: FETCH_BASES,
    payload: response.data,
  });
};
