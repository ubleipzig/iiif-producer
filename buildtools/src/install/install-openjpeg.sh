#!/bin/bash

set -e

OPENJPEG_VERSION=v2.3.0
OPENJPEG_ARCHIVE=openjpeg-${OPENJPEG_VERSION}-linux-x86_64.tar.gz

cd ~
git clone https://github.com/uclouvain/openjpeg.git
cd openjpeg
cmake -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX=/usr
make && make install
cd -

git clone https://github.com/ImageMagick/ImageMagick.git
cd ImageMagick
./configure --prefix=/usr --with-modules --with-perl=/usr/bin/perl --with-jp2 --enable-shared --disable-static
make && make install
cd -
