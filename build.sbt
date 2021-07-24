name := "optics"

version := "0.1"

scalaVersion := "2.13.4"

val monocleVersion = "2.1.0"

//according to https://www.optics.dev/Monocle/
libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %% "monocle-core"  % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
)

// `-Ymacro-annotations` is required only when using annotation such as `@Lenses`
scalacOptions in Global += "-Ymacro-annotations"