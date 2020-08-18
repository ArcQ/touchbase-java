/* @module */
import { delay, put } from 'redux-saga/effects';
import axios from 'axios';
import { Auth } from 'aws-amplify';

import { errorActions } from '../../store/error/ducks';
import envService from '../env/envService';
import modifyKeys from '../../utils/modifyKeys';
import { camelToSnake, snakeToCamel } from '../../utils/stringCasing';

// apiService will automatically throw an error if an error occurs, passing this in the onError handler will tell apiService to not throw the errorr notification (eg. you want to handle it yourself or swallow it silently)
export const NO_ERROR_NOTIFICATION = 'NO_ERROR_NOTIFICATION';

function errorHandler(responseData) {
  if (!responseData.error) {
    return false;
  }
  return responseData.error;
}

export function* apiCall({ call, onSuccess }, ...args) {
  try {
    const response = yield call(...args);
    yield onSuccess(response);
  } catch (e) {
    yield put(
      errorActions.setError({
        error: e,
      }),
    );
  }
}

/**
 * api - general api function
 *
 * @property {func} get
 * @property {func} post
 *
 * @returns {undefined}
 */
const apiService = {
  core: null,
  chat: null,
  init() {
    axios.defaults.timeout = 5000;
    axios.defaults.headers.common.Accept = 'application/json';
    axios.defaults.headers.common['Content-Type'] = 'application/json';
    axios.defaults.headers.common['App-Version'] =
      process.env.REACT_APP_VERSION;

    this.core = this.createInstance({
      baseUrl: envService.getConfig().coreApiUrl,
    });
    this.chat = this.createInstance({
      baseUrl: envService.getConfig().chatApiUrl,
    });
  },
  createInstance(axiosConfig, usesSnake) {
    const instance = axios.create(axiosConfig);

    instance.interceptors.request.use(async (config) => {
      if (usesSnake) {
        config.data = modifyKeys(config.data, camelToSnake);
        config.params = modifyKeys(config.params, camelToSnake);
      }

      const session = await Auth.currentSession();
      const { jwtToken } = session.accessToken;

      return {
        ...config,
        headers: {
          ...config.headers,
          Authorization: `Bearer ${jwtToken}`,
        },
      };
    });

    instance.interceptors.response.use(
      (response) => {
        const error = errorHandler(response.data);
        if (error) Promise.reject(error);
        if (usesSnake) {
          response.data = modifyKeys(response.data, snakeToCamel);
        }
        return response;
      },
      (error) => Promise.reject(errorHandler({ error })),
    );
    return instance;
  },
  *mock(endpoint, [gen], mockData) {
    yield delay(100);
    return yield gen(mockData);
  },
};

export default apiService;
