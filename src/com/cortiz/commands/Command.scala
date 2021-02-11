package com.cortiz.commands

import com.cortiz.filesystem.State

/**
 * Trait implementa comandos en la shell que van modificando el estado actual de la app al
 * ejecutarse (crear, eliminar, directorios files, etc)
 * */
trait Command {

  //metodo que transforma un estado de la app en otro
  def apply(state:State): State


}

object Command{
  //factory method que recibe un string y crea una instancia Commando, por ahora creamos un comando desconocido
  def  from(input: String): Command = new UnknownCommand
}
