inThisBuild(Seq(
  name := "scala-diff-utils",
  version := "1.0.2-SNAPSHOT",

  scalaVersion := "2.12.6"
))

name := (ThisBuild / name).value

import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

lazy val root = crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure).in(file("."))
  .settings(
    name := (ThisBuild / name).value
  )
