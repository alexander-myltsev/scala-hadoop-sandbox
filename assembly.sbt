import AssemblyKeys._

assemblySettings

jarName in assembly <<= (name, version) { (name, version) => name + "-" + version + ".jar" }

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  val excludes = Set(
    "scala-compiler.jar",
    "ant-1.6.5.jar",
    "jsp-api-2.1-6.1.14.jar",
    "jsp-2.1-6.1.14.jar",
    "stax-api-1.0.1.jar",
    "stax-api-1.0-2.jar",
    "jasper-compiler-5.5.12.jar",
    "minlog-1.2.jar", // Otherwise causes conflicts with Kyro (which bundles it)
    "janino-2.5.16.jar", // Janino includes a broken signature, and is not needed anyway
    "commons-beanutils-core-1.8.0.jar", // Clash with each other and with commons-collections
    "commons-beanutils-1.7.0.jar",
    "hadoop-core-1.1.2.jar",
    "asm-4.0.jar"
  )
  cp filter { jar => excludes(jar.data.getName) }
}