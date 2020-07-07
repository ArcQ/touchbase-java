import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import './BasePage.css';
// import Modal from '../../components/Modals/Modal';

function BasePage({ base }) {
  // const actions = (
  //   <div>
  //     <button>Save</button>
  //     <button>Close</button>
  //   </div>
  // );

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
          {/* <Modal
            title='Settings'
            content='these are the settings of the base'
            actions={actions}
            onDismiss={() => {
              console.log('dismissed');
            }}
          /> */}
          <Link to={`/user/bases/${base.uuid}/settings`} className='button'>
            SETTINGS
          </Link>
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
