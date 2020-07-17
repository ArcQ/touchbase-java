import { connect } from 'react-redux';
import MessagesListComponent from './MessagesList';

export const MessagesList = connect(
  (state) => ({
    messages: state.chat,
  }),
  {}
)(MessagesListComponent);
