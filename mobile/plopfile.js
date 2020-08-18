module.exports = function(plop) {
  plop.setGenerator('component', {
    description: 'component',
    prompts: [
      {
        type: 'input',
        name: 'name',
        message: 'component name',
      },
    ],
    actions: [
      {
        type: 'add',
        path: 'src/components/{{name}}.jsx',
        templateFile: 'plop-templates/component.hbs',
      },
      {
        type: 'add',
        path: 'src/components/__stories__/{{name}}.stories.jsx',
        templateFile: 'plop-templates/stories.hbs',
      },
    ],
  });

  plop.setGenerator('screen', {
    description: 'screen',
    prompts: [
      {
        type: 'input',
        name: 'name',
        message: 'component name',
      },
    ],
    actions: [
      {
        type: 'add',
        path: 'src/screens/{{name}}/{{name}}.jsx',
        templateFile: 'plop-templates/screen.hbs',
      },
      {
        type: 'add',
        path: 'src/screens/{{name}}/{{name}}Container.jsx',
        templateFile: 'plop-templates/screencontainer.hbs',
      },
    ],
  });

  plop.setGenerator('reducer', {
    description: 'reducer',
    prompts: [
      {
        type: 'input',
        name: 'name',
        message: 'reducer name',
      },
    ],
    actions: [
      {
        type: 'add',
        path: 'src/store/{{name}}/ducks.js',
        templateFile: 'plop-templates/ducks.hbs',
      },
      {
        type: 'add',
        path: 'src/store/{{name}}/sagas.js',
        templateFile: 'plop-templates/sagas.hbs',
      },
      {
        type: 'append',
        path: 'src/store/sagas.js',
        pattern: /\/\/ ---plop_append_saga---/gi,
        template: '  {{name}}Saga,',
      },
      {
        type: 'append',
        path: 'src/store/sagas.js',
        pattern: /\/\/ ---plop_append_import---/gi,
        template: "import {{name}}Saga from './{{name}}/sagas';",
      },
      {
        type: 'append',
        path: 'src/store/reducers.js',
        pattern: /\/\/ ---plop_append_reducer---/gi,
        template: '    [{{name}}Namespace]: {{name}}Reducer,',
      },
      {
        type: 'append',
        path: 'src/store/reducers.js',
        pattern: /\/\/ ---plop_append_import---/gi,
        template:
          "import {{name}}Reducer, { {{name}}Namespace } from './{{name}}/ducks';",
      },
    ],
  });
};
