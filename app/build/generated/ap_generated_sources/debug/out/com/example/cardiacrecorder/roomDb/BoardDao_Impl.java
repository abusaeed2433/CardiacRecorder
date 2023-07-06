package com.example.cardiacrecorder.roomDb;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.cardiacrecorder.classes.EachData;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BoardDao_Impl implements BoardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EachData> __insertionAdapterOfEachData;

  private final EntityDeletionOrUpdateAdapter<EachData> __deletionAdapterOfEachData;

  private final EntityDeletionOrUpdateAdapter<EachData> __updateAdapterOfEachData;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public BoardDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEachData = new EntityInsertionAdapter<EachData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `data_table` (`id`,`timestamp`,`date`,`time`,`sysPressure`,`dysPressure`,`heartRate`,`comment`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EachData value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getTimestamp());
        if (value.getDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDate());
        }
        if (value.getTime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTime());
        }
        stmt.bindLong(5, value.getSysPressure());
        stmt.bindLong(6, value.getDysPressure());
        stmt.bindLong(7, value.getHeartRate());
        if (value.getComment() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getComment());
        }
      }
    };
    this.__deletionAdapterOfEachData = new EntityDeletionOrUpdateAdapter<EachData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `data_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EachData value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfEachData = new EntityDeletionOrUpdateAdapter<EachData>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `data_table` SET `id` = ?,`timestamp` = ?,`date` = ?,`time` = ?,`sysPressure` = ?,`dysPressure` = ?,`heartRate` = ?,`comment` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EachData value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getTimestamp());
        if (value.getDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDate());
        }
        if (value.getTime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTime());
        }
        stmt.bindLong(5, value.getSysPressure());
        stmt.bindLong(6, value.getDysPressure());
        stmt.bindLong(7, value.getHeartRate());
        if (value.getComment() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getComment());
        }
        stmt.bindLong(9, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM data_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final EachData data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEachData.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final EachData data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfEachData.handle(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final EachData data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfEachData.handle(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<EachData>> getAllData() {
    final String _sql = "SELECT * FROM data_table ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"data_table"}, false, new Callable<List<EachData>>() {
      @Override
      public List<EachData> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfSysPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "sysPressure");
          final int _cursorIndexOfDysPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "dysPressure");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfComment = CursorUtil.getColumnIndexOrThrow(_cursor, "comment");
          final List<EachData> _result = new ArrayList<EachData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final EachData _item;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
            }
            final int _tmpSysPressure;
            _tmpSysPressure = _cursor.getInt(_cursorIndexOfSysPressure);
            final int _tmpDysPressure;
            _tmpDysPressure = _cursor.getInt(_cursorIndexOfDysPressure);
            final int _tmpHeartRate;
            _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            final String _tmpComment;
            if (_cursor.isNull(_cursorIndexOfComment)) {
              _tmpComment = null;
            } else {
              _tmpComment = _cursor.getString(_cursorIndexOfComment);
            }
            _item = new EachData(_tmpTimestamp,_tmpDate,_tmpTime,_tmpSysPressure,_tmpDysPressure,_tmpHeartRate,_tmpComment);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
