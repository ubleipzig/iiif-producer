## IIIF-Producer

A CLI tool that generates IIIF Presentation 2.1 Manifests from METS/MODS (produced by Kitodo)

## Build
`$ ./gradlew clean build`

## Test
`$ gradle test`

## Run
* find distribution archive in `producer/build/distributions`
* extract archive
* `$ cd producer-{$version}/bin`
* `$ chmod 755 producer`
* `./producer -i {$your_inputfile}.xml -o {$your_outputfile.json} -t {$kitodo_archive_name} -v {$view_identifier}`

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