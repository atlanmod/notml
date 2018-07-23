![CyprIoT Logo](logo.png)

A Domain-Specific language (DSL) to design network of things easily.

[![Build Status](https://travis-ci.org/atlanmod/CyprIoT.svg?branch=master)](https://travis-ci.org/atlanmod/CyprIoT)

## Prerequisites
* Xtext 2.14+
* EMF
* Maven
* Eclipse Oxygen or newer

## Installation

The project consists of the following artifacts : 

* A Code syntax, formalized [here](https://github.com/atlanmod/CyprIoT/tree/master/org.atlanmod.cypriot/src/org/atlanmod/cypriot)
* Code generators, available in generators package

How to get started :

* Run ``mvn clean install``
* Using Eclipse Right-Click on ``org.atlanmod.cypriot`` package
* Hit Run As -> Eclipse Application
* Enjoy network modeling !

## Usage

The DSL can be used to model a network of heterogeneous devices. Device modeling has to be done using [ThingML](https://github.com/TelluIoT/ThingML). The configuration should have an external port. The latter will be overriden by the network configuration.

## Contributors

* imberium