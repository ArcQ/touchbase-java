import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';

import UserApp from './pages/UserApp/UserApp';

import './App.css';

function App() {
  return (
    <div className='app'>
      <Switch>
        <Route exact path='/user' render={() => <UserApp />} />
      </Switch>
    </div>
  );
}

export default App;
