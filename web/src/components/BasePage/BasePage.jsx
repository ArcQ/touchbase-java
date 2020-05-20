import React from 'react';
import { connect } from 'react-redux';

import './BasePage.css';

function BasePage({ selectedBase }) {
  if (selectedBase) {
    return (
      <div className='base-page'>
        <p className='base-page-name'>{selectedBase.name}</p>
        <p className='base-page-score'>
          <span class='fa fa-star yellow'></span> {selectedBase.score}
        </p>
        <img src={selectedBase.icon} alt={selectedBase.name} />
      </div>
    );
  }
  return (
    <div className='base-page'>
      <p className='base-page-name'>Welcome to TouchBase</p>
    </div>
  );
}

const mapStateToProps = (state) => {
  return {
    selectedBase: state.selectedBase,
  };
};

export default connect(mapStateToProps)(BasePage);
