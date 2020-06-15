import React from 'react';
import { connect } from 'react-redux';

import './BasePage.css';

function BasePage({ base }) {
  return (
    <div className='base-page'>
      <p className='base-page-name'>{base.name}</p>
      <p className='base-page-score'>
        <span className='fa fa-star yellow'></span> {base.score}
      </p>
      <img src={base.imageUrl} alt={base.name} />
      <div className='base-members'>
        <p>Members</p>
        <ul>
          {base.members.map((member) => (
            <li key={member.username}>{member.username}</li>
          ))}
        </ul>
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
