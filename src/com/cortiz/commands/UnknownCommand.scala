package com.cortiz.commands

import com.cortiz.filesystem.State

/**
 * clase que define un comando desconocido, fuera de al lista de los comandos validos
 * */
class UnknownCommand extends Commands {
  override def apply(state:State): State =

}
