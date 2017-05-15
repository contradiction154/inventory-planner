package fk.retail.ip.email.internal.module;

import com.google.inject.AbstractModule;
import fk.retail.ip.email.client.ConnektClient;
import fk.retail.ip.email.client.HystrixConnektClient;

/**
 * Created by agarwal.vaibhav on 05/05/17.
 */
public class EmailModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ConnektClient.class).to(HystrixConnektClient.class);
        bind(EmailDetailsRepository.class).to(JPAEmailDetailsRepository.class);
    }
}
