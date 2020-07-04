import React from 'react';
import { connect } from 'react-redux';

import './BasePage.css';

function BasePage({ base }) {
  return (
    <div className='page'>
      <div className='card'>
        <div className='info-container'>
          <p className='title'>{base.name}</p>
          <p className='base-page-score'>
            <span className='fa fa-star yellow'></span> {base.score}
          </p>
          <img className='base-page-img' src={base.imageUrl} alt={base.name} />
          <div className='base-members'>
            <p>Members</p>
            <ul>
              {base.members.map((member) => (
                <li key={member.username}>{member.username}</li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}

const mapStateToProps = (state) => {
  return {
    selectedBase: state.selectedBase,
  };
};

export default connect(mapStateToProps)(BasePage);
