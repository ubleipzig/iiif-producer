#!/bin/bash
set -e

OPENJPEG_VERSION=v2.3.0
OPENJPEG_ARCHIVE=openjpeg-${OPENJPEG_VERSION}-linux-x86_64.tar.gz

cd ~
wget https://github.com/uclouvain/openjpeg/releases/download/${OPENJPEG_VERSION}/${OPENJPEG_ARCHIVE}
tar -xzf ${OPENJPEG_ARCHIVE}
export OPENJPEG_HOME=~/openjpeg-${OPENJPEG_VERSION}-linux-x86_64
export PATH=${OPENJPEG_HOME}/bin:$PATH
cd -
