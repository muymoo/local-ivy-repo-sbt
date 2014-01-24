import sbt._
import play.Project._
import Keys._
import IvyRepositories.{localDepRepo, localRepoArtifacts, makeLocalRepoSettings, localDepRepoCreated}

object ApplicationBuild extends Build {

  val appVersion = "1.6.0-SNAPSHOT"
          
  val appDependencies = Seq(
          javaCore,
          "org.apache.ws.security" % "wss4j" % "1.6.9"
  )          
  
  // So we can publish our jars to local play install
  lazy val publishSettings = Seq( 
      organization := "com.acme.test",
      publishMavenStyle := true,
      publishTo := Some(Resolver.file("file", new File("../test-repo/releases")))
      
  )
  
  // Create Local Repo  
  
  lazy val s = playJavaSettings ++ publishSettings
  lazy val publishedProjects: Seq[Project] = Seq(subModule)
  
  lazy val subModule = play.Project("sub-module", appVersion, appDependencies, settings = s, path = file("modules/sub-module"))
  
  lazy val main = play.Project("local-test", appVersion, appDependencies, settings = s).settings(
          localRepoArtifacts += "org.apache.ws.security" % "wss4j" % "1.6.9").settings(localDepRepo := new File("../test-repo/releases")).settings(makeLocalRepoSettings(publishedProjects):_*).settings(localDepRepoCreated(file("../test-repo/releases"))).aggregate(subModule)

}
