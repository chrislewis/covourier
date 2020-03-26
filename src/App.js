import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import Deliveries from './components/Deliveries';
import NewDelivery from './components/NewDelivery';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path="/deliveries">
            <Deliveries deliveries={this.state.deliveries} />
          </Route>
          <Route path="/delivery">
            <NewDelivery />
          </Route>
        </Switch>
        <div>
          <Link to="/deliveries">deliveries</Link>
          <Link to="/delivery">&#x2295;</Link>
        </div>
      </Router>
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
