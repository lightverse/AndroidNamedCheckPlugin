package com.github.lightverse.namedcheck;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by lightverse on 2019/9/1.
 *
 * This named folder reference the document at https://developer.android.com/guide/topics/resources/providing-resources.html
 */
public class FolderType {


    public static final String TYPE_LAYOUT = "layout";

    public static final String TYPE_ANIM = "anim";

    public static final String TYPE_COLOR = "color";

    public static final String TYPE_ANIMATOR = "animator";

    public static final String TYPE_DRAWABLE = "drawable";

    public static final String TYPE_INTERPOLATOR = "interpolator";

    public static final String TYPE_MENU = "menu";

    public static final String TYPE_MINMAP = "minmap";

    public static final String TYPE_NAVIGATION = "navigation";

    public static final String TYPE_RAW = "raw";

    public static final String TYPE_TRANSITION = "transition";

    public static final String TYPE_XML = "xml";

    public static final String TYPE_VALUE = "value";


    public static final HashSet<String> FIXED_PREFIX_FOLDER = new HashSet<>(Arrays.asList(
            TYPE_LAYOUT,TYPE_ANIM,TYPE_COLOR,TYPE_ANIMATOR,TYPE_DRAWABLE,
            TYPE_INTERPOLATOR,TYPE_XML
    ));

}
