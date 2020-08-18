import { assoc } from 'ramda';

import {
  createConstantsAndActions,
  createSelectorsAndState,
} from '../../utils/reduxHelpers';

export const errorNamespace = 'error';

const constArr = ['SET_ERROR', 'SET_SUCCESS_MESSAGE', 'CLEAR_ERROR'];

export const {
  constants: errorConstants,
  actions: errorActions,
} = createConstantsAndActions(errorNamespace, constArr);

const { initialState, selectors } = createSelectorsAndState(errorNamespace, {
  error: { k: undefined },
});

export const errorSelectors = {
  ...selectors,
};

const c = errorConstants;

const mapError = (payload) => {
  switch (true) {
    // error => { k: str, val: str }
    case payload?.status === 409:
      return {
        k: 'email_already_taken',
      };
    case payload?.error === 'not_signed_in':
      return {
        k: 'not_signed_in',
      };
    case payload.error === 'generic_error_00':
      return {
        k: 'generic_error_00',
      };
    case !!payload?.message:
      return {
        k: 'api_error',
        message: payload.message,
      };
    default:
      return {
        k: 'generic_error_00',
      };
  }
};

export default function errorReducer(state = initialState, action) {
  switch (action.type) {
    // TODO should turn this into alert ducks
    case c.SET_SUCCESS_MESSAGE: {
      return assoc('error', { k: 'success', v: action.payload.value }, state);
    }
    case c.SET_ERROR: {
      return assoc('error', mapError(action.payload), state);
    }
    case c.CLEAR_ERROR: {
      return initialState;
    }
    default:
      return state;
  }
}
