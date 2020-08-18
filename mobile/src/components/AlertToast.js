import { Text, View } from 'react-native';
import Modal from 'react-native-modal';
import React from 'react';
import PropTypes from 'prop-types';

import globalStyles from '../constants/globalStyles';
import alertsContent from '../constants/alertsContent';

const style = {
  alertModalContainer: {
    justifyContent: 'flex-end',
    margin: 0,
  },
  alertContainer: {
    ...globalStyles.flexRowCenter,
    padding: 25,
  },
  alertText: {
    ...globalStyles.textCiruBook12,
    color: colors.white10,
  },
};
// TODO, should make this generic toast, not just alert modal, because it allows for success now
export default function AlertModal(props) {
  const parseAlert = () =>
    props.alert.message || alertsContent(props.alert?.k, props.alert?.value);
  return (
    <Modal
      backdropOpacity={0.1}
      isVisible={!!props.alert?.k}
      onBackdropPress={props.onClose}
      style={style.alertModalContainer}
      onSwipeComplete={props.onClose}
      swipeDirection={['down']}
    >
      <View
        style={[
          style.alertContainer,
          {
            backgroundColor:
              props.alert?.k === 'success' ? colors.success : colors.fail,
          },
        ]}
      >
        <Text style={style.alertText}>
          {props.alert?.k === 'success' ? props.alert.v : parseAlert()}
        </Text>
      </View>
    </Modal>
  );
}

AlertModal.propTypes = {
  onClose: PropTypes.func,
  alert: PropTypes.objectOf(PropTypes.string),
};
