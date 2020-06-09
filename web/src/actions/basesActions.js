import { BASE_SELECTED } from './types';

export const selectBase = (base) => {
  return {
    type: BASE_SELECTED,
    payload: base,
  };
};
