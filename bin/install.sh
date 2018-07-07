#!/bin/bash

#unzip /tmp/src/config/keycloak-wildfly-adapter-dist-4.1.0.Final.zip -d ${JBOSS_HOME}
cd ${JBOSS_HOME}
./bin/jboss-cli.sh --file=bin/adapter-install-offline.cli
