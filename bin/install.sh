#!/bin/bash

unzip -o /tmp/src/config/keycloak-wildfly-adapter-dist-4.1.0.Fina.zip -d ${JBOSS_HOME}
cd ${JBOSS_HOME}
./bin/jboss-cli.sh --file="${JBOSS_HOME}/bin/adapter-install.cli"