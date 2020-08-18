import { connect } from 'react-redux';
import React from 'react';
import { GiftedChat } from 'react-native-gifted-chat';

import Message from '../components/Message';
import { threadActions, threadSelectors } from '../store/thread/ducks';
import AccessoryBar from '../components/AccessoryBar';

function ChatScreen({ messages, sendMessage }) {
  console.log(messages);
  return (
    <GiftedChat
      alwaysShowSend
      messages={messages}
      onSend={(msgs) => sendMessage(msgs)}
      renderAccessory={(_props) => <AccessoryBar {..._props} />}
      renderMessage={(_props) => <Message {..._props} />}
      renderSend={() => null}
      user={{ _id: 1 }}
    />
  );
}

const mapStateToProps = (state) => ({
  messages: threadSelectors.messages(state),
});

const mapDispatchToProps = {
  sendMessage: threadActions.sendMessage,
};

export default connect(mapStateToProps, mapDispatchToProps)(ChatScreen);
