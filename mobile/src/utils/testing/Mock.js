import { View } from 'react-native';
import PropTypes from 'prop-types';
import React from 'react';
import { SafeAreaProvider, SafeAreaView } from 'react-native-safe-area-context';

export default function Mock(props) {
  return <SafeAreaProvider>{props.children}</SafeAreaProvider>;
}

Mock.propTypes = {
  children: PropTypes.node,
};

export function MockWithPadding(props) {
  return (
    <Mock>
      <SafeAreaView style={{ flex: 1 }}>
        <View style={{ marginTop: 50, flex: 1 }}>{props.children}</View>
      </SafeAreaView>
    </Mock>
  );
}

MockWithPadding.propTypes = {
  children: PropTypes.node,
};
