import PropTypes from 'prop-types';
import { Text, View } from 'react-native';
import { connect } from 'react-redux';
import React from 'react';

import { baseActions, baseSelectors } from '../store/base/ducks';

const style = {
  container: {
    flex: 1,
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'space-around',
    paddingTop: 20,
    width: '100%',
    backgroundColor: '#FFF',
  },
};

function BaseScreen({ base }) {
  return (
    <View>
      <Text>{base.name}</Text>
      <View />
      <View />
    </View>
  );
}

BaseScreen.propTypes = {
  base: PropTypes.shape({
    name: PropTypes.string,
  }),
};

const mapStateToProps = (state) => ({
  base: baseSelectors.base(state),
});

const mapDispatchToProps = {
  requestMyBases: baseActions.requestMyBases,
};

export default connect(mapStateToProps, mapDispatchToProps)(BaseScreen);
