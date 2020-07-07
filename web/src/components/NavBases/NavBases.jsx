import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { selectBase } from '../../actions/basesActions';
import './NavBases.css';

function NavBases(props) {
  const { user, pathname } = props;

  if (!user) {
    return <div className='nav-bases'></div>;
  }

  return (
    <div className='nav-bases'>
      {user.bases.map((base) => (
        <Link
          to={`/user/bases/${base.uuid}`}
          className='nav-bases-item'
          key={base.name}
          id={pathname.includes(`/user/bases/${base.uuid}`) ? 'selected' : ''}
        >
          <img className='circle-icon' src={base.imageUrl} alt={base.name} />
        </Link>
      ))}
      <div className='nav-bases-item' style={{ fontSize: 16 }}>
        +
      </div>
      <Link
        to='/user/profile'
        className='nav-bases-item'
        id={pathname === '/user/profile' ? 'selected' : ''}
      >
        {user.username}
      </Link>
    </div>
  );
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
  };
};

export default connect(mapStateToProps, { selectBase })(NavBases);
