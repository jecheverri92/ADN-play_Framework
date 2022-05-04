lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := """comparendos""",
    organization := "co.com.ceiba",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      guice,
      javaJpa,
      jdbc,
      javaJdbc,
      evolutions,
      "mysql"                             % "mysql-connector-java"             % "8.0.28",
      "org.hibernate"                     % "hibernate-core"                   % "5.5.6",
      "io.vavr"                           % "vavr-jackson"                     % "0.10.3",
      "io.vavr"                           % "vavr"                             % "0.10.4",
      "net.jodah"                         % "failsafe"                         % "1.0.3",
      "org.projectlombok"                 % "lombok"                           % "1.18.24",
      "org.apache.commons"                % "commons-lang3"                    % "3.12.0",
      "org.jdbi"                          % "jdbi"                             % "2.78",
      "com.github.javafaker"              % "javafaker"                        % "1.0.2",
      "com.fasterxml.jackson.module"      % "jackson-module-parameter-names"   % "2.12.0",
      "com.fasterxml.jackson.datatype"    % "jackson-datatype-jdk8"            % "2.12.0",
      "com.fasterxml.jackson.datatype"    % "jackson-datatype-jsr310"          % "2.12.0",
      "com.fasterxml.jackson.module"      %% "jackson-module-scala"            % "2.12.0"
    ),
  )
