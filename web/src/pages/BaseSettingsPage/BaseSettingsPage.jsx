import React from 'react';
import { Link } from 'react-router-dom';

function BaseSettingsPage({ base }) {
  return (
    <div className='page'>
      <div className='card'>
        <div className='info-container'>
          <Link to={`/user/bases/${base.uuid}`}>BACK</Link>
          <p className='title'>{base.name} Settings</p>
        </div>
      </div>
    </div>
  );
}

export default BaseSettingsPage;
