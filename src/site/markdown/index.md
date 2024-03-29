## About AEM Sling Models Extension

AEM Object Injector for Sling Models.

[![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.sling.models)](https://repo1.maven.org/maven2/io/wcm/io.wcm.sling.models/)


### Documentation

* [Usage][usage]
* [API documentation][apidocs]
* [Changelog][changelog]


### Overview

The AEM Object Injector for Sling Models provides:

* Access to PageManager, Page and TagManager objects
* Current WCM mode and Authoring UI mode
* Current Designer, Design and Style objects
* XSSAPI for current request
* CQ I18n object for the current request
* Granite Workflow Session
* Support injection request-derived context objects on all models, not only when the adaptable is a request

See [Injector API documentation][apidocs-aemobjectinjector] for details.


### AEM Version Support Matrix

|wcm.io AEM Sling Models Extensions version |AEM version supported
|-------------------------------------------|----------------------
|1.7.x or higher                            |AEM 6.5.17+, AEMaaCS
|1.6.x                                      |AEM 6.4+, AEMaaCS
|1.5.x                                      |AEM 6.2+
|1.0.x - 1.4.x                              |AEM 6.1+
|0.x                                        |AEM 6.0+


### Dependencies

To use this module you have to deploy also:

|---|---|---|
| [wcm.io Sling Commons](https://repo1.maven.org/maven2/io/wcm/io.wcm.sling.commons/) | [![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.sling.commons)](https://repo1.maven.org/maven2/io/wcm/io.wcm.sling.commons/) |


### Comparison to ACS Commons

The [ACS AEM Commons][acs-commons] project also contains an implementation of an [AEM Object Injector][acs-commons-aem-object-injrecotr].
It has basically the same feature-set as the wcm.io version, but it uses name-based injection based on field names.

The wcm.io AEM Object injector use class-based injection, making it easier to use independently from the field name. Since version 1.1 it supports more features than the ACS AEM Commons version.


### GitHub Repository

Sources: https://github.com/wcm-io/io.wcm.sling.models


[usage]: usage.html
[apidocs]: apidocs/
[apidocs-aemobjectinjector]: apidocs/io/wcm/sling/models/annotations/AemObject.html
[changelog]: changes-report.html
[acs-commons]: http://adobe-consulting-services.github.io/acs-aem-commons/
[acs-commons-aem-object-injrecotr]: http://adobe-consulting-services.github.io/acs-aem-commons/features/aem-sling-models-injectors.html
