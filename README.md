# XMLParser [![version](https://img.shields.io/badge/version-1.0.1-green.svg)](https://semver.org)

## How to use

Include the standalone .jar in your project folder and you are ready to go. With

`XMLNode node = XMLParser.parse(file)` 

you can parse a given file into the XML-Tree data structure. Via the accessor methods you can readout the data you are interested in. Note that during parsing, an exception can be thrown if the given input is null or the given XML-Input is malformed.

## Changelog

### Version 1.0.1

- Added ability to parse xml comments