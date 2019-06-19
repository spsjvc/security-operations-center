import React from 'react'
import axios from 'axios'
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Container from 'react-bootstrap/Container'

export default function() {
  return (
    <Container style={{ height: '100%' }}>
      <Row style={{ marginTop: '2rem' }}>
        <h1>Security Operations Center</h1>
      </Row>
      <Row style={{ marginTop: '2rem' }}>
        <Col md={4}>
          <Button
            onClick={() => {
              axios.post('http://localhost:8080/simulate-payment-system-alarm')
            }}
          >
            Simulate Payment System Alarm
          </Button>
        </Col>
      </Row>
      <Row style={{ marginTop: '1rem' }}>
        <Col md={4}>
          <Button
            onClick={() => {
              axios.post(
                'http://localhost:8080/simulate-three-unsuccessful-logins-alarm'
              )
            }}
          >
            Simulate Three Unsuccessful Logins
          </Button>
        </Col>
      </Row>
    </Container>
  )
}
