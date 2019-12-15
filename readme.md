[![Maven](https://img.shields.io/maven-central/v/io.github.dgroup/xlsx-matchers.svg)](https://mvnrepository.com/artifact/io.github.dgroup/xlsx-matchers)
[![Javadocs](http://www.javadoc.io/badge/io.github.dgroup/xlsx-matchers.svg)](http://www.javadoc.io/doc/io.github.dgroup/xlsx-matchers)
[![License: MIT](https://img.shields.io/github/license/mashape/apistatus.svg)](./license.txt)
[![Commit activity](https://img.shields.io/github/commit-activity/y/dgroup/xlsx-matchers.svg?style=flat-square)](https://github.com/dgroup/xlsx-matchers/graphs/commit-activity)

[![Build Status](https://circleci.com/gh/dgroup/xlsx-matchers.svg?style=svg&circle-token=a1104cecfe0dfd20fc91aa7b6a809c28b87cbbd7)](https://circleci.com/gh/dgroup/xlsx-matchers)
[![0pdd](http://www.0pdd.com/svg?name=dgroup/xlsx-matchers)](http://www.0pdd.com/p?name=dgroup/xlsx-matchers)
[![Dependency Status](https://requires.io/github/dgroup/xlsx-matchers/requirements.svg?branch=master)](https://requires.io/github/dgroup/xlsx-matchers/requirements/?branch=master)
[![Known Vulnerabilities](https://snyk.io/test/github/dgroup/xlsx-matchers/badge.svg)](https://snyk.io/org/dgroup/project/!!!!!!<TBD>!!!!!!!/?tab=dependencies&vulns=vulnerable)

[![DevOps By Rultor.com](http://www.rultor.com/b/dgroup/xlsx-matchers)](http://www.rultor.com/p/dgroup/xlsx-matchers)
[![EO badge](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org/#principles)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Qulice](https://img.shields.io/badge/qulice-passed-blue.svg)](http://www.qulice.com/)
[![SQ maintainability](https://sonarcloud.io/api/project_badges/measure?project=io.github.dgroup%3Axlsx-matchers&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=io.github.dgroup%3Axlsx-matchers)
[![Codebeat](https://codebeat.co/badges/b85595f7-67f3-4384-8e87-4d622eb5a083)](https://codebeat.co/projects/github-com-dgroup-xlsx-matchers-master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8c6c2c027935428c86a6a7302c7040eb)](https://www.codacy.com/manual/dgroup/xlsx-matchers?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dgroup/xlsx-matchers&amp;utm_campaign=Badge_Grade)
[![Codecov](https://codecov.io/gh/dgroup/xlsx-matchers/branch/master/graph/badge.svg)](https://codecov.io/gh/dgroup/xlsx-matchers)

## What it is
**xlsx-matchers** is an elegant object-oriented hamcrest matchers for Apache POI.

## Principles
[Design principles](http://www.elegantobjects.org#principles) behind xlsx-matchers.

## How to use
Get the latest version [here](https://github.com/dgroup/xlsx-matchers/releases):

```xml
<dependency>
    <groupId>io.github.dgroup</groupId>
    <artifactId>xlsx-matchers</artifactId>
    <version>${version}</version>
</dependency>
```

Java version required: 1.8+.

All examples below are using the following frameworks/libs:
 - [Hamcrest](https://github.com/hamcrest/JavaHamcrest) - Library of matchers, which can be combined in to create flexible expressions of intent in tests.
 - [cactoos](https://github.com/yegor256/cactoos) - Object-Oriented Java primitives, as an alternative to Google Guava and Apache Commons.
 - [cactoos-matchers](https://github.com/yegor256/cactoos) - Object-Oriented Hamcrest matchers

#### [HasCells](src/main/java/io/github/dgroup/xlsx/cell/HasCells.java)
Ensure that particular Apache POI row has cells with the required data:
```java
// Testing prerequisites
final XSSFRow row = new XSSFWorkbook().createSheet().createRow(1);
final XSSFCell name = row.createCell(0);
name.setCellValue("Name");
final XSSFCell birth = row.createCell(1);
birth.setCellValue("Birth");
final XSSFCell phone = row.createCell(2);
phone.setCellValue("Phone");
// Testing ...
MatcherAssert.assertThat(
    "The mather matches row with all required cells",
    row,
    new HasCells<>(
        new CellOf<>(0, "Name"),
        new CellOf<>(1, "Birth"),
        new CellOf<>(2, "Phone")
    )
);
// or in cactoos-matchers way
new Assertion<>(
    "The mather matches row with all required cells",
    row,
    new HasCells<>(
        new CellOf<>(0, "Name"),
        new CellOf<>(1, "Birth"),
        new CellOf<>(2, "Phone")
    )
).affirm();
```