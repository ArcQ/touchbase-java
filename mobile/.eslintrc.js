const path = require('path');

module.exports = {
  extends: ['airbnb', 'airbnb/hooks'],
  parser: 'babel-eslint',
  parserOptions: {
    ecmaVersion: 11,
    sourceType: 'module',
    ecmaFeatures: {
      jsx: true,
    },
  },
  plugins: ['jest'],
  env: {
    jest: true,
  },
  globals: {},
  settings: {
    // 'import/resolver': {
    //   node: {
    //     extensions: ['', '.js', '.jsx'],
    //     paths: [path.join(__dirname, './src')],
    //   },
    // },
  },
  rules: {
    'react/jsx-filename-extension': 0,
    'react/jsx-wrap-multilines': 0,
    'react/jsx-closing-tag-location': 0,
    'react/jsx-props-no-spreading': 0,
    'max-len': [1, 80, 2],
    'import/no-named-as-default': 0,
    'no-param-reassign': ['error', { props: false }],
    'no-mixed-operators': 1,
    'no-underscore-dangle': 0,
    'import/no-unresolved': 2,
    'func-names': 0,
    // temporary since webpack-resolver not working with aliases in webpack2
    'import/no-extraneous-dependencies': 0,
    'class-methods-use-this': 0,
    'react/require-default-props': 0,
    'array-callback-return': 0,
    'react/destructuring-assignment': 0,
    'space-before-function-paren': 0,
    // prettier
    'function-paren-newline': 0,
    'arrow-parens': 0,
    'import/prefer-default-export': 0,
    'implicit-arrow-linebreak': 0,
    'object-curly-newline': 0,
    // use prettier for these
    'max-len': 0,
    'function-paren-newline': 0,
    'no-confusing-arrow': 0,
    'generator-star-spacing': 0,
    'operator-linebreak': 0,
  },
};
