import React from 'react';
import { Link } from 'react-router-dom';

function BaseChatPage({ base }) {
  return (
    <div className='page'>
      <Link to={`/user/bases/${base.uuid}`}>BACK</Link>
      <p className='title'>{base.name} Chat</p>
    </div>
  );
}

export default BaseChatPage;
