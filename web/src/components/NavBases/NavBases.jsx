import React from 'react';
import { connect } from 'react-redux';
import { selectBase } from '../../actions';
import './NavBases.css';

function NavBases({ bases, selectedBase, selectBase }) {
  //force coding this for now, will refactor
  if (selectedBase) {
    return (
      <div className='NavBases'>
        {bases.map((base) => (
          <div
            key={base.name}
            id={selectedBase.name === base.name ? 'selected' : ''}
            className='NavBasesItem'
            onClick={() => selectBase(base)}
          >
            <img className='circle-icon' src={base.icon} alt={base.name} />
          </div>
        ))}
      </div>
    );
  }
  return (
    <div className='NavBases'>
      {bases.map((base) => (
        <div
          key={base.name}
          className='NavBasesItem'
          onClick={() => selectBase(base)}
        >
          <img className='circle-icon' src={base.icon} alt={base.name} />
        </div>
      ))}
    </div>
  );
}

// const NavBases = ({ bases, selectedBase, handleBaseClick }) => {
//   return (
//     <div className='NavBases'>
//       {bases.map((base, i) => (
//         <div
//           key={i}
//           id={selectedBase === i ? 'selected' : ''}
//           className='NavBasesItem'
//           onClick={() => handleBaseClick(i)}
//         >
//           <img className='circle-icon' src={base.icon} alt={base.name} />
//         </div>
//       ))}
//     </div>
//   );
// };

const mapStateToProps = (state) => {
  return {
    bases: state.bases,
    selectedBase: state.selectedBase,
  };
};

export default connect(mapStateToProps, { selectBase })(NavBases);
