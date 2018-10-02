package code.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fittreat.android.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
    public static Toast mToast;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    static ProgressDialog progressDialog;

    public static float convertDpToPixel(float dp) {
        return dp * (((float) Resources.getSystem().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static float convertPixelsToDp(float px) {
        return px / (((float) Resources.getSystem().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static String print(String mString) {
        return mString;
    }

    public static String printD(String Tag, String mString) {
        return mString;
    }

    public static String printE(String Tag, String mString) {
        return mString;
    }

    public static int startPosition(String word, String sourceString) {
        int startingPosition = sourceString.indexOf(word);
        print("startingPosition" + word + " " + startingPosition);
        return startingPosition;
    }

    public static int endPosition(String word, String sourceString) {
        int endingPosition = sourceString.indexOf(word) + word.length();
        print("startingPosition" + word + " " + endingPosition);
        return endingPosition;
    }

    public static void showToastSort(Context context, String text) {
        if (mToast != null && mToast.getView().isShown()) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            try {
                @SuppressLint("WrongConstant") InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
                View view = activity.getCurrentFocus();
                if (view != null) {
                    IBinder binder = view.getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static float convertDpToPixel(float dp, Context context) {
        return (((float) getDisplayMetrics(context).densityDpi) / 160.0f) * dp;
    }

    public static int convertDpToPixelSize(float dp, Context context) {
        float pixels = convertDpToPixel(dp, context);
        int res = (int) (0.5f + pixels);
        if (res != 0) {
            return res;
        }
        if (pixels == 0.0f) {
            return 0;
        }
        if (pixels > 0.0f) {
            return 1;
        }
        return -1;
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhone(String pass) {
        return pass != null && pass.length() == 10;
    }

    @SuppressLint("WrongConstant")
    public static void centerToolbarTitle(@NonNull Toolbar toolbar, @NonNull Activity mActivity) {
        CharSequence title = toolbar.getTitle();
        ArrayList<View> outViews = new ArrayList(1);
        toolbar.findViewsWithText(outViews, title, 1);
        if (!outViews.isEmpty()) {
            TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(17);
            ((LayoutParams) titleView.getLayoutParams()).width = -1;
            toolbar.requestLayout();
            toolbar.setContentInsetsAbsolute(0, 0);
            toolbar.setContentInsetStartWithNavigation(0);
            setCustomFont(mActivity, titleView, "centurygothic.ttf");
        }
    }

    public static void setCustomFont(Activity mActivity, TextView mTextView, String asset) {
        mTextView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), asset));
    }

    public static void showRequestDialog(Activity activity) {

        if(!((Activity) activity).isFinishing())
        {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(activity.getString(R.string.please_wait));
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progressDialog.show();
            }
        }


    }

    public static void showRequestDialog(Activity activity, String message) {
        if (progressDialog == null) {
            //progressDialog = new ProgressDialog(activity, R.style.MyAlertDialogStyle);
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();
        }
    }

    public static void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static String getTncDate() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    /*public static void showErrorMessage(View mView, String errorMessage, Context mActivity) {
        Snackbar snackbar = Snackbar.make(mView, errorMessage, Snackbar.LENGTH_SHORT);
        TextView tv = (TextView) (snackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
        *//*Typeface font = Typeface.createFromAsset(mActivity.getAssets(), "centurygothic.otf");
        tv.setTypeface(font);*//*

        snackbar.show();
    }*/

    void changeProgressbarColor(ProgressBar mProgressBar, Activity mActivity) {
        if (VERSION.SDK_INT < 21) {
            Drawable wrapDrawable = DrawableCompat.wrap(mProgressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(mActivity, R.color.colorAccent));
            mProgressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            return;
        }
        mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mActivity, R.color.colorAccent), Mode.SRC_IN);
    }

    public static String toCamelCaseSentence(String s) {
        if (s == null) {
            return "";
        }
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String toCamelCaseWord : words) {
            sb.append(toCamelCaseWord(toCamelCaseWord));
        }
        return sb.toString().trim();
    }

    public static String toCamelCaseWord(String word) {
        if (word == null) {
            return "";
        }
        switch (word.length()) {
            case 0:
                return "";
            case 1:
                return word.toUpperCase(Locale.getDefault()) + " ";
            default:
                return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase(Locale.getDefault()) + " ";
        }
    }

    public static String split(String str) {
        String result = "";
        if (str.contains(" ")) {
            return toCamelCaseWord(str.split("\\s+")[0]);
        }
        return toCamelCaseWord(str);
    }

    public static void expand(final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.MATCH_PARENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    // GetDeviceId
    public static String getDeviceID(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDateCurrentTimeZone(long timestamp) {

        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy, hh:mm aa");

        System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String getDateFromTimestamp(long timestamp) {

        DateFormat formatter = new SimpleDateFormat("dd MMM hh:mm");

        System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp*1000);
        System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String getCurrentDate() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getDateTimeFromTimestamp(long timestamp) {

        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");

        System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp*1000);
        System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static boolean isEmailValid(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static String getmiliTimeStamp() {

        long LIMIT = 10000000000L;

        long t = Calendar.getInstance().getTimeInMillis();

        return String.valueOf(t).substring(0, 10);
    }

    public static String covertTimeToText(long createdAt) {
        DateFormat userDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        DateFormat dateFormatNeeded = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS");
        Date date = null;
        date = new Date(createdAt);
        String crdate1 = dateFormatNeeded.format(date);

        // Date Calculation
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        crdate1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);

        // get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currenttime = dateFormat.format(cal.getTime());

        Date CreatedAt = null;
        Date current = null;
        try {
            CreatedAt = dateFormat.parse(crdate1);
            current = dateFormat.parse(currenttime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = current.getTime() - CreatedAt.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String time = null;
        if (diffDays > 0) {
            if (diffDays == 1) {
                time = diffDays + " day ago ";
            } else {
                time = diffDays + " days ago ";
            }
        } else {
            if (diffHours > 0) {
                if (diffHours == 1) {
                    time = diffHours + " hr ago";
                } else {
                    time = diffHours + " hrs ago";
                }
            } else {
                if (diffMinutes > 0) {
                    if (diffMinutes == 1) {
                        time = diffMinutes + " min ago";
                    } else {
                        time = diffMinutes + " mins ago";
                    }
                } else {
                    if (diffSeconds > 0) {
                        time = diffSeconds + " secs ago";
                    }
                }

            }

        }
        return time;
    }

    public static String getDifference(String del, String lmp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = sdf.parse(del);
            Date now = sdf.parse(lmp);
            long days = getDateDiff(date, now, TimeUnit.DAYS);
            if (days < 7)
                return days + " Days";
            else
                return days / 7 + " Weeks";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }


    public static int getWeekDifference(String del, String lmp) {
       int week=0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = sdf.parse(del);
            Date now = sdf.parse(lmp);
            long days = getDateDiff(date, now, TimeUnit.DAYS);
            if (days < 7)
                week=0;
                //return days + " Days";
            else
                week = (int) (days / 7);
                //return days / 7 + " Weeks";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return week;
    }

    public static String getDateAgo(String del) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = sdf.parse(del);
            Date now = new Date(System.currentTimeMillis());
            long days = getDateDiff(date, now, TimeUnit.DAYS);
                return days + " Days";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static int getAgeFromDOB(String dobDate) {

        int age = 0;
        DateFormat format = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dobDate);

            try {

                if (dobDate != null) {

                    Date currDate = Calendar.getInstance().getTime();
                    // Log.d("Curr year === "+currDate.getYear()+" DOB Date == "+dobDate.getYear());
                    age = currDate.getYear() - date.getYear();
                    // Log.d("Calculated Age == "+age);
                }

            } catch (Exception e) {
                //Log.d(SyncStateContract.Constants.kApiExpTag, e.getMessage()+ "at Get Age From DOB mehtod.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010


        return age;

    }

    /**
     Current Activity instance will go through its lifecycle to onDestroy() and a new instance then created after it.
     */
    @SuppressLint("NewApi")
    public static final void recreateActivityCompat(final Activity a) {
        //GetBackFragment.ClearStack();
        if (VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        } else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finishAffinity();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getCurrentLocale(Activity activity){
        if (VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return activity.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return activity.getResources().getConfiguration().locale;
        }
    }

    public static long dateDifference(String dob)
    {
        long day=0;
        Date userDob = null;
        try {
            userDob = new SimpleDateFormat("dd-MM-yyyy HH:mm aa").parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date today = new Date();
        long diff =  today.getTime() - userDob.getTime();
        day =  diff / (1000 * 60 * 60 * 24);

        return  day;
    }

    public static long getCurrentTimestamp()
    {
        long time= System.currentTimeMillis();

        return  time;
    }

    public static void enableDisable(ViewGroup layout, boolean b) {
        layout.setEnabled(b);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                enableDisable((ViewGroup) child,b);
            } else {
                child.setEnabled(b);
            }
        }
    }

    /**
     * Making notification bar transparent
     */
    public static void changeStatusBarColor(Activity activity) {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static void bottomViewAnimation(Activity activity, View view) {
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, 0, activity.getResources().getDimension(R.dimen._240sdp), 0);
        mTranslateAnimation.setDuration(500);
        mTranslateAnimation.setFillAfter(true);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(mTranslateAnimation);
    }

    public static void bottomViewAkkuAnimation(Activity activity, View view) {
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, 0, activity.getResources().getDimension(R.dimen._240sdp), 0);
        mTranslateAnimation.setDuration(1000);
        mTranslateAnimation.setFillAfter(true);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(mTranslateAnimation);
    }

    public static void leftViewAkkuAnimation(Activity activity, View view) {
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(activity.getResources().getDimension(R.dimen._240sdp), 0, 0, 0);
        mTranslateAnimation.setDuration(1000);
        mTranslateAnimation.setFillAfter(true);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(mTranslateAnimation);
    }

    public static void rightViewAkkuAnimation(Activity activity, View view) {
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(activity.getResources().getDimension(R.dimen._minus60sdp), activity.getResources().getDimension(R.dimen._100sdp), 0, 0);
        mTranslateAnimation.setDuration(1000);
        mTranslateAnimation.setFillAfter(true);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(mTranslateAnimation);
    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }


        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a min ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " mins ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hr ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hrs ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static String dateDialog(Context context, final EditText editText, int type) {
        final String[] times = {""};

        Calendar mcurrentDate= Calendar.getInstance();
        int year=mcurrentDate.get(Calendar.YEAR);
        int month=mcurrentDate.get(Calendar.MONTH);
        int day=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        if(!editText.getText().toString().isEmpty())
        {
            String[] parts = editText.getText().toString().split("/");
            String part1 = parts[0];
            String part2 = parts[1];
            String part3 = parts[2];

            day = Integer.parseInt(part3);
            month = Integer.parseInt(part2);
            year = Integer.parseInt(part1);
        }

        DatePickerDialog mDatePicker1 =new DatePickerDialog(context,  new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
            {
                String dob = String.valueOf(new StringBuilder().append(selectedday).append("-").append(selectedmonth+1).append("-").append(selectedyear));
                Log.d("dob",dob);
                Log.d("dob",formatDate(selectedyear,selectedmonth,selectedday));
                //AppSettings.putString(AppSettings.from,formatDate(selectedyear,selectedmonth,selectedday));
                //tvDob.setText(dob);
                editText.setText(formatDate(selectedyear,selectedmonth,selectedday));
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },year, month, day);
        //mDatePicker1.setTitle("Select Date");
        // TODO Hide Future Date Here

        if(type==1)
        {
            mDatePicker1.getDatePicker().setMaxDate(System.currentTimeMillis());
        }
        else if(type==2)
        {
            mDatePicker1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
        else
        {
            mDatePicker1.getDatePicker().setMaxDate(System.currentTimeMillis());
            // Subtract 6 days from Calendar updated date
            mcurrentDate.add(Calendar.DATE, -1);
            mDatePicker1.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
        }

        // TODO Hide Past Date Here
        //set min todays date
        //mDatePicker1.getDatePicker().setMinDate(System.currentTimeMillis());

        mDatePicker1.show();

        return times[0];
    }

    public static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        return sdf.format(date);
    }
}
