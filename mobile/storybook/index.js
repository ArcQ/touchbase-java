import { configure, getStorybookUI } from '@storybook/react-native';
import { func } from '../src/constants/index';
import { loadStories } from './storyLoader';

// TODO this is not completely safe, but acceptable for now since
// it should be much faster than actual stories loading
func.loadAssetsAsync().then(() => {});

configure(() => {
  loadStories();
}, module);

const StorybookUIRoot = getStorybookUI({});

export default StorybookUIRoot;
