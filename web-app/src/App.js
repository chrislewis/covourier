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
  
  constructor({ deliveries }) {
    super();
    this.deliveries = deliveries;
  }

  render() {
    return (
      <div className="app">
        <Router>
          <Switch>
            <Route path="/deliveries">
              <Deliveries deliveryService={this.deliveries} deliveries={this.state.deliveries} />
            </Route>
            <Route path="/delivery">
              <NewDelivery />
            </Route>
          </Switch>

          <div className="navigation">
            <nav>
              <Link to="/deliveries">Active Deliveries</Link>
              <Link className="create-delivery" to="/delivery">&#x2295;</Link>
            </nav>
          </div>

        </Router>
      </div>
    );
  }

  state = {
    deliveries: []
  }

  componentDidMount() {
    this.deliveries.getAll()
      .then((data) => {
        this.setState({ deliveries: data })
      })
      .catch(console.log)
  }
}

export default App;
