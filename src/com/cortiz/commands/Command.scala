package com.cortiz.commands

import com.cortiz.filesystem.State
/**
 * Trait implementa comandos en la shell que van modificando el estado actual de la app al
 * ejecutarse (crear, eliminar directorios ,files, etc)
 * */
trait Command {
  //metodo que transforma un estado de la app en otro
  def apply(state:State): State

}

object Command{

  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"
  val TOUCH = "touch"
  val CD = "cd"

  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }
  def incompleteCommand(name:String): Command = new Command {
    override def apply(state: State): State =
      state.setMessage(name + " comando incompleto!")
  }
  //factory method que recibe un string y crea una instancia Command
  def  from(input: String): Command = {
    //separa el comando de sus argumentos y forma un array de Strings
    val tokens: Array[String] = input.split(" ")

    if(input.isEmpty || tokens.isEmpty) emptyCommand
    else if (MKDIR.equals(tokens(0))){ //si mkadir es el primer string del array
      if(tokens.length < 2) incompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    } else if(LS.equals(tokens(0))){
      new Ls
    }else if(PWD.equals(tokens(0))){
      new Pwd
    }else if(TOUCH.equals(tokens(0))){
    new Touch(tokens(1))
    }else if(CD.equals(tokens(0))){
      if(tokens.length < 2) incompleteCommand(CD)
      else new Cd(tokens(1))
  }
    else new UnknownCommand


  }
}
