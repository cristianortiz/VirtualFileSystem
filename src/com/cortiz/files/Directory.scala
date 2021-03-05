package com.cortiz.files

import com.cortiz.filesystem.FileSystemException

import scala.annotation.tailrec

/**
 * Clase para la creacion de directorios, extiende la clase abstracta DirEntry
 *
 * @param contents : lista de tipo DirEntry del contenido del directorio
 * */
class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

  // por inmutabilidad, reemplaza la estructura de archivos con una nueva instancia Directory con el nuevo elemento (dir/file) agregado
  def replaceEntry(entryName: String, newEntry: DirEntry): Directory =
   /*devuelve la nueva estructrua de directorios, usa funcion anonima para verificar que no hay ningun elemento de la lista contents con el mismo
   nombre que el nuevo elemento a agregar, luego se agrega el nuevo elemento a la nueva estructura de directorios*/
    new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)) :+ newEntry)

  def isRoot: Boolean = parentPath.isEmpty

  def findEntry(entryName: String): DirEntry = {
    @tailrec
    def findEntryHelper(name: String, contentList: List[DirEntry]): DirEntry =
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)

    findEntryHelper(entryName, contents)

  }


  //servira para folders y archivos
  def addEntry(newEntry: DirEntry): Directory = {
    //devuelve un nuevo objeto directory con el mismo nombre y path absoluto original, pero en la lista contents se agrefa el nuevo directorio o archivo
    new Directory(parentPath, name, contents :+ newEntry)
  }


  def findDescendant(path: List[String]): Directory =
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendant(path.tail)

  def getAllFoldersInPath: List[String] =
  //si la ruta es /a/b/c => List["a","b","c]
    path.substring(1).split(Directory.SEPARATOR).toList.filter(x => !x.isEmpty)

  def hasEntry(name: String): Boolean =
    findEntry(name) != null

  def asDirectory: Directory = this
  def asFile: File = throw new FileSystemException("un directorio no se pude convertir en archivo!")

  def isDirectory: Boolean = true
  def isFile: Boolean = false

  override def getType: String = "Directory"
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