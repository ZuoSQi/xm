/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.micode.notes.gtask.data;

import android.database.Cursor;
import android.util.Log;

import net.micode.notes.tool.GTaskStringUtils;

import org.json.JSONException;
import org.json.JSONObject;

// MetaData 类继承自 Task 类
public class MetaData extends Task {
    private final static String TAG = MetaData.class.getSimpleName();

    // 与其他任务相关的 Google 任务 ID
    private String mRelatedGid = null;

    // 设置元数据信息
    public void setMeta(String gid, JSONObject metaInfo) {
        try {
            // 将相关的 Google 任务 ID 放入 metaInfo JSONObject
            metaInfo.put(GTaskStringUtils.META_HEAD_GTASK_ID, gid);
        } catch (JSONException e) {
            // 捕获 JSONException 异常并记录错误日志
            Log.e(TAG, "failed to put related gid");
        }
        // 将 metaInfo 转换为字符串并设置为笔记内容
        setNotes(metaInfo.toString());
        // 设置笔记名称
        setName(GTaskStringUtils.META_NOTE_NAME);
    }

    // 获取相关的 Google 任务 ID
    public String getRelatedGid() {
        return mRelatedGid;
    }

    // 判断是否值得保存
    @Override
    public boolean isWorthSaving() {
        return getNotes() != null;
    }

    // 根据远程 JSON 内容设置任务内容
    @Override
    public void setContentByRemoteJSON(JSONObject js) {
        super.setContentByRemoteJSON(js);
        if (getNotes() != null) {
            try {
                // 从笔记内容中提取相关的 Google 任务 ID
                JSONObject metaInfo = new JSONObject(getNotes().trim());
                mRelatedGid = metaInfo.getString(GTaskStringUtils.META_HEAD_GTASK_ID);
            } catch (JSONException e) {
                // 捕获 JSONException 异常并记录警告日志
                Log.w(TAG, "failed to get related gid");
                mRelatedGid = null;
            }
        }
    }

    // 设置本地 JSON 内容的函数不应被调用
    @Override
    public void setContentByLocalJSON(JSONObject js) {
        // 不应该调用这个函数，因此抛出 IllegalAccessError 异常
        throw new IllegalAccessError("MetaData:setContentByLocalJSON should not be called");
    }

    // 获取本地 JSON 内容的函数不应被调用
    @Override
    public JSONObject getLocalJSONFromContent() {
        // 不应该调用这个函数，因此抛出 IllegalAccessError 异常
        throw new IllegalAccessError("MetaData:getLocalJSONFromContent should not be called");
    }

    // 获取同步操作的函数不应被调用
    @Override
    public int getSyncAction(Cursor c) {
        // 不应该调用这个函数，因此抛出 IllegalAccessError 异常
        throw new IllegalAccessError("MetaData:getSyncAction should not be called");
    }
}
