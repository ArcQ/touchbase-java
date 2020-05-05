import React from 'react';

import './BasePage.css';

const BasePage = ({ bases, selectedBase }) => {
  return (
    <div className='BasePage'>
      <p className='BasePage-name'>{bases[selectedBase].name}</p>
      <p className='BasePage-score'>
        <span class='fa fa-star yellow'></span> {bases[selectedBase].score}
      </p>
      <img src={bases[selectedBase].icon} alt={bases[selectedBase].name} />
    </div>
  );
};

export default BasePage;
