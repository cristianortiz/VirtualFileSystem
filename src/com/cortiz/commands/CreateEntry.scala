/**
 * clase abstracta para crear instancias de tipo Directory y de tipo File, cada una de estas implementara luego el metodo especifico
 * para crear un directorio o un archivo dentro de la estructura de directorios de la app
 */
package com.cortiz.commands

import com.cortiz.files.{DirEntry, Directory}
import com.cortiz.filesystem.State

abstract class CreateEntry (name:String) extends Command {

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry):Directory = {
    //si path es empty signfica que se crea el nuevo directorio o archivo en el wd o directori actual y no es necesario vavegar toda la estrutuctura de directorios
    if(path.isEmpty) currentDirectory.addEntry(newEntry)

    //si new entry se creara en una ubicacion distinta al wd, necesito recorrer la estructura de directorios partiendo del wd
    else{
      //path es una lista y pedimos su head porque ahi estara el primer folder de toda estructura de directorios (/a),
      // partiendo del wd, asDirectory convierte la nueva entrada a directory por si es un file
      val oldEntry = currentDirectory.findEntry(path.head).asDirectory
      //reemplaza la actual estructura de directorios con nueva instancia creada recursivamente por updateStructure, que contiene el nuevo dir/file
      currentDirectory.replaceEntry(oldEntry.name,updateStructure(oldEntry,path.tail,newEntry))

    }
  }


  //crea un directorio o archivo
  def doCreateEntry(state: State, name: String): State = {
    val wd = state.wd

    /* navegar desde el directori raiz hasta la ruta completa del directorio a crear y obtener la instancia directorio donde
     se necesita crear el directorio*/

    //1. listar todos los directorios de la ruta completa
    val allDirsInPath = wd.getAllFoldersInPath

    //2. crear el nuevo directorio o archivo en working directory actual, llamando al metodo especifico de la clase mkdir o touch
    //val newDir = Directory.empty(wd.path,name)
    val newEntry: DirEntry = createSpecificEntry(state)

    //3. actualizar toda la estructura de directorios partiendo del directorio raiz (la estructura de directorios es inmutable)
    //  implica volver  crearla ejecutar comandos como crear, eliminar o actualizar nuevos elementos(directorios y archivos) de ella
    val newRoot = updateStructure(state.root,allDirsInPath,newEntry)

    //4. encontrar y guardar instancia del nuevo WD recorriendo la nueva estructura de directorios
    val newWd = newRoot.findDescendant(allDirsInPath)
    State(newRoot,newWd)



  }
  def createSpecificEntry(state:State): DirEntry

  override def apply(state: State): State = {
    //recibir el working directory desde el estado de la app
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Entrada " + name + " ya existe!")
    }
    else if (name.contains(Directory.SEPARATOR)) {
      //por ahora bloqueamos directorios anidados mkdir dir/subdir
      state.setMessage(name + " no debe contener separadores")
    } else if (checkIllegal(name)) {
      state.setMessage(name + ": identificador no valido!")
    } else {
      //si pasa todas las verifiaciones anteriores se crea el directorio con el metodo doMkdir
      doCreateEntry(state, name)
      // Command.from("ls").apply(state)
    }
  }

}
