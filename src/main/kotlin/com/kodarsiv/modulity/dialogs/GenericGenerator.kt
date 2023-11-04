package com.kodarsiv.modulity.dialogs



import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.LocalFileSystem
import java.awt.event.KeyEvent
import javax.swing.*


class GenericGenerator() : JDialog() {
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


        buttonOK?.addActionListener { onOK() }
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




    private fun onOK() {


        dispose()
    }

    private fun onCancel() {
        dispose()
    }
}
