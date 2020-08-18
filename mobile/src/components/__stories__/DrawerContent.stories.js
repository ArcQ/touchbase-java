import React from 'react';

import { storiesOf } from '@storybook/react-native';
import DrawerContent from '../DrawerContent';
import { MockWithPadding } from '../../utils/testing/Mock';

export const Basic = () => (
  <MockWithPadding appearance="outline">
    <DrawerContent />
  </MockWithPadding>
);

storiesOf('DrawerContent', module).add('basic', Basic);
