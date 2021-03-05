package com.cortiz.files

/**
 * Clase abstracta para creacion de directorios,
 *
 * @param parentPath : ruta del directorio padre
 * @param name       : nombre del directorio a crear
 * */

abstract class DirEntry(val parentPath: String, val name: String) {
  //ruta absoluta de un directorio
  def path: String = {
    val separatorIfNecesary =
      if (Directory.ROOT_PATH.equals(parentPath)) ""
      else Directory.SEPARATOR
    parentPath + separatorIfNecesary + name
  }

  def asDirectory: Directory

  def asFile: File

  def getType: String

  def isDirectory: Boolean

  def isFile: Boolean

}
