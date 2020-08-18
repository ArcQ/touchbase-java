import PropTypes from 'prop-types';
import { View } from 'react-native';
import { connect } from 'react-redux';
import React from 'react';

import { appSelectors } from '../store/app/ducks';

const style = {
  overlayLoadingContainer: {
    position: 'absolute',
    top: 0,
    bottom: 0,
    right: 0,
    left: 0,
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 1000,
  },
};

function LoadingOverlay(props) {
  return <>{props.isShow && <View style={style.overlayLoadingContainer} />}</>;
}

LoadingOverlay.propTypes = {
  isShow: PropTypes.bool,
};

const mapStateToProps = (state) => ({
  isShow: appSelectors.isAppLoadingAnything(state),
});

export default connect(mapStateToProps, undefined)(LoadingOverlay);
