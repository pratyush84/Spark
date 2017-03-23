name := "ElectionAnalysis"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Hortonworks Releases" at "http://repo.hortonworks.com/content/repositories/releases/"
resolvers +=  "Hortonworks PublicRepo" at "http://repo.hortonworks.com/content/groups/public/"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.0.2.5.0.0-1245"

libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.0.0.2.5.0.0-1245"

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("com", "google", xs @ _*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
  case "META-INF/mailcap" => MergeStrategy.last
  case "META-INF/mimetypes.default" => MergeStrategy.last
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}