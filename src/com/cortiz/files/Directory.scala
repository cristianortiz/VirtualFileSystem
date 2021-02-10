package com.cortiz.files
/**
 * Clase para la creacion de directorios, extiende la clase abstracta DirEntry
 * @param contents: lista de tipo DirEntry del contenido del directorio
 * */
class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

}

//object companion para la clase Directory
object Directory {
  val SEPARATOR = "/" //token separador en el PATH de directorios
  val ROOT_PATH = "/" //token raiz del arbol de directorios

  //metodo utilitario para crear un directorio raiz en el sistema de archivos (rin ruta padre ni nombre es decir empty)
  def ROOT: Directory = Directory.empty("","")
  //crea un directorio vacio
  def empty(parentPath: String, name: String): Directory = new Directory(parentPath,name,List())

}