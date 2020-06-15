import React, { Component } from 'react';
import { Route } from 'react-router-dom';
import { connect } from 'react-redux';

import './UserApp.css';
import { fetchUser } from '../../actions/userActions';
import { selectBase } from '../../actions/basesActions';
import NavBases from '../../components/NavBases/NavBases';
import MainPage from '../../components/MainPage/MainPage';
import BasePage from '../../components/BasePage/BasePage';
import UserDetailsPage from '../../components/UserDetailsPage/UserDetailsPage';

class UserApp extends Component {
  componentDidMount() {
    this.props.fetchUser();
  }

  render() {
    const { user, pathname } = this.props;

    if (!user) {
      return <div className='user-app'>LOADING...</div>;
    }
    return (
      <div className='user-app'>
        <NavBases pathname={pathname} />
        <Route exact path='/user' render={() => <MainPage />} />
        <Route exact path='/user/profile' render={() => <UserDetailsPage />} />
        {user.bases.map((base) => (
          <Route
            exact
            path={`/user/bases/${base.uuid}`}
            key={base.uuid}
            render={() => <BasePage base={base} />}
          />
        ))}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
    selectedBase: state.selectedBase,
  };
};

export default connect(mapStateToProps, { fetchUser, selectBase })(UserApp);
