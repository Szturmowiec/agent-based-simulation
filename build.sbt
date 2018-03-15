cancelable in Global := true

val Version = new {
  val Akka = "2.5.11"
  val AkkaKryo = "0.5.2"
  val Logback = "1.2.3"
  val Guava = "23.0"
  val AvsCommons = "1.25.9"
  val ScalaTest = "3.0.5"
  val Mockito = "2.16.0"
  val ScalaLogging = "3.8.0"
  val Ficus = "1.4.3"
  val ScalaSwing = "2.0.2"
  val JFreeChart = "1.5.0"
}

inThisBuild(Seq(
  organization := "pl.edu.agh",
  version := "1.1-SNAPSHOT",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-language:implicitConversions",
    "-language:existentials",
    "-language:dynamics",
    "-language:experimental.macros",
    "-language:higherKinds",
    "-Xfuture",
    "-Xfatal-warnings",
    "-Xlint:_,-missing-interpolator,-adapted-args"
  ),
))

lazy val xinuk = project.in(file("."))
  .aggregate(`xinuk-core`, formin, fortwist, torch)
  .disablePlugins(AssemblyPlugin)

lazy val `xinuk-core` = project
  .settings(
    name := "xinuk-core",
    libraryDependencies ++= Seq(
      "com.avsystem.commons" %% "commons-core" % Version.AvsCommons,
      "com.github.romix.akka" %% "akka-kryo-serialization" % Version.AkkaKryo,
      "com.iheart" %% "ficus" % Version.Ficus,
      "com.typesafe.akka" %% "akka-actor" % Version.Akka,
      "com.typesafe.akka" %% "akka-slf4j" % Version.Akka,
      "com.typesafe.akka" %% "akka-cluster" % Version.Akka,
      "com.typesafe.akka" %% "akka-cluster-sharding" % Version.Akka,
      "com.typesafe.scala-logging" %% "scala-logging" % Version.ScalaLogging,
      "org.scala-lang.modules" %% "scala-swing" % Version.ScalaSwing,
      "org.jfree" % "jfreechart" % Version.JFreeChart,
      "org.scalatest" %% "scalatest" % Version.ScalaTest % Test,
      "com.typesafe.akka" %% "akka-testkit" % Version.Akka % Test,
      "org.mockito" % "mockito-core" % Version.Mockito % Test,
    ),
  ).disablePlugins(AssemblyPlugin)

def modelProject(projectName: String)(mainClassName: String): Project = {
  Project(projectName, file(projectName))
    .settings(
      name := projectName,
      libraryDependencies ++= Seq(
        "ch.qos.logback" % "logback-classic" % Version.Logback,
        "com.iheart" %% "ficus" % Version.Ficus,
        "org.scalatest" %% "scalatest" % Version.ScalaTest % Test,
        "com.typesafe.akka" %% "akka-testkit" % Version.Akka % Test,
        "org.mockito" % "mockito-core" % Version.Mockito % Test,
      ),
      mainClass in assembly := Some(mainClassName),
      assemblyJarName in assembly := s"$projectName.jar",
      test in assembly := {},
    ).dependsOn(`xinuk-core`)
}

lazy val formin = modelProject("formin")("pl.edu.agh.formin.Main")
lazy val fortwist = modelProject("fortwist")("pl.edu.agh.fortwist.Main")
lazy val torch = modelProject("torch")("pl.edu.agh.torch.Main")