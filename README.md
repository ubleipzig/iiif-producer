## IIIF-Producer

[![Build Status](https://travis-ci.org/ubleipzig/iiif-producer.png?branch=master)](https://travis-ci.org/ubleipzig/iiif-producer)
[![codecov](https://codecov.io/gh/ubleipzig/iiif-producer/branch/master/graph/badge.svg)](https://codecov.io/gh/ubleipzig/iiif-producer)

A CLI tool that generates IIIF Presentation 2.1 Manifests from METS/MODS (produced by Kitodo)

## Build

`$ ./gradlew clean build`

## Test

`$ gradle test`

## Install

* find distribution archive in `producer/build/distributions`
* extract archive

```bash
$ cd producer-{$version}
$ chmod +x bin/producer
```

## Configuration

See `etc/producer-config.yml`

## Usage

```bash
bin/producer -x xmlFile -o outputfile -v view_identifier -c configFile
```

| Argument | Description | Example     |
| -------- | ----------- | ----------- |
| -x | A METS/MODS xml file path | /MS_187.xml |
| -o | An JSON-LD output file path | /output.json |
| -v | The name of the IIIF viewer identifer | 0004285964 |
| -c, --config | a yaml configuration File | etc/producer-config.yml |

## Images

The images must already be present on the image server consistent the semantics of the service URL builder.

Example: `https://iiif.ub.uni-leipzig.de/iiif/j2k/{$viewId[0-4]}/{$viewId[5-8]}/{$viewId[0-10]}/{imageIndex[0-8]}.jpx`

## Java

* This requires Java 8 or higher

## Local Testing

* change configuration settings

 ```yaml
 isUBLImageService: false
 imageServiceBaseUrl: "http://localhost:5000/iiif/"
 imageServiceFileExtension: ".tif"
 ```

```bash
$ cp {$imageSourceDir} /mnt/serialization/binaries/{$viewId}
$ docker-compose up
```

A Mirador instance is available at
`http://localhost:9000`

The image service URI defaults to:

* `http://localhost:5000/iiif/{viewId}/{file name.tif}`

* If your package images are not pyramidal tifs, then run these commands in the image directory:

```bash
$ mogrify -define tiff:tile-geometry=256x256 -depth 8 -format ptif *.tif
$ rm *.tif
$ rename 's/.ptif$/.tif/' *.ptif
```

* Note: generated manifests can published to `/mnt/http-server`

```bash
$ bin/producer -x xmlFile -i /mnt/serialization/binaries/{$imageSoureDir} -o /mnt/http-server/outputfile -v {$imageSoureDir} -c configFile [-s]
``` 

The manifest URI will be `http://localhost:3000/{$your_outputfile.jsonld}`
