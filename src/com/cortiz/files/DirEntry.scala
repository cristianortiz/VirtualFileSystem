package com.cortiz.files

/**
 * Clase abstracta para creacion de directorios,
 *
 * @param parentPath : ruta del directorio padre
 * @param name       : nombre del directorio a crear
 * */

abstract class DirEntry(val parentPath: String, val name: String) {
  //ruta absoluta de un directorio
  def path: String = parentPath + Directory.SEPARATOR + name

  def asDirectory: Directory
  def getType: String

}
