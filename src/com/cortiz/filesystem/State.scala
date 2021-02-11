package com.cortiz.filesystem

import com.cortiz.files.Directory

/**
 * Clase que guarda el estado de la aplicación, ya que es un sistema de archivos virtual, ls instancia
 * state se va transformando a medida que ingresamos comandos
 *
 * @param root   : instancia Directory
 * @param wd     : directorio de trabajo actual
 * @param output : resultado de la ejecucion del comando anterior
 */

class State(val root: Directory, val wd: Directory, val output: String) {

  //metodo que imprime en la consola el output y luego el simbolo del shell
  def show: Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  //metodo  que recibe un string  lo asigna al output de la instancia State, por inmutabilidad, devuelve una instancia State
  def setMessage(message:String): State =
    State(root,wd,message)// por apply() se puede crear instancia State con notacion simplificada

}

//companion object para la clase State
object State {
  val SHELL_TOKEN = "$ "

  //factory method para crear un instancia State, con output por defecto vacio ""
  def apply(root: Directory, wd: Directory, output: String = ""):State = new State(root,wd,output)

}
