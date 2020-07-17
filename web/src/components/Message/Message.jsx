import React from 'react';
import PropTypes from 'prop-types';

function Message({ message, author }) {
  return (
    <p>
      <i>{author}</i>: {message}
    </p>
  );
}

Message.propTypes = {
  message: PropTypes.string.isRequired,
  author: PropTypes.string.isRequired,
};

export default Message;
