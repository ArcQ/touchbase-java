import React from 'react';
import { connect } from 'react-redux';

import './BasePage.css';

function BasePage({ selectedBase }) {
  if (selectedBase) {
    return (
      <div className='BasePage'>
        <p className='BasePage-name'>{selectedBase.name}</p>
        <p className='BasePage-score'>
          <span class='fa fa-star yellow'></span> {selectedBase.score}
        </p>
        <img src={selectedBase.icon} alt={selectedBase.name} />
      </div>
    );
  }
  return (
    <div className='BasePage'>
      <p className='BasePage-name'>Welcome to TouchBase</p>
    </div>
  );
}

// const BasePage = ({ bases, selectedBase }) => {
//   return (
//     <div className='BasePage'>
//       <p className='BasePage-name'>{bases[selectedBase].name}</p>
//       <p className='BasePage-score'>
//         <span class='fa fa-star yellow'></span> {bases[selectedBase].score}
//       </p>
//       <img src={bases[selectedBase].icon} alt={bases[selectedBase].name} />
//     </div>
//   );
// };

const mapStateToProps = (state) => {
  return {
    selectedBase: state.selectedBase,
  };
};

export default connect(mapStateToProps)(BasePage);
