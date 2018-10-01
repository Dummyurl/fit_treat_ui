package code.database;

import android.app.Activity;

public final class AppSettings extends OSettings {
    public static final String PREFS_MAIN_FILE             = "PREFS_FITTREAT_FILE";
    public static final String accessToken                   = "accessToken";
    public static final String userId = "userId";
    public static final String from       = "from";
    public static final String otcName       = "otcName";

    public AppSettings(Activity mActivity) {
        super(mActivity);
    }
}
