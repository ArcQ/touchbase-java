import React from 'react';
import PropTypes from 'prop-types';

import './NewMessageBar.css';

function NewMessageBar(props) {
  let input;

  return (
    <div className='new-message-bar-container'>
      <input
        onKeyPress={(e) => {
          if (e.key === 'Enter') {
            props.dispatch(input.value, 'Me');
            input.value = '';
          }
        }}
        type='text'
        ref={(node) => {
          input = node;
        }}
      />
    </div>
  );
}

NewMessageBar.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

export default NewMessageBar;
