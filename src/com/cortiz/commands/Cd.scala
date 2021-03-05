package com.cortiz.commands

import com.cortiz.files.{DirEntry, Directory}
import com.cortiz.filesystem.State

import scala.annotation.tailrec

//la ide es cambiar el wd al aplicar el comando cd, si dicho wd existe
class Cd(dir: String) extends Command {
  override def apply(state: State): State = {
    /* por ahora soportara solo:
    cd absolute path -> cd /someDir/someOtherdir/etc/.../
    cd relative path -> cd a/b/c/ to the current WD
    * */
    //1. find the root
    val root = state.root
    val wd = state.wd

    //2. find the absolute path of the directory i want to cd to
    val absolutePath =
      if (dir.startsWith(Directory.SEPARATOR)) dir //si el directorio empieza con "/"
      else if (wd.isRoot) wd.path + dir //si el wd es el directorio root "/"
      else wd.path + Directory.SEPARATOR + dir //si estoy en una ruta relativa, armo el absolute path

    //3. find the destiny directory given the path,
    val destinationDirectory = doFindEntry(root, absolutePath)

    //4. change the state given the new WD directory
    if (destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(dir + ": no existe el directorio")
    else
      State(root, destinationDirectory.asDirectory)

  }

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) currentDirectory
      //si path ya no tiene mas elementos
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      //si path aun tiene elementos..
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if (nextDir == null || nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    //1. necesitamos los tokens en el path para navegar la estructura de directorios
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList

    //2. navegar a traves del path hasta el directorio destino correcta
    findEntryHelper(root, tokens)
  }

}
