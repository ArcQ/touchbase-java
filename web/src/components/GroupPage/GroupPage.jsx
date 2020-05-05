import React from 'react';

import './GroupPage.css';

const GroupPage = ({ groups, selectedGroup }) => {
  return (
    <div className='GroupPage'>
      <p className='GroupPage-name'>{groups[selectedGroup].name}</p>
      <p className='GroupPage-points'>
        <span class='fa fa-star yellow'></span>
        {groups[selectedGroup].points}
      </p>
      <img src={groups[selectedGroup].icon} alt={groups[selectedGroup].name} />
    </div>
  );
};

export default GroupPage;
