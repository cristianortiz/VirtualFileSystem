package com.cortiz.filesystem

import com.cortiz.files.Directory

/**
 * Clase que guarda el estado de la aplicación, ya que es un sistema de archivos virtual, el objeto
 * state se va transformando a medida que ingresamos comandos
 *
 * @param root   : instancia Directory
 * @param wd     : directorio de trabajo actual
 * @param output : resultado de la ejecucion del comando anterior
 */

class State(val root: Directory, val wd: Directory, val output: String) {

  //metodo que imprime en la consola o shell
  def show: Unit =
    print(State.SHELL_TOKEN)

  //metodo que permite a una isntancia State configurar un mensaje, por inmutabilidad, devuelve una instancia State
  def setMessage(message:String): State =
    State(root,wd,message)// por apply() se puede crear instancia State con notacion simplificada

}

//companion object para la clase State
object State {
  val SHELL_TOKEN = "$ "

  //factory method para crear un instancia State, coun output por defecto vacio ""
  def apply(root: Directory, wd: Directory, output: String = ""):State = new State(root,wd,output)

}
