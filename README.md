## IIIF-Producer

[![Build Status](https://travis-ci.org/ubleipzig/iiif-producer.png?branch=master)](https://travis-ci.org/ubleipzig/iiif-producer)
[![codecov](https://codecov.io/gh/ubleipzig/iiif-producer/branch/master/graph/badge.svg)](https://codecov.io/gh/ubleipzig/iiif-producer)

A CLI tool that generates IIIF Presentation 2.1 Manifests from METS/MODS (produced by Kitodo)

## Build
`$ ./gradlew clean build`

## Test
`$ gradle test`

## Requirements
* imageMagick with openjp2 delegate

See `buildtools/src/install/install-openjpeg.sh`

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
bin/producer -x xmlFile -i imageSourceDir-o outputfile -v view_identifier -c configFile [-s]
```

| Argument | Description | Example     |
| -------- | ----------- | ----------- |
| xmlFile  | A METS/MODS xml file path | /MS_187.xml |
| imageSourceDir | the image source directory | /images |
| outputfile | An JSON-LD output file path | /output.json |
| view_identifier | The name of the IIIF viewer identifer | 004285964 | 
| -c, --config | a yaml configuration File | etc/producer-config.yml |
| -s, --serializeImageManifest | serialize image manifest | |
| -u, --imageManifestUrl | imageManifestUrl | http://localhost:9098/extractor?type=dimensions&manifest=https://iiif.ub.uni-leipzig.de/0000000504/manifest.json |

## Image Dimension Manifest
* If dimensions can be produced from an existing manifest serialization, then use the `-u` option to reference the dimension manifest API producer.

* See [image](https://github.com/ubleipzig/image) for information about the image metadata creation process.
* the image.metadata generator supports reading dimensions from JP2 and JPX files

the default output location for the manifest when using the `-s` option is the `imageSourceDir`.
It will have a filename like this `image-manifest-185c961d-774c-5540-a31d-c0bca454c47d` 
   
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
