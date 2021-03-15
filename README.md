# scala-diff-utils
[![Test Workflow](https://github.com/LolHens/scala-diff-utils/workflows/test/badge.svg)](https://github.com/LolHens/scala-diff-utils/actions?query=workflow%3Atest)
[![Release Notes](https://img.shields.io/github/release/LolHens/scala-diff-utils.svg?maxAge=3600)](https://github.com/LolHens/scala-diff-utils/releases/latest)
[![Maven Central](https://img.shields.io/maven-central/v/de.lolhens/scala-diff-utils_2.13)](https://search.maven.org/artifact/de.lolhens/scala-diff-utils_2.13)
[![Apache License 2.0](https://img.shields.io/github/license/LolHens/scala-diff-utils.svg?maxAge=3600)](https://www.apache.org/licenses/LICENSE-2.0)

This is a scala fork of [java-diff-utils/java-diff-utils](https://github.com/java-diff-utils/java-diff-utils).

Diff Utils library is an OpenSource library for performing the comparison operations between texts: computing diffs, applying patches, generating unified diffs or parsing them, generating diff output for easy future displaying (like side-by-side view) and so on.

Main reason to build this library was the lack of easy-to-use libraries with all the usual stuff you need while working with diff files. Originally it was inspired by JRCS library and it's nice design of diff module.

## Usage
### build.sbt
```sbt
// use this snippet for the JVM
libraryDependencies += "de.lolhens" %% "scala-diff-utils" % "1.1.1"

// use this snippet for JS, or cross-building
libraryDependencies += "de.lolhens" %%% "scala-diff-utils" % "1.1.1"
```

## License
This project uses the Apache 2.0 License. See the file called LICENSE.
