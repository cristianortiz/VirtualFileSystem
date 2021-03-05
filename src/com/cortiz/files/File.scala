package com.cortiz.files

import java.nio.file.FileSystemException

class File(override val parentPath: String, override val name: String, contents: String) extends DirEntry(parentPath, name) {

  def asFile: File = this

  def asDirectory: Directory =
    throw new FileSystemException("Un archivo no se puede convertir en directorio")

  def getType: String = "File"

  def isDirectory: Boolean = false
  def isFile: Boolean = true


}

//objeto companion para la clase File
object File {
 //crea instancia File vacia
  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")
}