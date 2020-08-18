import { merge, pathOr, pipe } from 'ramda';
import { schema, normalize } from 'normalizr';

export const baseObj = new schema.Entity('dbObject', {}, { idAttribute: 'id' });

export const normalizeBase = (payload) => normalize(payload, [baseObj]);

const updateState = (state, prefix = '') => (normalized) => ({
  ...state,
  [`${prefix}Entities`]: merge(
    pathOr({}, [`${prefix}Entities`], state),
    normalized.entities.dbObject,
  ),
  [`${prefix}List`]: pathOr([], [`${prefix}List`], state).concat(
    normalized.result,
  ),
});

export const normalizeAndUpdate = (state, prefix) =>
  pipe(normalizeBase, updateState(state, prefix));
