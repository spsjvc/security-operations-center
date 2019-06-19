import React, { Component } from 'react'
import axios from 'axios'
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Container from 'react-bootstrap/Container'
import ListGroup from 'react-bootstrap/ListGroup'

export default class App extends Component {
  state = {
    alarms: []
  }

  componentWillMount() {
    axios.get('http://localhost:8080/alarms').then(({ data }) => {
      this.setState({
        alarms: data
      })
    })
  }

  render() {
    return (
      <Container style={{ height: '100%' }}>
        <Row style={{ paddingTop: '2rem' }}>
          <h1>Security Operations Center</h1>
        </Row>
        <Row style={{ paddingTop: '2rem' }}>
          <Col md={6}>
            <h3>Simulations</h3>
          </Col>
        </Row>
        <Row style={{ paddingTop: '2rem' }}>
          <Col md={6}>
            <Button
              onClick={() => {
                axios.post(
                  'http://localhost:8080/simulate-payment-system-alarm'
                )
              }}
            >
              Simulate Payment System Alarm
            </Button>
          </Col>
        </Row>
        <Row style={{ paddingTop: '1rem' }}>
          <Col md={6}>
            <Button
              onClick={() => {
                axios.post(
                  'http://localhost:8080/simulate-three-unsuccessful-logins-alarm'
                )
              }}
            >
              Simulate 3 Unsuccessful Logins
            </Button>
          </Col>
        </Row>
        <Row style={{ paddingTop: '1rem' }}>
          <Col md={6}>
            <Button
              onClick={() => {
                axios.post(
                  'http://localhost:8080/simulate-seven-antivirus-threats-from-the-same-machine'
                )
              }}
            >
              Simulate 7 Antivirus Threats from the Same Machine
            </Button>
          </Col>
        </Row>
        <Row style={{ paddingTop: '1rem' }}>
          <Col md={6}>
            <Button
              onClick={() => {
                axios.post(
                  'http://localhost:8080/simulate-login-from-inactive-account'
                )
              }}
            >
              Simulate Login from Inactive Account
            </Button>
          </Col>
        </Row>
        <Row style={{ paddingTop: '1rem' }}>
          <Col md={6}>
            <Button
              onClick={() => {
                axios.post(
                  'http://localhost:8080/simulate-fifteen-logins-from-the-same-ip'
                )
              }}
            >
              Simulate 15 Unsuccessful Logins from the Same IP
            </Button>
          </Col>
        </Row>
        <Row style={{ paddingTop: '2rem', paddingBottom: '2rem' }}>
          <Col md={6}>
            <h3>Alarms</h3>
            <ListGroup>
              {this.state.alarms.map(alarm => (
                <ListGroup.Item key={alarm.id}>
                  <b>Type: </b> {alarm.type}
                  <br />
                  <b>Message: </b> {alarm.message}
                  <br />
                  <b>Username: </b> {alarm.username}
                  <br />
                  <b>Time: </b> {alarm.timestamp}
                </ListGroup.Item>
              ))}
            </ListGroup>
          </Col>
        </Row>
      </Container>
    )
  }
}
