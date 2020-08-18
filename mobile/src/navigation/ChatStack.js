import React from 'react';
import { createStackNavigator } from 'react-navigation';

import HeaderLeft from '../components/HeaderLeft';
import HeaderRight from '../components/HeaderRight';
import { gStyle } from '../constants';
import ModalRoutes from './ModalRoutes';
import Chat from '../screens/ChatScreen';
import Notifications from '../screens/NotificationsScreen';

// components

// create stack navigator
const ChatStack = createStackNavigator(
  {
    Chat: {
      screen: Chat,
      navigationOptions: ({ screenProps }) => ({
        headerRight: <HeaderRight screenProps={screenProps} />,
      }),
    },
    Notifications,
  },
  {
    initialRouteName: 'Chat',
    defaultNavigationOptions: {
      headerLeft: <HeaderLeft />,
      headerRight: <HeaderRight />,
      headerTitleStyle: gStyle.textLarsBold16,
    },
    transitionConfig: ModalRoutes,
  },
);

export default ChatStack;
