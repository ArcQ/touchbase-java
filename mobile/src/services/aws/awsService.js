import Amplify from 'aws-amplify';

import awsconfig from '../../awsconfig.json';

const amplifyConfigure = () => Amplify.configure(awsconfig);

export default {
  init() {
    amplifyConfigure();
  },
};
