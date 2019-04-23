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
package com.jd.journalq.broker.network;

import com.jd.journalq.broker.network.frontend.FrontendServer;
import com.jd.journalq.broker.network.listener.BrokerTransportListener;
import com.jd.journalq.broker.network.protocol.ProtocolManager;
import com.jd.journalq.broker.BrokerContext;
import com.jd.journalq.broker.monitor.SessionManager;
import com.jd.journalq.network.transport.config.ServerConfig;
import com.jd.journalq.broker.network.backend.BackendServer;
import com.google.common.base.Preconditions;
import com.jd.journalq.toolkit.service.Service;

/**
 * broker服务
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/8/14
 */
public class BrokerServer extends Service {

    private FrontendServer frontendServer;
    private BackendServer backendServer;
    private BrokerTransportListener transportListener;

    public BrokerServer(BrokerContext brokerContext, ProtocolManager protocolManager) {
        Preconditions.checkArgument(brokerContext != null, "broker context can not be null");
        Preconditions.checkArgument(protocolManager != null, "protocol manager can not be null");

        ServerConfig frontendConfig = brokerContext.getBrokerConfig().getFrontendConfig();
        ServerConfig backendConfig = brokerContext.getBrokerConfig().getBackendConfig();
        SessionManager sessionManager = brokerContext.getSessionManager();

        frontendConfig.setAcceptThreadName("journalq-frontend-accept-eventLoop");
        frontendConfig.setIoThreadName("journalq-frontend-io-eventLoop");
        backendConfig.setAcceptThreadName("journalq-backend-accept-eventLoop");
        backendConfig.setIoThreadName("journalq-backend-io-eventLoop");

        this.transportListener = new BrokerTransportListener(sessionManager);
        this.frontendServer = new FrontendServer(frontendConfig, protocolManager);
        this.backendServer = new BackendServer(backendConfig, brokerContext);
        this.frontendServer.addListener(transportListener);
        this.backendServer.addListener(transportListener);
    }

    @Override
    protected void doStart() throws Exception {
        this.frontendServer.start();
        this.backendServer.start();
    }

    @Override
    protected void doStop() {
        this.frontendServer.removeListener(transportListener);
        this.backendServer.removeListener(transportListener);
        this.frontendServer.stop();
        this.backendServer.stop();
    }
}