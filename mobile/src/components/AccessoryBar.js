import React from 'react';
import { View } from 'react-native';

import { gStyle } from '../constants';
import SvgAt from './icons/Svg.At';
import SvgPaperClip from './icons/Svg.PaperClip';
import SvgImage from './icons/Svg.Image';
import SendButton from './SendButton';

export default (props) => (
  <View style={[gStyle.flexRowCenter, gStyle.pH8]}>
    <View style={[gStyle.flexRow, gStyle.flex4]}>
      <View style={gStyle.mR16}>
        <SvgAt />
      </View>
      <SvgPaperClip />
    </View>
    <View style={gStyle.flexRowCenterAlign}>
      <View style={gStyle.mR16}>
        <SvgImage />
      </View>
      <SendButton {...props} />
    </View>
  </View>
);
