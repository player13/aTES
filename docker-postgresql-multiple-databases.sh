#!/bin/bash

set -e
set -u

function create_user_and_database() {
    local database=$(echo $1 | tr '@' ' ' | awk  '{print $2}')
    local creds=$(echo $1 | tr '@' ' ' | awk  '{print $1}')
    local user=$(echo $creds | tr ':' ' ' | awk  '{print $1}')
    local pass=$(echo $creds | tr ':' ' ' | awk  '{print $2}')
    echo "  Creating database '$database'"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        CREATE USER $user WITH PASSWORD '$pass';
        CREATE DATABASE $database WITH OWNER $user;
EOSQL
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
    echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
    for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
        create_user_and_database $db
    done
    echo "Multiple databases created"
fi