import React, { Component } from 'react';
import { connect } from 'react-redux';

import { selectBase } from '../../actions';
import { fetchBases } from '../../actions';
import './NavBases.css';

class NavBases extends Component {
  componentDidMount() {
    this.props.fetchBases();
  }

  render() {
    const { bases, selectedBase } = this.props;

    //force coding this for now, will refactor
    if (selectedBase) {
      return (
        <div className='nav-bases'>
          {bases.map((base) => (
            <div
              key={base.name}
              id={selectedBase.name === base.name ? 'selected' : ''}
              className='nav-bases-item'
              onClick={() => this.props.selectBase(base)}
            >
              <img className='circle-icon' src={base.icon} alt={base.name} />
            </div>
          ))}
        </div>
      );
    }
    return (
      <div className='nav-bases'>
        {bases.map((base) => (
          <div
            key={base.name}
            className='nav-bases-item'
            onClick={() => this.props.selectBase(base)}
          >
            <img className='circle-icon' src={base.icon} alt={base.name} />
          </div>
        ))}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    bases: state.bases,
    selectedBase: state.selectedBase,
  };
};

export default connect(mapStateToProps, { fetchBases, selectBase })(NavBases);
