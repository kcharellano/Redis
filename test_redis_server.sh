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

test_ping() {
  # Use redis-cli to test the connection
  if redis-cli -h $host -p $port ping | grep -q "PONG"; then
      echo "[TEST 2 PASSED] Ping test passed"
  else
      echo "Error: Ping test failed"
      exit 1
  fi
}


test_connection
test_ping








