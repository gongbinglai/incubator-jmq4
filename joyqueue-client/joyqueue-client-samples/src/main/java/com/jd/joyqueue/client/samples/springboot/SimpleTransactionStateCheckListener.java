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
package com.jd.joyqueue.client.samples.springboot;

import io.openmessaging.message.Message;
import io.openmessaging.producer.TransactionStateCheckListener;
import io.openmessaging.spring.boot.annotation.OMSTransactionStateCheckListener;

/**
 * TransactionStateCheckListener1
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2019/3/8
 */
@OMSTransactionStateCheckListener
public class SimpleTransactionStateCheckListener implements TransactionStateCheckListener {

    @Override
    public void check(Message message, TransactionalContext context) {
        System.out.println(String.format("check, message: %s", message));
        context.commit();
    }
}