import React from 'react';
import ReactDOM from 'react-dom';

import './Modal.css';

function Modal(props) {
  return ReactDOM.createPortal(
    <div onClick={props.onDismiss} className='modal-container'>
      <div className='modal-contents'>
        {props.title} {props.content} {props.actions}
      </div>
    </div>,
    document.querySelector('#modal')
  );
}

export default Modal;
