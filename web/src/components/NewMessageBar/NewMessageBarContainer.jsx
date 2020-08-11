import { connect } from 'react-redux';
import NewMessageBarComponent from './NewMessageBar';
import { addMessage } from '../../actions/chatActions';

const mapDispatchToProps = (dispatch) => ({
  dispatch: (message, author) => {
    dispatch(addMessage(message, author));
  },
});

export const NewMessageBar = connect(
  () => ({}),
  mapDispatchToProps
)(NewMessageBarComponent);
