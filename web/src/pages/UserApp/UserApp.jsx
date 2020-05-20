import React from 'react';
import { Route } from 'react-router-dom';

import NavBases from '../../components/NavBases/NavBases';
import BasePage from '../../components/BasePage/BasePage';
import './UserApp.css';

function UserApp() {
  return (
    <div className='user-app'>
      <NavBases />
      <BasePage />
      <Route></Route>
    </div>
  );
}

export default UserApp;
