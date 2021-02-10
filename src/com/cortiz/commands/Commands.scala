package com.cortiz.commands

import com.cortiz.filesystem.State

/**
 * Trait que transforma el estado actual de la app en otro al ejecutarse comandos (crear, eliminar, directorios files, etc)
 * */
trait Commands {

  //metodo que transforma un estado de la app en otro
  def apply(state:State): State


}
