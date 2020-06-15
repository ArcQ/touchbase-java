import React from 'react';
import { connect } from 'react-redux';

import './UserDetailsPage.css';

function UserDetailsPage({ user }) {
  return <div className='user-details-page'>USER PROFILE</div>;
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
  };
};

export default connect(mapStateToProps)(UserDetailsPage);
