export const snakeToCamel = str =>
  str.replace(/([-_][a-z])/g, group =>
    group
      .toUpperCase()
      .replace('-', '')
      .replace('_', ''),
  );

export function camelToSnake(s) {
  return s
    .split(/(?=[A-Z])/)
    .join('_')
    .toLowerCase();
}
