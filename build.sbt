import java.nio.file.Files

name := "Screeps"

version := "1.0"

scalaVersion := "2.12.3"

lazy val copyJS = TaskKey[Unit]("copyJS")

lazy val screepslib = project.
  enablePlugins(ScalaJSPlugin).
  settings(
    scalaJSLinkerConfig ~= { _.withOptimizer(false) },
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1",
      "com.lihaoyi" %%% "utest" % "0.4.5" % "test"
    ),
    copyJS := {
      val outPath = target.value / "scala-" + scalaBinaryVersion.value
      new File(outPath, "").listFiles().
        filter(_.toString.endsWith("-opt.js")).
        foreach(file => Files.copy(file.toPath, new File((baseDirectory.value / "export").toString, "screeps.js").toPath))
    },
    publish := {
      //TODO: Figure out how to get git in here
    }
  )
