package com.cortiz.filesystem

import java.util.Scanner

object FileSystem extends App{

  val scanner = new Scanner(System.in)
  //muestra simbolo prompt $ en bucle  y permite ingresar comandos
  while (true){
    print("$ ")
    println(scanner.nextLine())
  }

}
