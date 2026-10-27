package br.gov.sp.cps.produtos_soap.config;

import java.util.List;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

/**
 * WS-Security com UsernameToken: toda requisição SOAP precisa ter o cabeçalho
 * wsse:Security com usuário e senha válidos, senão o serviço devolve um SOAP Fault.
 */
@Configuration
public class WebServiceSecurityConfig implements WsConfigurer {

    @Value("${soap.security.username}")
    private String username;

    @Value("${soap.security.password}")
    private String password;

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();
        interceptor.setValidationActions("UsernameToken");
        interceptor.setValidationCallbackHandler(passwordCallbackHandler());
        return interceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }

    private CallbackHandler passwordCallbackHandler() {
        return callbacks -> {
            for (Callback callback : callbacks) {
                if (callback instanceof WSPasswordCallback pc && username.equals(pc.getIdentifier())) {
                    pc.setPassword(password);
                }
            }
        };
    }
}
