/** @module */
/**
 * formQuery - transforms an object into a query for http requests
 *
 * @param {Object} _query
 * @returns {string}
 */
export function formQuery(_query) {
  const query = Object.entries(_query)
    .map(([k, v]) => `${encodeURIComponent(k)}=${encodeURIComponent(v)}`)
    .join('&');
  return `?${query}`;
}
