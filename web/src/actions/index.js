import touchbase from '../apis/touchbase';
import { BASE_SELECTED, FETCH_BASES } from './types';

export const selectBase = (base) => {
  return {
    type: BASE_SELECTED,
    payload: base,
  };
};

export const fetchBases = () => async (dispatch) => {
  const bases = await touchbase.get('/bases');

  dispatch({
    type: FETCH_BASES,
    payload: bases.data,
  });
};
