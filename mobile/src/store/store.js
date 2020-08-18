import { persistReducer, persistStore } from 'redux-persist';
import { createStore, applyMiddleware } from 'redux';
import createSagaMiddleware from 'redux-saga';

import AsyncStorage from '@react-native-community/async-storage';

import { cachedProfilesNamespace } from './cachedProfiles/ducks';
import { chatListNamespace } from './chatList/ducks';
import { threadNamespace } from './thread/ducks';
import createReducer from './reducers';
import rootSaga from './sagas';

const persistConfig = {
  key: 'reduxState',
  whitelist: [cachedProfilesNamespace, chatListNamespace, threadNamespace],
  storage: AsyncStorage,
};

const sagaMiddleware = createSagaMiddleware({});

export const storeFuncs = {
  dispatch: undefined,
};

export default function configureStore(initialState) {
  const rootReducer = createReducer();
  const persistedReducer = persistReducer(persistConfig, rootReducer);
  const store = createStore(
    persistedReducer,
    initialState,
    applyMiddleware(sagaMiddleware),
  );

  const persistor = persistStore(store);

  sagaMiddleware.run(rootSaga);

  store.runSaga = sagaMiddleware.run;
  store.asyncReducers = {};
  if (module.hot) {
    module.hot.accept('./reducers', () => {
      import('./reducers').then((reducerModule) => {
        const createReducers = reducerModule.default;
        const nextReducers = createReducers(store.asyncReducers);

        store.replaceReducer(nextReducers);
      });
    });
  }
  storeFuncs.dispatch = store.dispatch;
  return { store, persistor };
}
