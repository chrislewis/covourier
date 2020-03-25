import React, { Component } from 'react';
import Deliveries from './components/Deliveries';

class App extends Component {
  render() {
    return (
      <>
      <Deliveries deliveries={this.state.deliveries} />
      </>
    );
  }

  state = {
    deliveries: []
  }

  componentDidMount() {
    fetch('http://jsonplaceholder.typicode.com/users')
      .then(res => res.json())
      .then((data) => {
        this.setState({ deliveries: data })
      })
      .catch(console.log)
  }
}

export default App;
