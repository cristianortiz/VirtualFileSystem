package com.cortiz.commands

import com.cortiz.filesystem.State

/**
 * clase que define un comando desconocido, fuera de al lista de los comandos validos
 * */
class UnknownCommand extends Command {
  //mensaje que indica que el comando no existe
  override def apply(state:State): State =
    state.setMessage("Comando no encontrado!")

}
