import React from 'react';
import { connect } from 'react-redux';

import './UserDetailsPage.css';

function UserDetailsPage({ user }) {
  return (
    <div className='page'>
      <div className='card'>
        <div className='info-container'>
          <img
            className='user-img'
            src='https://i.imgur.com/O6isR7b.jpg/'
            alt='profile-img'
          />
          <div className='title'>{user.username}</div>
          <div className='user-info'>
            <table>
              <tbody>
                <tr>
                  <td className='table-header'>Full Name</td>
                  <td>
                    {user.firstName} {user.lastName}
                  </td>
                </tr>
                <tr>
                  <td className='table-header'>Email</td>
                  <td>{user.email}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <a className='button'>EDIT</a>
        </div>
      </div>
    </div>
  );
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
  };
};

export default connect(mapStateToProps)(UserDetailsPage);
