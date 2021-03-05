package com.cortiz.commands

import com.cortiz.files.DirEntry
import com.cortiz.files.File
import com.cortiz.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path, name)

}
