#
# INTRODUCTION
#

Teserract OCR is one of the most reliable open source image-to-txt converter text recogniton tool ever. It is an open source project and its algorithm development strategy lies beyond mid 80's. Its developed in C++ and several implementations in C# and Java is available as open source projects. The details and the source code of the project can be found on;

https://github.com/tesseract-ocr/tesseract

#
# Tess4j API
#

tess4j api is an implementation of Tesseract OCR approach in Java language. Tess4J ## Description: A Java JNA wrapper for Tesseract OCR API. Tess4J is released and distributed under the Apache License, v2.0. ## Features: The library provides optical character recognition (OCR) support for: TIFF, JPEG, GIF, PNG, and BMP image formats Multi-page TIFF images PDF document format. Below are there several tips for developers about how to use Tess4j library with Netbeans editor.

 http://tess4j.sourceforge.net/tutorial/

 #
 # FOR USER HOW TO USE
 #

 Because tess4j api is an extension of Tesseract OCR project, it uses the Tesseract pproject training data for recognising optical characters. There are training data for almost all languages. In this implementation, I used German and English texts to parse. To be able to use this executable jar application, please follow the instructions below;

Before following the instructions, make sure you set JAVA_HOME environment variable to Java installation path (1.8+, Java 12 recommendable). Also you need to add Java binary directory to PATH environment variable. If you do not know how to do, here are sone tips;

Install Java MAC : https://java.com/en/download/help/mac_install.xml?printFriendly=true

Install Java Windows : https://java.com/en/download/help/windows_manual_download.xml

Install Java Linux Ubuntu : https://askubuntu.com/questions/673633/installing-java-on-ubuntu

 1. Clone the project to your local.
    git clone https://github.com/erguzel/ocr-app-tess4j.git
