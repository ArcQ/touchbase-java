module.exports = function (api) {
  api.cache(true);
  return {
    presets: ['babel-preset-expo'],
    plugins: [
      [
        'module-resolver',
        {
          alias: {
            assets: './src/assets',
            components: './src/components',
            constants: './src/constants',
            containers: './src/containers',
            navigation: './src/navigation',
            screens: './src/screens',
            services: './src/services',
            store: './src/store',
            utils: './src/utils',
            App: './src/App',
            'awsconfig.json': './src/awsconfig.json',
            'config.json': './src/config.json',
            'config.production.json': './src/config.production.json',
          },
        },
      ],
      '@babel/plugin-proposal-optional-chaining',
      '@babel/plugin-proposal-nullish-coalescing-operator',
    ],
  };
};
