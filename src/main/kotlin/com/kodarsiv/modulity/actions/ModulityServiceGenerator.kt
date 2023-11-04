package com.kodarsiv.modulity.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.kodarsiv.modulity.dialogs.GenericGenerator
import java.awt.Dimension
import javax.swing.JLabel

class ModulityServiceGenerator: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project ?: return

        val dialog = GenericGenerator()
        dialog.fillModuleListWithFallback(project)
        dialog.minimumSize = Dimension(650, 250)
        dialog.title = "Generate Service with Modulity"

        dialog.pack()
        dialog.setLocationRelativeTo(null);
        dialog.isVisible = true;
    }

}