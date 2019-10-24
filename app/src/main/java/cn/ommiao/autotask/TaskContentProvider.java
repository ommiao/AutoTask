package cn.ommiao.autotask;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import cn.ommiao.autotask.entity.ExecuteResultData;
import cn.ommiao.autotask.util.AppDatabase;
import cn.ommiao.base.entity.order.ExecuteResult;

public class TaskContentProvider extends ContentProvider {

    private static final String AUTHORITY = "cn.ommiao.autotask.provider";

    private static final int EXECUTE_RESULT_DIR = 0;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "executeresult", EXECUTE_RESULT_DIR);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case EXECUTE_RESULT_DIR:
                return "vnd.android.cursor.dir/vnd.cn.ommiao.autotask.provider.executeresult";
        }
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case EXECUTE_RESULT_DIR:
                ExecuteResult executeResult = ExecuteResult.fromJson(values.getAsString("executeResult"), ExecuteResult.class);
                ExecuteResultData executeResultData = new ExecuteResultData();
                executeResultData.setTaskId(executeResult.taskId);
                executeResultData.setTaskName(executeResult.taskName);
                executeResultData.setStartTime(executeResult.startTime);
                executeResultData.setEndTime(executeResult.endTime);
                executeResultData.setSuccess(executeResult.success);
                executeResultData.setErrorReason(executeResult.errorReason);
                long newId = AppDatabase.getTaskDatabase().taskDao().insertExecuteResult(executeResultData);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/executeresult/" + newId);
                if(getContext() != null){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
