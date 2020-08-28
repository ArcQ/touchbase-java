import produce from 'immer';

import { normalizeBase } from '../../utils/schema';
import {
  createConstantsAndActions,
  createSelectorsAndState,
} from '../../utils/reduxHelpers';

export const baseNamespace = 'base';

const constArr = ['REQUEST_MY_BASES', 'RECEIVE_MY_BASES'];

export const {
  constants: baseConstants,
  actions: baseActions,
} = createConstantsAndActions(baseNamespace, constArr);

const { initialState, selectors } = createSelectorsAndState(baseNamespace, {
  myBaseEntities: {},
  myBaseList: {},
  hasLoaded: false,
});

export const baseSelectors = {
  ...selectors,
};

const c = baseConstants;

const baseReducer = produce((state = initialState, action) => {
  switch (action.type) {
    case c.RECEIVE_MY_BASES: {
      const dbObj = normalizeBase(action.payload.bases);
      state.baseEntities = dbObj.entities;
      state.baseList = dbObj.result;
      state.hasLoaded = true;
      return state;
    }
    default:
      return state;
  }
});

export default baseReducer;
