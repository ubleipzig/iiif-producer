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
$ cd producer-{$version}/bin
$ chmod +x producer
```

## Usage
```bash
producer -i inputfile -o outputfile -t archive_package -v view_identifier [-s]
```

| Argument | Description | Example |
| -------- | ------- | ----------- | 
| inputfile | A METS/MODS xml file path | /MS_187.xml |
| outputfile | An JSON-LD output file path | /output.json |
| archive_package | The name of the archive package | MS_187 |
| view_identifier | The name of the IIIF viewer identifer | 004285964 | 
| -s, --serializeImageManifest | serialize image manifest | |

## Image Dimension Manifest
* See [image](https://github.com/ubleipzig/image) for information about the image metadata creation process.
* the image.metadata generator supports reading dimensions from JP2 and JPX files

the default ouput location for the manifest when using the `-s` option is the base directory where the xml is located.
It will have a filename like this `image-manifest-185c961d-774c-5540-a31d-c0bca454c47d` 

 

## Configuration Dependencies
* the Image Service Identifier assignment may have to be reconfigured for your infrastructure. (see Constants)
* the metadata package must look like this:

```
package
|--metadata_12345.xml
+--metadata_12345_tif
    |--00000001.tif
    |--00000002.tif
    |...
```    
   
## Java
* This requires Java 8 or higher

## Local Testing

```bash
$ cp {package image directory} /mnt/serialization/binaries/
$ cp {package metadatafile} /mnt/serialization/binaries
$ docker-compose up
```
A Mirador instance is available at 
`http://localhost:9000`

The test branch image service URI defaults to:
 * `http://localhost:5000/iiif/{package image directory}/{file name.tif}`

* If your package images are not pyramidal tifs, then run these commands in the image directory:
```bash
$ mogrify -define tiff:tile-geometry=256x256 -depth 8 -format ptif *.tif
$ rm *.tif
$ rename 's/.ptif$/.tif/' *.ptif
```
* Note: generated manifests can published to `/mnt/http-server`
```bash
./producer -i /mnt/serialization/binaries/{$your_inputfile}.xml -o /mnt/http-server/{$your_outputfile.jsonld} -t {$kitodo_archive_name} -v {package image directory}
``` 
The manifest URI will be `http://localhost:3000/{$your_outputfile.jsonld}`
