/**Clase que inicializa los estados de la app y recibe los commandos
 * @param state: importante este se declara com VAR, porque necesita actualizarse  a medida que
 *               recibe comandos, solo por esta app en particular
 * */
package com.cortiz.filesystem

import com.cortiz.commands.Command
import com.cortiz.files.Directory

import java.util.Scanner
//entry point de la app
object FileSystem extends App{
  val root = Directory.ROOT
  //estado inicial VARIABLE, por defecto con root como dir raiz y dir actual, llama a State.apply() con output "" por defecto
  var state = State(root,root)

  val scanner = new Scanner(System.in)
  //muestra simbolo prompt $ en bucle  y permite ingresar comandos
  while (true){
    state.show
    val input = scanner.nextLine()//user input en la app
    state = Command.from(input).apply(state)
    /* a partir del texto ingresado Command.from() crea UnknownCommand (por ahora), el apply de este escribe
    "comando no encontrado" y llama a setMesage() que lo convierte en el output del estado anterior finalmente
    apply(state) recibe la instancia state anterior y crea una nueva instancia con el nuevo output, se vuelve al inicio del blucle
    y state.show() muestra el output del comando ingresado
      */
  }

}
