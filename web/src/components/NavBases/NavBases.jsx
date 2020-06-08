import React, { Component } from 'react';
import { connect } from 'react-redux';

import { selectBase } from '../../actions';
import { fetchUserBases } from '../../actions';
import './NavBases.css';

class NavBases extends Component {
  componentDidMount() {
    this.props.fetchUserBases();
  }

  render() {
    const { userBases, selectedBase } = this.props;

    if (!userBases) {
      return 'loading';
    }
    //force coding this for now, will refactor
    else if (selectedBase) {
      return (
        <div className='nav-bases'>
          {userBases.bases.map((base) => (
            <div
              key={base.name}
              id={selectedBase.name === base.name ? 'selected' : ''}
              className='nav-bases-item'
              onClick={() => this.props.selectBase(base)}
            >
              <img
                className='circle-icon'
                src={base.imageUrl}
                alt={base.name}
              />
            </div>
          ))}
        </div>
      );
    }
    return (
      <div className='nav-bases'>
        {userBases.bases.map((base) => (
          <div
            key={base.name}
            className='nav-bases-item'
            onClick={() => this.props.selectBase(base)}
          >
            <img className='circle-icon' src={base.imageUrl} alt={base.name} />
          </div>
        ))}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  console.log(state);
  return {
    userBases: state.userBases,
    selectedBase: state.selectedBase,
  };
};

export default connect(mapStateToProps, { fetchUserBases, selectBase })(
  NavBases
);
