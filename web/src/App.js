import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import { withAuthenticator, AmplifySignOut } from '@aws-amplify/ui-react';

import UserApp from './pages/UserApp/UserApp';

import './App.css';

function App() {
  return (
    <div className='app'>
      <AmplifySignOut />
      <Switch>
        <Route
          path='/user'
          render={({ history }) => (
            <UserApp pathname={history.location.pathname} />
          )}
        />
      </Switch>
    </div>
  );
}

export default withAuthenticator(App);
