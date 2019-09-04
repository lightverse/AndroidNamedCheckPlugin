package com.github.lightverse.namedcheck;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by lightverse on 2019/9/2.
 */
public class ResourceUtil {


    public static VirtualFile  getAndroidResDir(@NotNull Module module){
        return null;
    }


    public static boolean maybeResDir(VirtualFile file){
        return file.isDirectory() && file.getName().equals("res");
    }


    public static String[] splitResourceAttrsDir(VirtualFile file){
        if(file.isDirectory()){
            return file.getName().split("-");
        }
        return new String[]{};
    }



    public static boolean checkNamedPrefix(VirtualFile file,String fixedPrefix){
        return !file.isDirectory() && file.getName().startsWith(fixedPrefix);
    }
}
