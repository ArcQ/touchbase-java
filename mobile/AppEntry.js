import { registerRootComponent } from 'expo';

import envService from './src/services/env/envService';
import App from './src/App';

envService.init();

if (envService.getConfig().storybook) {
  // eslint-disable-next-line global-require
  const StoryBook = require('./storybook').default;
  registerRootComponent(StoryBook);
} else {
  registerRootComponent(App);
}
