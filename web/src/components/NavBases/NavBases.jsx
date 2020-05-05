import React from 'react';

import './NavBases.css';

const NavBases = ({ bases, selectedBase, handleBaseClick }) => {
  return (
    <div className='NavBases'>
      {bases.map((base, i) => (
        <div
          key={i}
          id={selectedBase === i ? 'selected' : ''}
          className='NavBasesItem'
          onClick={() => handleBaseClick(i)}
        >
          <img className='circle-icon' src={base.icon} alt={base.name} />
        </div>
      ))}
    </div>
  );
};

export default NavBases;
