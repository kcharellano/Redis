#!/bin/sh
set -e

host="localhost"
port="6379"

test_connection() {
  # Use netcat to test the connection
  if nc -z $host $port; then
      echo "[TEST 1 PASSED] Endpoint is accepting connections"
  else
      echo "Error: Endpoint is not accepting connections"
      exit 1
  fi
}

ping_helper() {
  if redis-cli -h $host -p $port ping | grep -q "PONG"; then
        : # Do nothing
    else
        echo "Error: Ping test failed"
        exit 1
  fi
}

test_ping() {
  ping_helper
  echo "[TEST 2 PASSED] Ping test passed"
}

test_multi_ping() {
  # Use redis-cli to test the connection
  if echo "ping\nping" | redis-cli > /dev/null; then
      echo "[TEST 3 PASSED] Multi Ping test passed"
  else
      echo "Error: Ping test failed"
      exit 1
  fi
}

test_concurrent_clients() {
  ping_helper & ping_helper
  echo "[TEST 4 PASSED] Concurrent clients test passed"
}

test_echo_command() {
  if echo "echo hello" | redis-cli | grep -q "hello"; then
      echo "[TEST 5 PASSED] Echo command test passed"
  else
      echo "Error: Echo command test failed"
      exit 1
  fi
}

test_set_command() {
  if echo "set mykey hello" | redis-cli | grep -q "OK"; then
      echo "[TEST 6 PASSED] Set command test passed"
  else
      echo "Error: Set command test failed"
      exit 1
  fi
}

test_get_command() {
  if echo "get mykey" | redis-cli | grep -q "hello"; then
      echo "[TEST 7 PASSED] Get command test passed"
  else
      echo "Error: Get command test failed"
      exit 1
  fi
}

# Run the tests
test_connection
test_ping
test_multi_ping
test_concurrent_clients
test_echo_command
test_set_command
test_get_command










