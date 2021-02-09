name := "optics"

version := "0.1"

scalaVersion := "2.13.4"

//according to https://www.optics.dev/Monocle/
libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %% "monocle-core"  % "2.0.3",
  "com.github.julien-truffaut" %% "monocle-macro" % "2.0.3",
)

////TODO comment
//scalacOptions in Global += "-Ymacro-annotations"