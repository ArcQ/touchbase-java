import React from 'react';
import { connect } from 'react-redux';

import './MainPage.css';
import BasePage from '../BasePage/BasePage';

function MainPage({ selectedBase }) {
  // if (selectedBase) {
  //   return (
  //     <div className='main-page'>
  //       <BasePage />
  //     </div>
  //   );
  // }
  return <div className='main-page'>Welcome to Touchbase</div>;
}

const mapStateToProps = (state) => {
  return {
    selectedBase: state.selectedBase,
  };
};

export default connect(mapStateToProps)(MainPage);
