import sbt._
import play.Project._
import Keys._
import IvyRepositories.{localRepoArtifacts, makeLocalRepoSettings}

object ApplicationBuild extends Build {

  val appVersion = "1.6.0-SNAPSHOT"
          
  val appDependencies = Seq(
          javaCore,
          "org.apache.ws.security" % "wss4j" % "1.6.9"
  )          
  
  // So we can publish our jars to local play install
  lazy val publishSettings = Seq( 
      organization := "com.ge.dsv",
      publishMavenStyle := true,
      publishTo := Some(Resolver.file("file", new File("../test-repo/releases")))
      
  )
  
  // Create Local Repo  
  
  lazy val s = playJavaSettings ++ publishSettings
  //lazy val publishedProjects = Seq(main)
  
  lazy val main: Project = ( play.Project("predix", appVersion, appDependencies, settings = s).settings(
   //   localRepoArtifacts += "org.apache.ws.security" % "wss4j" % "1.6.9"//,
    //  makeLocalRepoSettings(publishedProjects)
      )
  )
          
}
