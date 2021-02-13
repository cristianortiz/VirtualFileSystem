package com.cortiz.commands

import com.cortiz.files.Directory
import com.cortiz.filesystem.State

class Mkdir(name: String) extends Command {

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: Directory):Directory = {
    if(path.isEmpty) currentDirectory.addEntry(newEntry)
    else{
      //path es una lista y pedimos su head porque ahi estara el primer folder de toda estructura de directorios (/a),
      // partiendo del path actual, asDirectory convierte la nueva entrada a directory por si es un file
      val oldEntry = currentDirectory.findEntry(path.head).asDirectory
      //reemplaza la actual estructura de directorios con nueva instancia creada recursivamente por updateStructure, que contiene el nuevo dir/file
      currentDirectory.replaceEntry(oldEntry.name,updateStructure(oldEntry,path.tail,newEntry))

    }
  }


  //crea un directorio
  def doMkdir(state: State, name: String): State = {
    val wd = state.wd

   /* navegar desde el directori raiz hasta la suta completa del directorio a crear y obtener la instancia directorio donde
    se necesita crear el directorio*/

    //1. listar todos los directorios de la ruta completa
    val allDirsInPath = wd.getAllFoldersInPath

    //2. crear el nuevo directorio en working directory actual
    val newDir = Directory.empty(wd.path,name)

    //3. actualizar toda la estructura de directorios partiendo del directorio raiz (la estructura de directorios es inmutable)
    //  y debe ser creada nuevamente al crear, eliminar o actualizar nuevos elementos de ella
    val newRoot = updateStructure(state.root,allDirsInPath,newDir)

    //4. encontrar un nuevo working directory INSTANCE dado wd's full path en la nueva estructura de directorios
    val newWd = newRoot.findDescendant(allDirsInPath)
    State(newRoot,newWd)



  }

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
      //si pasa todas las verifiaciones anteriores se crea el directorio
      doMkdir(state, name)
    }
  }

}

/* def updateStructure-> funcionara de forma recursiva recreando la estructura de directorios anterior para crear una nueva instancia
  Directory con el nuevo directorio que se le agrega
directorio dir
                   /a
                   /b
                   -> new(dir) /c, -> se reutiliza toda la estrucura anterior en una nueva instancia Directory con el mismo nombre y path absoluto
       => new instance dir
                       /a
                       /b
                       /c -> nuevo directorio agregado en una nueva instancia Directory con el mismo nombre dir y el mismo path absoluto

   /* /a/b
           /c
           (new entry) si estoy en /a->currentDirectory y path =["b"] se llama recursivamente a updateStructure


       */*/