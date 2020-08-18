import PropTypes from 'prop-types';
import React from 'react';
import { connect } from 'react-redux';

import { errorActions, errorSelectors } from '../store/error/ducks';

function AlertContainer(props) {
  return <AlertModal error={props.error} onClose={() => props.clearError()} />;
}

AlertContainer.propTypes = {
  clearAlert: PropTypes.func,
};

const mapStateToProps = (state) => ({
  error: errorSelectors.error(state),
});

const mapDispatchToProps = {
  clearAlert: errorActions.clearAlert,
};
export default connect(mapStateToProps, mapDispatchToProps)(AlertContainer);
