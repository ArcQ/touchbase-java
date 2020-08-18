import { StatusBar } from 'react-native';
import { createAppContainer, createDrawerNavigator } from 'react-navigation';

import ChatStack from './ChatStack';
import DrawerContent from '../components/DrawerContent';
import { colors, device } from '../constants';

// create drawer navigator
const DrawerNavigator = createDrawerNavigator(
  {
    ChatStack,
  },
  {
    contentComponent: DrawerContent,
    drawerType: 'slide',
    drawerWidth: device.width - 32,
    initialRouteName: 'ChatStack',
    overlayColor: colors.black50,
  },
);

const defaultGetStateForAction = DrawerNavigator.router.getStateForAction;

DrawerNavigator.router.getStateForAction = (action, state) => {
  if (action.type === 'Navigation/MARK_DRAWER_SETTLING') {
    if (action.willShow === false) {
      StatusBar.setBarStyle('dark-content');
    } else if (action.willShow === true) {
      StatusBar.setBarStyle('light-content');
    }
  }

  return defaultGetStateForAction(action, state);
};

const App = createAppContainer(DrawerNavigator);

export default App;
