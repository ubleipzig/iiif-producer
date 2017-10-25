## IIIF-Producer

[![Build Status](https://travis-ci.org/ubleipzig/iiif-producer.png?branch=master)](https://travis-ci.org/ubleipzig/iiif-producer)

A CLI tool that generates IIIF Presentation 2.1 Manifests from METS/MODS (produced by Kitodo)

## Build
`$ ./gradlew clean build`

## Test
`$ gradle test`

## Run
* find distribution archive in `producer/build/distributions`
* extract archive
```bash
$ cd producer-{$version}/bin
$ chmod +x producer
./producer -i {$your_inputfile}.xml -o {$your_outputfile.json} -t {$kitodo_archive_name} -v {$view_identifier}
```

## Configuration Dependencies
* the Image Service Identifier assignment may have to be reconfigured for your infrastructure. (see Constants)
* the source images must be TIFs
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
$ git checkout test
$ cp {package image directory} /mnt/serialization/binaries/
$ cp {package metadatafile} /mnt/serialization/binaries
$ docker-compose up
```

The test branch image service URI defaults to:
 * `http://localhost:5000/iiif/{package image directory}/{file name.tif}`

* If your package images are not pyramidal tifs, then run these commands in the image directory:
```bash
$ mogrify -define tiff:tile-geometry=256x256 -depth 8 -format ptif *.tif
$ rm *.tif
$ rename 's/.ptif$/.tif/' *.ptif
```
* Note: generated manifests need to output to a local HTTP server directory.
```bash
./producer -i /mnt/serialization/binaries/{$your_inputfile}.xml -o /home/username/IdeaProjects/manifest-service/public/{$your_outputfile.json} -t {$kitodo_archive_name} -v {package image directory}
``` 
* A quick HTTP manifest solution is to checkout the `https://github.com/pan-dora/manifest-service.git`
```bash
$ npm install
$ npm start
```
The manifest URI will be `http://localhost:3000/{$your_outputfile.json}`
 