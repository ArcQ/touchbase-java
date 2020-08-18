/** @module */
/**
 * watchActions
 *
 * @param {array} actionsAndRunners variadic amount of arguments of form
 [ACTION_NAME_0, generatorToRun0, hideProgress],
 [ACTION_NAME_1, generatorToRun1, hideProgress]...
 - hideProgress is if you don't want to show progress bar when running generator
 * @param {...array} args additional arguments to pass into generators
 * @returns {undefined}
 */
import { all, fork, takeLatest } from 'redux-saga/effects';

import takeFirst from './takeFirst';
import wrapInShowProgressBar from './wrapInShowProgressBar';

export default function* watchActions(actionsAndRunners, ...args) {
  yield all(
    actionsAndRunners.map(([action, generator, showProgress, ..._args]) =>
      fork(function* watchAction() {
        yield takeFirst(
          action,
          showProgress ? wrapInShowProgressBar(generator) : generator,
          ...args.concat(_args),
        );
      }),
    ),
  );
}

export function* watchActionsLatest(actionsAndRunners, ...args) {
  yield all(
    actionsAndRunners.map(([action, generator, showProgress, ..._args]) =>
      fork(function* watchAction() {
        yield takeLatest(
          action,
          showProgress ? wrapInShowProgressBar(generator) : generator,
          ...args.concat(_args),
        );
      }),
    ),
  );
}
