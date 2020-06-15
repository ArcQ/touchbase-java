import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import Amplify from 'aws-amplify';
import awsconfig from './aws-exports';

import './index.css';
import App from './App';
import reducers from './reducers';
import * as serviceWorker from './serviceWorker';

Amplify.configure(awsconfig);

const store = createStore(reducers, applyMiddleware(thunk));

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <Router>
        <Route path='/' component={App} />
      </Router>
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);

serviceWorker.unregister();
