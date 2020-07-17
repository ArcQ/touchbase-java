import React from 'react';
import { Link } from 'react-router-dom';

import './BaseChatPage.css';
import ChatSideBar from '../../components/ChatSideBar/ChatSideBar';
import { MessagesList } from '../../components/MessagesList/MessagesListContainer';
import { NewMessageBar } from '../../components/NewMessageBar/NewMessageBarContainer';

function BaseChatPage({ base }) {
  return (
    <div className='page'>
      <div className='chat-container'>
        <div className='chat-sidebar'>
          <ChatSideBar />
        </div>
        <div className='chat-main'>
          <MessagesList />
          <NewMessageBar />
        </div>
      </div>
    </div>
  );
}

export default BaseChatPage;
