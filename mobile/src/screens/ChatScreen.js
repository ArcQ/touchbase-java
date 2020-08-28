import { View } from 'react-native';
import { connect } from 'react-redux';
import React from 'react';

import { threadActions, threadSelectors } from '../store/thread/ducks';

function BaseScreen({ messages, sendMessage }) {
  return (
    <View />
  );
}

const mapStateToProps = (state) => ({
  messages: threadSelectors.messages(state),
});

const mapDispatchToProps = {
  sendMessage: threadActions.sendMessage,
};

export default connect(mapStateToProps, mapDispatchToProps)(BaseScreen);
