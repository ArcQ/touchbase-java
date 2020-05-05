import React, { Component } from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import axios from 'axios';

import NavBases from '../../components/NavBases/NavBases';
import BasePage from '../../components/BasePage/BasePage';
import './UserApp.css';

const bases = [
  {
    name: 'Base A',
    icon:
      'https://thumbs.dreamstime.com/b/teamwork-group-friends-icon-vector-illustration-teamwork-group-friends-icon-118637039.jpg',
    score: 300,
  },
  {
    name: 'Base B',
    icon:
      'data:image/png;base64,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',
    score: 500,
  },
  {
    name: 'Base C',
    icon:
      'https://i.pinimg.com/originals/37/ba/99/37ba99d54bb208fd4d855b1565e81b9d.png',
    score: 1300,
  },
];

class UserApp extends Component {
  constructor(props) {
    super(props);
    this.state = {
      bases: bases,
      selectedBase: 0,
    };
  }

  componentDidMount() {
    const config = {
      headers: {
        Origin: 'http://localhost:3000/',
      },
    };
    axios
      .get('https://touchbase-bitfvu56tq-uc.a.run.app/bases/', config)
      .then((res) => {
        console.log('res', res);
        if (res) {
          this.setState({
            bases: res.data,
          });
        }
      })
      .catch((error) => console.log('Authorization failed : ' + error.message));
  }

  handleBaseClick = (selected) => {
    this.setState({
      selectedBase: selected,
    });
  };

  render() {
    return (
      <div className='UserApp'>
        <NavBases
          bases={this.state.bases}
          selectedBase={this.state.selectedBase}
          handleBaseClick={this.handleBaseClick}
        />
        <BasePage
          bases={this.state.bases}
          selectedBase={this.state.selectedBase}
        />
        <Route></Route>
      </div>
    );
  }
}

export default UserApp;
