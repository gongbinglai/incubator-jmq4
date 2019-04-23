/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jd.journalq.server.archive.store.api;

import com.jd.journalq.exception.JournalqException;
import com.jd.journalq.server.archive.store.model.AchivePosition;
import com.jd.journalq.server.archive.store.model.ConsumeLog;
import com.jd.journalq.server.archive.store.model.Query;
import com.jd.journalq.server.archive.store.model.SendLog;
import com.jd.journalq.toolkit.lang.LifeCycle;

import java.util.List;

/**
 * 归档接口
 * <br>
 * 持久化消费信息、生产信息、归档进度信息
 * <p>
 * Created by chengzhiliang on 2018/12/4.
 */
public interface ArchiveStore extends LifeCycle {

    /**
     * 持久化消费日志
     *
     * @param consumeLogs
     * @throws JournalqException
     */
    void putConsumeLog(List<ConsumeLog> consumeLogs) throws JournalqException;

    /**
     * 持久化发送日志
     *
     * @param sendLogs
     * @throws JournalqException
     */
    void putSendLog(List<SendLog> sendLogs) throws JournalqException;

    /**
     * 持久化归档进度信息
     *
     * @param achivePosition
     * @throws JournalqException
     */
    void putPosition(AchivePosition achivePosition) throws JournalqException;

    /**
     * 查询归档进度信息
     *
     * @param topic
     * @param partition
     * @return
     * @throws JournalqException
     */
    Long getPosition(String topic, short partition) throws JournalqException;

    /**
     * 查看发送日志
     *
     * @param query
     * @return
     * @throws JournalqException
     */
    List<SendLog> scanSendLog(Query query) throws JournalqException;

    /**
     * 查看一条发送日志
     *
     * @param query
     * @return
     * @throws JournalqException
     */
    SendLog getOneSendLog(Query query) throws JournalqException;

    /**
     * 查询消费日志
     *
     * @param messageId
     * @param count
     * @return
     * @throws JournalqException
     */
    List<ConsumeLog> scanConsumeLog(String messageId,Integer count) throws JournalqException;
}
