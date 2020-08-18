// object pool for users so you don't have to make a call everytime
import produce from 'immer';

import {
  createConstantsAndActions,
  createSelectorsAndState,
} from '../../utils/reduxHelpers';

export const cachedProfilesNamespace = 'cachedProfiles';

const constArr = [
  'ADD_PROFILE',
  'UPDATE_IN_PROFILE',
  'REMOVE_PROFILE',
  'UPDATE_PROFILE',
  'TOGGLE_BLOCK_USER',
  'CLEAN_EXPIRED',
];

/**
 * Constants and Actions
 */

export const {
  constants: cachedProfilesConstants,
  actions: cachedProfilesActions,
} = createConstantsAndActions(cachedProfilesNamespace, constArr);

/**
 * Selectors
 */

const { initialState, selectors } = createSelectorsAndState(
  cachedProfilesNamespace,
  {
    myProfile: {},
    cachedProfilesList: [],
    cachedProfilesEntities: {},
  },
);

export const cachedProfilesSelectors = {
  ...selectors,
};

/**
 * Reducer
 */

const maxUsers = 20;

// 48h
const expiresIn = 2 * 86400000;

const c = cachedProfilesConstants;

const cachedProfilesReducer = produce((state = initialState, action) => {
  switch (action.type) {
    case c.CLEAN_EXPIRED:
      return state.filter((v) => v.expires > Math.round(new Date().getTime()));
    case c.UPDATE_IN_PROFILE:
      Object.entries(action.payload.values).forEach(([key, value]) => {
        state.myProfile[key] = value;
      });
      return state;
    case c.REMOVE_PROFILE: {
      state.cachedProfilesList.filter(() => action.payload.profile.id);
      delete state.cachedProfilesEntities[action.payload.profile.id];
      return state;
    }
    case c.UPSERT_PROFILE: {
      if (state.cachedProfilesList.length > maxUsers) {
        delete state[state.cachedProfilesList[0]];
        state.shift();
      }
      if (!state.cachedProfilesEntities[action.payload.profile.id]) {
        state.cachedProfilesList.push(action.payload.profile.id);
        state.cachedProfilesEntities[action.payload.profile.id] = {
          ...action.payload.profile,
          expires: Math.round(new Date().getTime()) + expiresIn,
        };
      }
      return state;
    }
    default:
      return state;
  }
});

export default cachedProfilesReducer;
