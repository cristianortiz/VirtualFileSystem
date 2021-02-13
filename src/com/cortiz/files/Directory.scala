package com.cortiz.files

/**
 * Clase para la creacion de directorios, extiende la clase abstracta DirEntry
 *
 * @param contents : lista de tipo DirEntry del contenido del directorio
 * */
class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {
  //reemplaza la estructura de archivos con una nueva instancia Directory con el nuevo elemento (dir/file) agregado
  def replaceEntry(entryName: String, newEntry: DirEntry): Directory = ???


  def findEntry(entryName: String):DirEntry = ???


  //servira para folders y archivos
  def addEntry(newEntry: DirEntry): Directory = ???


  def findDescendant(path: List[String]): Directory = ???

  def getAllFoldersInPath: List[String] =
  //si la ruta es /a/b/c => List["a","b","c]
    path.substring(1).split(Directory.SEPARATOR).toList

  def hasEntry(name: String): Boolean = ???

  def asDirectory: Directory = this
}

//object companion para la clase Directory
object Directory {
  val SEPARATOR = "/" //token separador en el PATH de directorios
  val ROOT_PATH = "/" //token raiz del arbol de directorios

  //metodo utilitario para crear un directorio raiz en el sistema de archivos (rin ruta padre ni nombre es decir empty)
  def ROOT: Directory = Directory.empty("", "")

  //crea un directorio vacio
  def empty(parentPath: String, name: String): Directory = new Directory(parentPath, name, List())

}