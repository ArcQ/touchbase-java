import { Auth } from 'aws-amplify';
import axios from 'axios';

async function getTouchbaseInstance() {
  const data = await Auth.currentSession();

  return axios.create({
    baseURL: 'https://touchbase-dev-bitfvu56tq-uc.a.run.app',
    headers: {
      Authorization: 'Bearer ' + data.accessToken.jwtToken,
    },
  });
}

export default {
  getTouchbaseInstance,
};
