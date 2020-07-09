import React, { Component } from 'react';
import { Field, reduxForm } from 'redux-form';

class BaseCreatePage extends Component {
  renderError({ error, touched }) {
    if (touched && error) {
      return <div>{error}</div>;
    }
  }

  renderInput = ({ input, label, meta }) => {
    return (
      <div className='field'>
        <label>{label}</label>
        <input {...input} autoComplete='off' />
        {this.renderError(meta)}
      </div>
    );
  };

  onSubmit(formValues) {}

  render() {
    return (
      <div className='page'>
        <div className='card'>
          <div className='info-container'>BASE CREATE</div>
          <form onSubmit={this.props.handleSubmit(this.onSubmit)}>
            <Field
              name='name'
              component={this.renderInput}
              label='Enter Base name'
            />
            <button>Submit</button>
          </form>
        </div>
      </div>
    );
  }
}

function validate(formValues) {
  const errors = {};

  if (!formValues.name) {
    errors.name = 'Base must have a name';
  }

  return errors;
}

export default reduxForm({
  form: 'baseCreate',
  validate,
})(BaseCreatePage);
