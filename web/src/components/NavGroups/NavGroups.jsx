import React from 'react';

import './NavGroups.css';

const NavGroups = ({ groups, selectedGroup, handleGroupClick }) => {
  return (
    <div className='NavGroups'>
      {groups.map((group, i) => (
        <div
          key={i}
          id={selectedGroup === i ? 'selected' : ''}
          className='NavGroupsItem'
          onClick={() => handleGroupClick(i)}
        >
          <img className='circle-icon' src={group.icon} alt={group.name} />
        </div>
      ))}
    </div>
  );
};

export default NavGroups;
