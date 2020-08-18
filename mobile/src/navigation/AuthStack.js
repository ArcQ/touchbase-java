import { Auth } from 'aws-amplify';
import { Notifications } from 'expo';
import React from 'react';
import { createStackNavigator } from 'react-navigation';

import HeaderLeft from '../components/HeaderLeft';
import HeaderRight from '../components/HeaderRight';
import { gStyle } from '../constants';
import ModalRoutes from './ModalRoutes';

// screens

// components

// create stack navigator
const AuthStack = createStackNavigator(
  {
    Auth: {
      screen: Auth,
      navigationOptions: ({ screenProps }) => ({
        headerRight: <HeaderRight screenProps={screenProps} />,
      }),
    },
    Notifications,
  },
  {
    initialRouteName: 'Auth',
    defaultNavigationOptions: {
      headerLeft: <HeaderLeft />,
      headerRight: <HeaderRight />,
      headerTitleStyle: gStyle.textLarsBold16,
    },
    transitionConfig: ModalRoutes,
  },
);

export default AuthStack;
