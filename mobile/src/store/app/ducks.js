import { path } from 'ramda';

import {
  createConstantsAndActions,
  createSelectorsAndState,
} from '../../utils/reduxHelpers';

export const appNamespace = 'app';

const constArr = ['SET_EXAMPLE'];

export const {
  constants: appConstants,
  actions: appActions,
} = createConstantsAndActions(appNamespace, constArr);

const { initialState, selectors } = createSelectorsAndState(appNamespace, {
  example: false,
});

export const appSelectors = {
  ...selectors,
  example: path([appNamespace, 'example']),
};

const c = appConstants;

export default function appReducer(state = initialState, action) {
  switch (action.type) {
    case c.SET_EXAMPLE: {
      return { ...state };
    }
    default:
      return state;
  }
}
