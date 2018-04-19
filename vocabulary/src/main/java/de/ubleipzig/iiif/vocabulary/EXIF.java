/*
 * IIIFProducer
 * Copyright (C) 2017 Leipzig University Library <info@ub.uni-leipzig.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package de.ubleipzig.iiif.vocabulary;

import static de.ubleipzig.iiif.vocabulary.VocabUtils.createIRI;

import org.apache.commons.rdf.api.IRI;

/**
 * EXIF.
 *
 * @author christopher-johnson
 */
public final class EXIF {

    /* Namespace */
    public static final String URI = "http://www.w3.org/2003/12/exif/ns#";

    /* Classes */
    public static final IRI IFD = createIRI(URI + "IFD");

    /* Properties */
    public static final IRI _unknown = createIRI(URI + "_unknown");
    public static final IRI apertureValue = createIRI(URI + "apertureValue");
    public static final IRI artist = createIRI(URI + "artist");
    public static final IRI bitsPerSample = createIRI(URI + "bitsPerSample");
    public static final IRI brightnessValue = createIRI(URI + "brightnessValue");
    public static final IRI cfaPattern = createIRI(URI + "cfaPattern");
    public static final IRI colorSpace = createIRI(URI + "colorSpace");
    public static final IRI componentsConfiguration = createIRI(URI + "componentsConfiguration");
    public static final IRI compressedBitsPerPixel = createIRI(URI + "compressedBitsPerPixel");
    public static final IRI compression = createIRI(URI + "compression");
    public static final IRI contrast = createIRI(URI + "contrast");
    public static final IRI copyright = createIRI(URI + "copyright");
    public static final IRI customRendered = createIRI(URI + "customRendered");
    public static final IRI datatype = createIRI(URI + "datatype");
    public static final IRI date = createIRI(URI + "date");
    public static final IRI dateAndOrTime = createIRI(URI + "dateAndOrTime");
    public static final IRI dateTime = createIRI(URI + "dateTime");
    public static final IRI dateTimeDigitized = createIRI(URI + "dateTimeDigitized");
    public static final IRI dateTimeOriginal = createIRI(URI + "dateTimeOriginal");
    public static final IRI deviceSettingDescription = createIRI(URI + "deviceSettingDescription");
    public static final IRI digitalZoomRatio = createIRI(URI + "digitalZoomRatio");
    public static final IRI exif_IFD_Pointer = createIRI(URI + "exif_IFD_Pointer");
    public static final IRI exifAttribute = createIRI(URI + "exifAttribute");
    public static final IRI exifdata = createIRI(URI + "exifdata");
    public static final IRI exifVersion = createIRI(URI + "exifVersion");
    public static final IRI exposureBiasValue = createIRI(URI + "exposureBiasValue");
    public static final IRI exposureIndex = createIRI(URI + "exposureIndex");
    public static final IRI exposureMode = createIRI(URI + "exposureMode");
    public static final IRI exposureProgram = createIRI(URI + "exposureProgram");
    public static final IRI exposureTime = createIRI(URI + "exposureTime");
    public static final IRI fileSource = createIRI(URI + "fileSource");
    public static final IRI flash = createIRI(URI + "flash");
    public static final IRI flashEnergy = createIRI(URI + "flashEnergy");
    public static final IRI flashpixVersion = createIRI(URI + "flashpixVersion");
    public static final IRI fNumber = createIRI(URI + "fNumber");
    public static final IRI focalLength = createIRI(URI + "focalLength");
    public static final IRI focalLengthIn35mmFilm = createIRI(URI + "focalLengthIn35mmFilm");
    public static final IRI focalPlaneResolutionUnit = createIRI(URI + "focalPlaneResolutionUnit");
    public static final IRI focalPlaneXResolution = createIRI(URI + "focalPlaneXResolution");
    public static final IRI focalPlaneYResolution = createIRI(URI + "focalPlaneYResolution");
    public static final IRI gainControl = createIRI(URI + "gainControl");
    public static final IRI geo = createIRI(URI + "geo");
    public static final IRI gpsAltitude = createIRI(URI + "gpsAltitude");
    public static final IRI gpsAltitudeRef = createIRI(URI + "gpsAltitudeRef");
    public static final IRI gpsAreaInformation = createIRI(URI + "gpsAreaInformation");
    public static final IRI gpsDateStamp = createIRI(URI + "gpsDateStamp");
    public static final IRI gpsDestBearing = createIRI(URI + "gpsDestBearing");
    public static final IRI gpsDestBearingRef = createIRI(URI + "gpsDestBearingRef");
    public static final IRI gpsDestDistance = createIRI(URI + "gpsDestDistance");
    public static final IRI gpsDestDistanceRef = createIRI(URI + "gpsDestDistanceRef");
    public static final IRI gpsDestLatitude = createIRI(URI + "gpsDestLatitude");
    public static final IRI gpsDestLatitudeRef = createIRI(URI + "gpsDestLatitudeRef");
    public static final IRI gpsDestLongitude = createIRI(URI + "gpsDestLongitude");
    public static final IRI gpsDestLongitudeRef = createIRI(URI + "gpsDestLongitudeRef");
    public static final IRI gpsDifferential = createIRI(URI + "gpsDifferential");
    public static final IRI gpsDOP = createIRI(URI + "gpsDOP");
    public static final IRI gpsImgDirection = createIRI(URI + "gpsImgDirection");
    public static final IRI gpsImgDirectionRef = createIRI(URI + "gpsImgDirectionRef");
    public static final IRI gpsInfo = createIRI(URI + "gpsInfo");
    public static final IRI gpsInfo_IFD_Pointer = createIRI(URI + "gpsInfo_IFD_Pointer");
    public static final IRI gpsLatitude = createIRI(URI + "gpsLatitude");
    public static final IRI gpsLatitudeRef = createIRI(URI + "gpsLatitudeRef");
    public static final IRI gpsLongitude = createIRI(URI + "gpsLongitude");
    public static final IRI gpsLongitudeRef = createIRI(URI + "gpsLongitudeRef");
    public static final IRI gpsMapDatum = createIRI(URI + "gpsMapDatum");
    public static final IRI gpsMeasureMode = createIRI(URI + "gpsMeasureMode");
    public static final IRI gpsProcessingMethod = createIRI(URI + "gpsProcessingMethod");
    public static final IRI gpsSatellites = createIRI(URI + "gpsSatellites");
    public static final IRI gpsSpeed = createIRI(URI + "gpsSpeed");
    public static final IRI gpsSpeedRef = createIRI(URI + "gpsSpeedRef");
    public static final IRI gpsStatus = createIRI(URI + "gpsStatus");
    public static final IRI gpsTimeStamp = createIRI(URI + "gpsTimeStamp");
    public static final IRI gpsTrack = createIRI(URI + "gpsTrack");
    public static final IRI gpsTrackRef = createIRI(URI + "gpsTrackRef");
    public static final IRI gpsVersionID = createIRI(URI + "gpsVersionID");
    public static final IRI height = createIRI(URI + "height");
    public static final IRI ifdPointer = createIRI(URI + "ifdPointer");
    public static final IRI imageConfig = createIRI(URI + "imageConfig");
    public static final IRI imageDataCharacter = createIRI(URI + "imageDataCharacter");
    public static final IRI imageDataStruct = createIRI(URI + "imageDataStruct");
    public static final IRI imageDescription = createIRI(URI + "imageDescription");
    public static final IRI imageLength = createIRI(URI + "imageLength");
    public static final IRI imageUniqueID = createIRI(URI + "imageUniqueID");
    public static final IRI imageWidth = createIRI(URI + "imageWidth");
    public static final IRI interoperability_IFD_Pointer = createIRI(URI + "interoperability_IFD_Pointer");
    public static final IRI interoperabilityIndex = createIRI(URI + "interoperabilityIndex");
    public static final IRI interoperabilityVersion = createIRI(URI + "interoperabilityVersion");
    public static final IRI interopInfo = createIRI(URI + "interopInfo");
    public static final IRI isoSpeedRatings = createIRI(URI + "isoSpeedRatings");
    public static final IRI jpegInterchangeFormat = createIRI(URI + "jpegInterchangeFormat");
    public static final IRI jpegInterchangeFormatLength = createIRI(URI + "jpegInterchangeFormatLength");
    public static final IRI length = createIRI(URI + "length");
    public static final IRI lightSource = createIRI(URI + "lightSource");
    public static final IRI make = createIRI(URI + "make");
    public static final IRI makerNote = createIRI(URI + "makerNote");
    public static final IRI maxApertureValue = createIRI(URI + "maxApertureValue");
    public static final IRI meter = createIRI(URI + "meter");
    public static final IRI meteringMode = createIRI(URI + "meteringMode");
    public static final IRI mm = createIRI(URI + "mm");
    public static final IRI model = createIRI(URI + "model");
    public static final IRI oecf = createIRI(URI + "oecf");
    public static final IRI orientation = createIRI(URI + "orientation");
    public static final IRI photometricInterpretation = createIRI(URI + "photometricInterpretation");
    public static final IRI pictTaking = createIRI(URI + "pictTaking");
    public static final IRI pimBrightness = createIRI(URI + "pimBrightness");
    public static final IRI pimColorBalance = createIRI(URI + "pimColorBalance");
    public static final IRI pimContrast = createIRI(URI + "pimContrast");
    public static final IRI pimInfo = createIRI(URI + "pimInfo");
    public static final IRI pimSaturation = createIRI(URI + "pimSaturation");
    public static final IRI pimSharpness = createIRI(URI + "pimSharpness");
    public static final IRI pixelXDimension = createIRI(URI + "pixelXDimension");
    public static final IRI pixelYDimension = createIRI(URI + "pixelYDimension");
    public static final IRI planarConfiguration = createIRI(URI + "planarConfiguration");
    public static final IRI primaryChromaticities = createIRI(URI + "primaryChromaticities");
    public static final IRI printImageMatching_IFD_Pointer = createIRI(URI + "printImageMatching_IFD_Pointer");
    public static final IRI recOffset = createIRI(URI + "recOffset");
    public static final IRI referenceBlackWhite = createIRI(URI + "referenceBlackWhite");
    public static final IRI relatedFile = createIRI(URI + "relatedFile");
    public static final IRI relatedImageFileFormat = createIRI(URI + "relatedImageFileFormat");
    public static final IRI relatedImageLength = createIRI(URI + "relatedImageLength");
    public static final IRI relatedImageWidth = createIRI(URI + "relatedImageWidth");
    public static final IRI relatedSoundFile = createIRI(URI + "relatedSoundFile");
    public static final IRI resolution = createIRI(URI + "resolution");
    public static final IRI resolutionUnit = createIRI(URI + "resolutionUnit");
    public static final IRI rowsPerStrip = createIRI(URI + "rowsPerStrip");
    public static final IRI samplesPerPixel = createIRI(URI + "samplesPerPixel");
    public static final IRI saturation = createIRI(URI + "saturation");
    public static final IRI sceneCaptureType = createIRI(URI + "sceneCaptureType");
    public static final IRI sceneType = createIRI(URI + "sceneType");
    public static final IRI seconds = createIRI(URI + "seconds");
    public static final IRI sensingMethod = createIRI(URI + "sensingMethod");
    public static final IRI sharpness = createIRI(URI + "sharpness");
    public static final IRI shutterSpeedValue = createIRI(URI + "shutterSpeedValue");
    public static final IRI software = createIRI(URI + "software");
    public static final IRI spatialFrequencyResponse = createIRI(URI + "spatialFrequencyResponse");
    public static final IRI spectralSensitivity = createIRI(URI + "spectralSensitivity");
    public static final IRI stripByteCounts = createIRI(URI + "stripByteCounts");
    public static final IRI stripOffsets = createIRI(URI + "stripOffsets");
    public static final IRI subjectArea = createIRI(URI + "subjectArea");
    public static final IRI subjectDistance = createIRI(URI + "subjectDistance");
    public static final IRI subjectDistanceRange = createIRI(URI + "subjectDistanceRange");
    public static final IRI subjectLocation = createIRI(URI + "subjectLocation");
    public static final IRI subseconds = createIRI(URI + "subseconds");
    public static final IRI subSecTime = createIRI(URI + "subSecTime");
    public static final IRI subSecTimeDigitized = createIRI(URI + "subSecTimeDigitized");
    public static final IRI subSecTimeOriginal = createIRI(URI + "subSecTimeOriginal");
    public static final IRI tag_number = createIRI(URI + "tag_number");
    public static final IRI tagid = createIRI(URI + "tagid");
    public static final IRI transferFunction = createIRI(URI + "transferFunction");
    public static final IRI userComment = createIRI(URI + "userComment");
    public static final IRI userInfo = createIRI(URI + "userInfo");
    public static final IRI versionInfo = createIRI(URI + "versionInfo");
    public static final IRI whiteBalance = createIRI(URI + "whiteBalance");
    public static final IRI whitePoint = createIRI(URI + "whitePoint");
    public static final IRI width = createIRI(URI + "width");
    public static final IRI xResolution = createIRI(URI + "xResolution");
    public static final IRI yCbCrCoefficients = createIRI(URI + "yCbCrCoefficients");
    public static final IRI yCbCrPositioning = createIRI(URI + "yCbCrPositioning");
    public static final IRI yCbCrSubSampling = createIRI(URI + "yCbCrSubSampling");
    public static final IRI yResolution = createIRI(URI + "yResolution");


    private EXIF() {

    }
}
