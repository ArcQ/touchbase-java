import React from 'react';
import PropTypes from 'prop-types';
import Message from '../Message/Message';

function MessagesList({ messages }) {
  return (
    <div>
      <ul>
        {messages.map((message) => (
          <Message key={message.id} {...message} />
        ))}
      </ul>
    </div>
  );
}

MessagesList.propTypes = {
  messages: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      message: PropTypes.string.isRequired,
      author: PropTypes.string.isRequired,
    }).isRequired
  ).isRequired,
};

export default MessagesList;
