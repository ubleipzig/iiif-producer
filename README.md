## IIIF-Producer

[![Build Status](https://travis-ci.org/ubleipzig/iiif-producer.png?branch=master)](https://travis-ci.org/ubleipzig/iiif-producer)
[![codecov](https://codecov.io/gh/ubleipzig/iiif-producer/branch/master/graph/badge.svg)](https://codecov.io/gh/ubleipzig/iiif-producer)

A CLI tool that generates IIIF Presentation API v2 and v3 Manifests from METS/MODS (produced by Kitodo)

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
bin/producer -x xmlFile -o outputfile -v view_identifier -c configFile -f v2
```

| Argument     | Description                           | Example                 | Default                 |
|--------------|---------------------------------------|---------------------------|-------------------------|
| -x           | A METS/MODS xml file path             | /MS_187.xml               |                         |
| -o           | An JSON-LD output file path           | /output.json              | /tmp/output.json       |
| -v           | The name of the IIIF viewer identifer | 0004285964                |                         |
| -c, --config | a yaml configuration File             | etc/producer-config.yml    | etc/producer-config.yml |
| -f, --format | a Presentation API version format     | v2                        | v3                      |

## Images

The images must already be present on the image server consistent the semantics of the service URL builder.

Example: `https://iiif.ub.uni-leipzig.de/iiif/j2k/{$viewId[0-4]}/{$viewId[5-8]}/{$viewId[0-10]}/{imageIndex[0-8]}.jpx`

## Java

* This requires Java 8 or higher

## Local Testing

If using IntelliJ, enable the built-in webserver (requires the PHP plugin).  See Preferences -> Build,Execution,Deployment -> Debugger.

Install Mirador 3 
```
npm install mirador
```
Start Mirador
```
npm start
```

Add local manifest to catalogue e.g. 
`http://localhost:63342/iiif-producer/converter/src/test/resources/HSP.json`
