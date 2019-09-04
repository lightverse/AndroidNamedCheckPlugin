package com.github.lightverse.namedcheck;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScopesCore;

/**
 * Created by lightverse on 2019/8/31.
 */
public class DialogAction extends AnAction{


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Project thisPro = anActionEvent.getProject();
        DataContext dataContext = anActionEvent.getDataContext();


        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        Module module = ModuleUtilCore.findModuleForFile(psiFile.getVirtualFile(), project);
        Module[] modules = ModuleManager.getInstance(project).getModules();

        PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, "MyKotlin.kt", GlobalSearchScopesCore.projectProductionScope(project) );
        if(psiFile instanceof PsiJavaFile){
            PsiImportList importList =  getImportList(project, (PsiJavaFile) psiFile);

            String result = Messages.showInputDialog(project,"PsiJavaFile","hello",Messages.getQuestionIcon());

        }else{
            Messages.showInputDialog(project,"otherFile","hello",Messages.getQuestionIcon());

        }

    }


    public PsiImportList getImportList(Project project,PsiJavaFile javaFile){
        final PsiImportList newImportList = JavaCodeStyleManager.getInstance(project).prepareOptimizeImportsResult(javaFile);
        return newImportList;
    }


    public void getXmlFiles(){

    }


}
