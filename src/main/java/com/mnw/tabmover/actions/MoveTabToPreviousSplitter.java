package com.mnw.tabmover.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class MoveTabToPreviousSplitter extends DumbAwareAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = PlatformDataKeys.PROJECT.getData(event.getDataContext());
        final FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        final EditorWindow activeWindowPane = EditorWindow.DATA_KEY.getData(event.getDataContext());

        if (activeWindowPane == null) return; // Action invoked when no files are open; do nothing

        final EditorWindow prevWindowPane = fileEditorManagerEx.getPrevWindow(activeWindowPane);

        if (prevWindowPane == activeWindowPane) return; // Action invoked with one pane open; do nothing

        final EditorWithProviderComposite activeEditorTab = activeWindowPane.getSelectedEditor();
        final VirtualFile activeFile = activeEditorTab.getFile();

        prevWindowPane.getManager().openFileImpl2(prevWindowPane, activeFile, true);

        activeWindowPane.closeFile(activeFile, true, false);
    }

}
