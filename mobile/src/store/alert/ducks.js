import { path } from 'ramda';
import produce from 'immer';

import {
  createConstantsAndActions,
  createSelectorsAndState,
} from '../../utils/reduxHelpers';

export const alertNamespace = 'alerts';

const constArr = ['TOGGLE_SUCCESS_ALERT'];

export const {
  constants: alertConstants,
  actions: alertsActions,
} = createConstantsAndActions(alertNamespace, constArr);

const { initialState, selectors } = createSelectorsAndState(alertNamespace, {
  toast: {},
});

export const alertSelectors = {
  ...selectors,
  toast: path([alertNamespace, 'toast']),
};

const c = alertConstants;

const alertReducer = produce((state = initialState, action) => {
  switch (action.type) {
    case c.TOGGLE_SUCCESS_ALERT: {
      state.toast = {
        type: 'success',
        ...action.payload,
      };
      return state;
    }
    default:
      return state;
  }
});

export default alertReducer;
