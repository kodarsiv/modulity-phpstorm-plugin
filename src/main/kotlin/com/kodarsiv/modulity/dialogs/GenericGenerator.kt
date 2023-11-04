package com.kodarsiv.modulity.dialogs



import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.LocalFileSystem
import com.kodarsiv.modulity.commands.GenerateService
import java.awt.event.KeyEvent
import javax.swing.*


class GenericGenerator(project: Project) : JDialog() {
    private var contentPane: JPanel? = null
    private var buttonOK: JButton? = null
    private var buttonCancel: JButton? = null
    private var descriptionLabel: JLabel? = null
    private var moduleList: JComboBox<String>? = null
    var fileNameLabel: JLabel? = null;
    private var fileNameTextField: JTextField? = null;

    init {
        setContentPane(contentPane)
        isModal = true
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        rootPane.defaultButton = buttonOK

        fileNameLabel = JLabel("Please Give a Name to Service:");


        fileNameTextField?.document?.addDocumentListener(object : javax.swing.event.DocumentListener {
            override fun insertUpdate(e: javax.swing.event.DocumentEvent?) {
                validateInput()
            }

            override fun removeUpdate(e: javax.swing.event.DocumentEvent?) {
                validateInput()
            }

            override fun changedUpdate(e: javax.swing.event.DocumentEvent?) {
                validateInput()
            }
        })


        buttonOK?.addActionListener { onOK(project) }
        buttonCancel?.addActionListener { onCancel() }
        addWindowListener(object : java.awt.event.WindowAdapter() {
            override fun windowClosing(e: java.awt.event.WindowEvent) {
                onCancel()
            }
        })

        contentPane?.registerKeyboardAction(
            { onCancel() },
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        )
    }

    fun fillModuleListWithFallback(project: Project) {
        val baseDir = project.guessProjectDir()

        val modulityDir = baseDir?.findChild("Modules")
            ?: project.basePath?.let { basePath ->
                LocalFileSystem.getInstance().findFileByPath("$basePath/app/Modules")
            }


        val moduleDirs = modulityDir?.children?.filter { it.isDirectory }
        val modules = moduleDirs?.map { it.name } ?: listOf()
        moduleList?.model = DefaultComboBoxModel(modules.toTypedArray())
    }

    private fun validateInput() {
        val serviceName = fileNameTextField?.text
        buttonOK?.isEnabled = !serviceName.isNullOrBlank()
    }



    private fun onOK(project:Project) {
        val moduleName = moduleList?.selectedItem as? String
        val serviceName = fileNameTextField?.text

        if (serviceName.isNullOrBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter a service name.", "Validation Error", JOptionPane.ERROR_MESSAGE)
        } else if (moduleName.isNullOrBlank()) {
            JOptionPane.showMessageDialog(this, "Please select a module.", "Validation Error", JOptionPane.ERROR_MESSAGE)
        } else {
            try {
                val generateService = GenerateService()
                generateService.generateService(project, moduleName, serviceName)
                JOptionPane.showMessageDialog(this, "Service generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE)
                dispose()
            } catch (e: IllegalStateException) {
                JOptionPane.showMessageDialog(this, e.message, "Error", JOptionPane.ERROR_MESSAGE)
            }
        }
    }



    private fun onCancel() {
        dispose()
    }
}
