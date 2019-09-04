package com.github.lightverse.namedcheck;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.remoteServer.impl.runtime.log.ConsoleTerminalProvider;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import groovy.ui.ConsoleTextEditor;
import org.apache.http.util.TextUtils;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by lightverse on 2019/9/1.
 */
public class NamedCheckAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {


        VirtualFile actionFile = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());

        Module module = ModuleUtilCore.findModuleForFile(actionFile, e.getProject());

        if(module != null) {
            String result = Messages.showInputDialog(e.getProject(), "Please input the prefix:", module.getName(), Messages.getQuestionIcon());

            if (!TextUtils.isEmpty(result)) {

                VirtualFile[] children = actionFile.getChildren();

                List<VirtualFile> candidateFiles = new ArrayList<>();//找到候选资源目录
                for (VirtualFile aChildren : children) {
                    if (aChildren.isDirectory() && ResourceUtil.maybeResDir(aChildren)) {
                        candidateFiles.add(aChildren);
                    }
                }

                List<VirtualFile> fixedNameDirs = new ArrayList<>();//找到需要检查命名的文件夹

                if (!candidateFiles.isEmpty()) {
                    candidateFiles.forEach(file -> {
                        // TODO: 2019/9/2 检查子文件夹是不是layout anim ...

                        for (VirtualFile subFile : file.getChildren()) {
                            String[] resourceAttrsDir = ResourceUtil.splitResourceAttrsDir(subFile);
                            if (resourceAttrsDir.length > 0) {
                                String attr = resourceAttrsDir[0];
                                if (FolderType.FIXED_PREFIX_FOLDER.contains(attr)) {
                                    fixedNameDirs.add(subFile);
                                }
                            }
                        }


                    });
                }

                List<VirtualFile> rejectFiles = new ArrayList<>();
                if (!fixedNameDirs.isEmpty()) {
                    // TODO: 2019/9/2 检查文件夹里面文件的命名
                    fixedNameDirs.forEach(file -> {
                        if (file.isDirectory()) {
                            VirtualFile[] fixedNameFileList = file.getChildren();
                            for (VirtualFile subFile : fixedNameFileList) {
                                if (!ResourceUtil.checkNamedPrefix(subFile, result)) {
                                    rejectFiles.add(subFile);
                                }
                            }
                        }

                    });

                    // TODO: 2019/9/2 如何输出

                    ToolWindow actionSystemToolWindow = ToolWindowManager.getInstance(e.getProject()).getToolWindow("Named Check Result");
                    if(actionSystemToolWindow != null){
                        actionSystemToolWindow.show(new Runnable() {
                            @Override
                            public void run() {
                                JPanel jPanel = new JPanel();
                                Content actionToolWindow = ContentFactory.SERVICE.getInstance().createContent(jPanel, "", false);
                                actionSystemToolWindow.getContentManager().addContent(actionToolWindow);
                                StringBuilder outStr = new StringBuilder("****Files Below need check!****\n");
                                if(rejectFiles.size() > 0){
                                    for (VirtualFile rejectFile : rejectFiles) {
                                        outStr.append(rejectFile.getCanonicalPath());
                                        outStr.append("\n");
                                    }
                                }else{
                                    outStr.append("Well Done !! No file need change");
                                }

                                JTextArea jTextArea = new JTextArea(outStr.toString());
                                jTextArea.setLineWrap(true);
                                jPanel.setLayout(new BorderLayout());
                                jPanel.add(new JBScrollPane(jTextArea));


                            }
                        });
                    }
                }
            }
        }

    }





    @Override
    public void update(AnActionEvent e) {

        Module[] modules = ModuleManager.getInstance(e.getProject()).getModules();
        VirtualFile actionFile = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        for (int i = 0; i < modules.length; i++) {
            VirtualFile moduleFile = modules[i].getModuleFile();
            if(moduleFile.getParent().equals(actionFile)){
                e.getPresentation().setVisible(true);
                return;
            }
        }
        e.getPresentation().setVisible(false);

        //只有模块层级才会展示
    }
}
