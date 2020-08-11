import touchbase from '../apis/touchbase';
import { FETCH_USER } from './types';

export const fetchUser = () => async (dispatch) => {
  const instance = await touchbase.getTouchbaseInstance();
  const response = await instance.get('/api/v1/person/me');

  dispatch({
    type: FETCH_USER,
    payload: response.data,
  });
};
